package com.momotalk_v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    public RedisTest(RedisTemplate<String, String> stringTemplate,
                                  RedisTemplate<String, Long> longTemplate) {
        maxUidHash = longTemplate.boundHashOps("momo:maxuid");
    }
    private final BoundHashOperations<String, String, Long> maxUidHash;

    @Test
    void test1(){
//        maxUidHash.put("1531661844",0L);
        Number maxuid = (Number) maxUidHash.get("1531661844");
        System.out.println(maxUidHash.get("1531661844"));
    }
}
