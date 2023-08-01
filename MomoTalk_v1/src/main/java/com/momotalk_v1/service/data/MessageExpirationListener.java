package com.momotalk_v1.service.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageExpirationListener extends KeyExpirationEventMessageListener {
    private final String expirePrefix = "momo:expire:";
    @Autowired
    @Qualifier("redisPool")
    MessagePool messagePool;
    @Autowired
    public MessageExpirationListener(@Qualifier("redisContainer") RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = message.toString();
//        log.info("key expire:"+expireKey);
        if (expireKey.startsWith(RedisCachedMessagePool.expirePrefix)){//消息列表过期
            String[] splits=expireKey.split(":");
            messagePool.close(splits[splits.length-1]);
        }else if (expireKey.startsWith(RedisCachedMessagePool.debouncePrefix)){//延迟消息过期
            String[] splits=expireKey.split(":");
            messagePool.delayExpire(splits[splits.length-1]);
        }
    }
}

