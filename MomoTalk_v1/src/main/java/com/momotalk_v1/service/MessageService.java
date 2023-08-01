package com.momotalk_v1.service;

import com.momotalk_v1.entity.Message;
import com.momotalk_v1.service.data.MessagePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    UidGeneratorManager uidGeneratorManager;
    @Autowired
    MessageForwarder messageForwarder;
    @Autowired
    @Qualifier("redisPool")
    MessagePool messagePool;
    @Autowired
    FileService fileService;
    public void addText(Message message){
        message.setType("text");
        message.setUid(uidGeneratorManager.nextId(message.getGroupId()));
        messageForwarder.forwardNew(message);
        messagePool.add(message);
    }
    public void addImg(Message message, MultipartFile file){
        message.setType("img");
        String imgPath= fileService.upload(file);
        message.setContent(imgPath);
        message.setUid(uidGeneratorManager.nextId(message.getGroupId()));
        messageForwarder.forwardNew(message);
        messagePool.add(message);
    }
    public void addFile(Message message, MultipartFile file){
        message.setType("file");
        String filePath= fileService.upload(file);
        message.setContent(filePath);
        message.setUid(uidGeneratorManager.nextId(message.getGroupId()));
        messageForwarder.forwardNew(message);
        messagePool.add(message);
    }
    public void addSystem(Message message){
        message.setType("system");
        message.setUid(uidGeneratorManager.nextId(message.getGroupId()));
        messageForwarder.forwardNew(message);
        messagePool.add(message);
    }
    public List<Message> fetch (String groupId, long start){
        return messagePool.fetch(groupId,start);
    }
}
