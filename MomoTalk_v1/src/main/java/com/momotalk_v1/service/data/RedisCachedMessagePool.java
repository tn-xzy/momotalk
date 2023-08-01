package com.momotalk_v1.service.data;

import com.momotalk_v1.entity.Message;
import com.momotalk_v1.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository("redisPool")
@Slf4j
public class RedisCachedMessagePool implements MessagePool {
    @Autowired
    private RedisTemplate<String, Message> messageRedis;
    @Autowired
    private RedisTemplate<String, Long> expireRedis;
    @Autowired
    private RedisTemplate<String, Integer> debounceRedis;
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    public RedisCachedMessagePool(RedisTemplate<String, String> stringTemplate,
                                  RedisTemplate<String, Long> longTemplate) {
        groupSet = stringTemplate.boundSetOps("momo:group");
        finishedGroupSet = stringTemplate.boundSetOps("momo:finished");
        maxUidHash = longTemplate.boundHashOps("momo:maxuid");
    }

    private final BoundHashOperations<String, String, Long> maxUidHash;
    private final BoundSetOperations<String, String> groupSet;
    private final BoundSetOperations<String, String> finishedGroupSet;
    private static final String messagePrefix = "momo:message:";
    protected static final String expirePrefix = "momo:expire:";
    protected static final String debouncePrefix = "momo:debounce:";
    private final long startUid=1L<<33;

    @Override
    synchronized public void add(Message message) {//新增消息
        load(message.getGroupId(), -1);
        delaySignal(message.getGroupId());
        messageRedis.opsForZSet().add(messagePrefix + message.getGroupId(),
                message, message.getUid());
    }

    @Override
    public List<Message> fetch(String groupId, long start) {//获取消息
        load(groupId, start);
        delaySignal(groupId);
        Set<Message> result;
        if (start<0){
            result=messageRedis.opsForZSet().reverseRange(messagePrefix+groupId,0,num-1);
        }else {
            result=messageRedis.opsForZSet().reverseRangeByScore(messagePrefix + groupId, 0, start - 1, 0, num);
        }
        return new ArrayList<>(Optional.ofNullable(result).orElse(new HashSet<>()));
    }
    private void delaySignal(String groupId){//通过redis的过期机制进行防抖，一秒至多执行一次
        if (Boolean.FALSE.equals(debounceRedis.hasKey(debouncePrefix + groupId))){
            debounceRedis.opsForValue().set(debouncePrefix+groupId,1,1,TimeUnit.SECONDS);
        }
    }
    @Override
    public void delayExpire(String groupId){//过期时间延长
        Long now=System.currentTimeMillis();
        Long expire=Optional.ofNullable(expireRedis.opsForValue().get(expirePrefix +groupId)).orElse(now);
        long minute=(expire-now)/60/1000;
        long expireMilli= (long) (Math.pow(minute,1.2)+2)*60*1000;
        minute=expireMilli>60*60*1000?60:expireMilli/60/1000;//限定最大值
        expire= now+minute*60*1000;
        expireRedis.opsForValue().set(expirePrefix +groupId,expire,minute, TimeUnit.MINUTES);
        log.info(groupId+" will expire at "+(minute)+" minutes");
    }
    synchronized public boolean load(String groupId, long start) {//加载消息
        if (Boolean.TRUE.equals(finishedGroupSet.isMember(groupId))) return true;//已完成加载直接返回
        if (!Boolean.TRUE.equals(groupSet.isMember(groupId))) {//未加载的情况
            List<Message> messageList = messageMapper.init(groupId);//从数据库拿取最新的十条放入redis
            for (Message message : messageList)
                messageRedis.opsForZSet().add(messagePrefix + groupId, message, message.getUid());
            groupSet.add(groupId);
            if (messageList.size() < num) {//没拿到指定的数量说明没有更多消息
                finishedGroupSet.add(groupId);
            }
            if (messageList.size()==0){
                maxUidHash.put(groupId, startUid);
            }else maxUidHash.put(groupId, messageList.get(0).getUid());
            return load(groupId, start);
        } else {
            if (start < 0) return false;
            //要考虑到群组原本无消息的情况
            List<Message> messageList = new ArrayList<>(Optional.ofNullable(messageRedis.opsForZSet().range(messagePrefix + groupId, 0, 0)).orElse(new HashSet<>()));
            long minuid = start;
            if (messageList.size() == 0) {//群组原本无消息,理论上不会出现
                return true;
            } else if (messageList.size() == 1) {
                minuid = messageList.get(0).getUid();
            }
            if (start < minuid) {//redis未加载到start，补足缺失
                messageList = messageMapper.range(groupId, minuid, start);
                for (Message message : messageList)
                    messageRedis.opsForZSet().add(messagePrefix + groupId, message, message.getUid());
            }
            messageList = messageMapper.get(groupId, start, num * 2);
            for (Message message : messageList)
                messageRedis.opsForZSet().add(messagePrefix + groupId, message, message.getUid());
            if (messageList.size() < num * 2) {
                finishedGroupSet.add(groupId);
                return true;
            }
            return false;
        }
    }

    public void save(String groupId) {//获取过去的最大uid，将比过去最大值还大的部分写入数据库
//        log.info("prepare to save "+groupId);
        long pastMaxuid = Optional.ofNullable(maxUidHash.get(groupId)).orElse(startUid);
        List<Message> messageList=new ArrayList<>(Optional.ofNullable(messageRedis.opsForZSet().reverseRange(messagePrefix+groupId,0,0)).orElse(new HashSet<>()));
        if (messageList.size()==0) return;//redis和数据库都无消息
        long newMaxuid=messageList.get(0).getUid();
        if (pastMaxuid==newMaxuid) return;//redis无新消息
        messageList = new ArrayList<>(Optional.ofNullable(messageRedis.opsForZSet().rangeByScore(messagePrefix + groupId, pastMaxuid + 1, newMaxuid)).orElse(new HashSet<>()));
        for (Message message : messageList)
            messageMapper.add(message);
        maxUidHash.put(groupId, newMaxuid);
    }
    @Override
    synchronized public void close(String groupId) {
        if (Boolean.FALSE.equals(groupSet.isMember(groupId))) return;//已经被关闭或已在关闭中
        groupSet.remove(groupId);
        finishedGroupSet.remove(groupId);
        save(groupId);
        messageRedis.delete(messagePrefix + groupId);
        maxUidHash.delete(groupId);
        expireRedis.delete(expirePrefix+groupId);
    }

    @Scheduled(fixedRate = 60*1000)
    protected void saveAll() {
        Set<String> groupSet = this.groupSet.members();
        if (groupSet == null) return;
        groupSet.forEach(this::save);
    }

    @PreDestroy
    synchronized private void closeAll() {
        Set<String> groupSet = this.groupSet.members();
        if (groupSet == null) return;
        groupSet.forEach(this::close);


    }
}
