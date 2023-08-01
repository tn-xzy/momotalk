package com.momotalk_v1.controller;

import com.momotalk_v1.entity.*;
import com.momotalk_v1.entity.constant.ResultCodes;
import com.momotalk_v1.mapper.GroupMapper;
import com.momotalk_v1.mapper.MemberMapper;
import com.momotalk_v1.service.FileService;
import com.momotalk_v1.service.MemberService;
import com.momotalk_v1.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import com.momotalk_v1.utils.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    FileService fileService;
    @Autowired
    MessageService messageService;
    @GetMapping
    public Result<List<Group>> getGroups(@SessionAttribute String username){
        return new Result<>(ResultCodes.SUCCESS,memberMapper.checkGroups(username));
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> create(MultipartFile file, String groupName, @SessionAttribute String username){
        Group group=new Group();
        if (file!=null){
            String imgPath= fileService.upload(file);
            group.setAvatar(imgPath);
        }
        group.setGroupName(groupName);
        group.setGroupId(NumberUUidGenerator.numberUUID());
        group.setCreatedTime(new Date());
        group.setIntroduction("快来写点什么介绍一下吧");
        groupMapper.create(group);
        Message message=new Message(0, group.getGroupId(), "system","system",username+"创建了群组");
        messageService.addSystem(message);
        memberService.addAdmin(new GroupMember(group.getGroupId(),username,"creator"));
        return new Result<>(ResultCodes.SUCCESS,"success");
    }

}
