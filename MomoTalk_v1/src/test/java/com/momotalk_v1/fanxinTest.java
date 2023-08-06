package com.momotalk_v1;

import com.momotalk_v1.entity.Message;
import com.momotalk_v1.mapper.MessageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.MessageMapping;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:mysql://localhost/dev"})
public class fanxinTest {
    @Autowired
    MessageMapper messageMapper;
    @Test
    public void main(){

    }
}
