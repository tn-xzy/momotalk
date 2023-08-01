package com.momotalk_v1.controller;

import com.momotalk_v1.entity.Message;
import com.momotalk_v1.entity.Result;
import com.momotalk_v1.entity.constant.ResultCodes;
import com.momotalk_v1.service.MessageService;
import com.momotalk_v1.service.UidGeneratorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    UidGeneratorManager uidGeneratorManager;
    @Autowired
    MessageService messageService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<String> send(@RequestBody Message message,@SessionAttribute String username){
        message.setSender(username);
        messageService.addText(message);
        return new Result<>(ResultCodes.SUCCESS,"success");
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> send(MultipartFile file,String type,String groupId,@SessionAttribute String username){
        if ("img".equals(type)){
            Message message=new Message(uidGeneratorManager.nextId(groupId),groupId,type,username,null);
            messageService.addImg(message,file);
            return new Result<>(ResultCodes.SUCCESS,"success");
        }else if ("file".equals(type)){
            Message message=new Message(uidGeneratorManager.nextId(groupId),groupId,type,username,null);
            messageService.addFile(message,file);
            return new Result<>(ResultCodes.SUCCESS,"success");
        }
        return new Result<>(ResultCodes.SUCCESS,"success");
    }
    @GetMapping
    public Result<List<Message>> fetch(String groupId, Long start){
        if (groupId==null||start==null)
            return new Result<>(ResultCodes.FAIL,0,"数据不全");
        return new Result<>(ResultCodes.SUCCESS,messageService.fetch(groupId,start));
    }

}
