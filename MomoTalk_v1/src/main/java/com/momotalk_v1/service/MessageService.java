package com.momotalk_v1.service;

import com.momotalk_v1.entity.Message;
import com.momotalk_v1.entity.UploadFile;
import com.momotalk_v1.entity.context.ContentFile;
import com.momotalk_v1.entity.context.ContentImage;
import com.momotalk_v1.entity.context.ContentText;
import com.momotalk_v1.service.data.MessagePool;
import com.momotalk_v1.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    String resourcePath = System.getProperty("user.dir");
    public void addText(Message message){
        ContentText contentText=new ContentText(message.getContent());
        message.setContent(JsonUtil.entity2json(contentText));
        message.setType("text");
        message.setUid(uidGeneratorManager.nextId(message.getGroupId()));
        messageForwarder.forwardNew(message);
        messagePool.add(message);
    }
    public void addImg(Message message, MultipartFile file){
        message.setType("img");
        UploadFile uploadFile= fileService.upload(file,"img");
        ContentImage contentImage=new ContentImage(uploadFile);
        Path targetPath=Paths.get("static","img",uploadFile.getSavename());
        contentImage.setImgPath(targetPath.toString());
        contentImage.setImgName(file.getOriginalFilename());
        message.setContent(JsonUtil.entity2json(contentImage));
        message.setUid(uidGeneratorManager.nextId(message.getGroupId()));
        messageForwarder.forwardNew(message);
        messagePool.add(message);
    }
    public void addFile(Message message, MultipartFile file){
        message.setType("file");
        UploadFile uploadFile= fileService.upload(file,"file");
        Path targetPath=Paths.get("static","file",uploadFile.getSavename());
        ContentFile contentFile=new ContentFile(uploadFile);
        contentFile.setFilepath(targetPath.toString());
        contentFile.setFilename(file.getOriginalFilename());
        message.setContent(JsonUtil.entity2json(contentFile));
        message.setUid(uidGeneratorManager.nextId(message.getGroupId()));
        messageForwarder.forwardNew(message);
        messagePool.add(message);
    }
    public void addSystem(Message message){
        ContentText contentText=new ContentText(message.getContent());
        message.setContent(JsonUtil.entity2json(contentText));
        message.setType("system");
        message.setUid(uidGeneratorManager.nextId(message.getGroupId()));
        messageForwarder.forwardNew(message);
        messagePool.add(message);
    }
    public List<Message> fetch (String groupId, long start){
        return messagePool.fetch(groupId,start);
    }
}
