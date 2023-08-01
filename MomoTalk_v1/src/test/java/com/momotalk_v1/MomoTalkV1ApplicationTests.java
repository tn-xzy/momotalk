package com.momotalk_v1;

import com.momotalk_v1.entity.Message;
import com.momotalk_v1.mapper.MessageMapper;
import com.momotalk_v1.service.MessageService;
import com.momotalk_v1.service.UidGeneratorManager;
import com.momotalk_v1.service.data.MessagePool;
import com.momotalk_v1.service.data.RedisCachedMessagePool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:mysql://localhost/momotalk"})
class MomoTalkV1ApplicationTests {
    @Autowired
    MessageService messageService;
    @Autowired
    UidGeneratorManager uidGeneratorManager;
    @Autowired
    private RedisTemplate<String, Instant> expireRedis;
    @Autowired
    RedisCachedMessagePool messagePool;
    @Autowired
    private RedisTemplate<String, String> messageRedisTemplate;
    private final String messagePerfix = "momo:message:";
    @Autowired
    MessageMapper messageMapper;
    @Test
    void addMessage() throws IOException {
        Message message=new Message();
        int content=10;
        message.setContent("1");
        message.setGroupId("1");
        message.setSender("me");
//        message.setType("text");
        for (int i=10;i<20;i++){
            message.setContent(i+"");
            messageService.addText(message);

        }
    }
    @Test
    void uidTest(){
    }
    @Test
    void redisTest(){
        Object o= expireRedis.opsForValue().get("key");
        System.out.println(o.getClass().getName());
    }
    @Test
    void sqlTest(){
        System.out.println(messageMapper.init("1531661844"));;
    }

}
