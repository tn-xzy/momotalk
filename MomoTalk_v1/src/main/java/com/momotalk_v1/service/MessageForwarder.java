package com.momotalk_v1.service;


import com.momotalk_v1.entity.Message;
import com.momotalk_v1.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MessageForwarder {

    @Autowired
    SimpMessagingTemplate smt;
    public void forwardNew(Message message){
//        System.out.println(forwardPrefix+message.getGroupId());
        smt.convertAndSend("/forward/"+message.getGroupId(),new Operation("new",message));
    }
}
