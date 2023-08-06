package com.momotalk_v1.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.momotalk_v1.entity.*;
import com.momotalk_v1.entity.constant.ResultCodes;
import com.momotalk_v1.mapper.GroupMapper;
import com.momotalk_v1.mapper.MemberMapper;
import com.momotalk_v1.service.FileService;
import com.momotalk_v1.service.MemberService;
import com.momotalk_v1.service.MessageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import com.momotalk_v1.utils.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("group")
@SaCheckLogin
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
    public Result<List<Group>> getGroups(){
        return new Result<>(ResultCodes.SUCCESS,memberMapper.checkGroups(StpUtil.getSession().getString("username")));
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> create(MultipartFile file, String groupName){
        Group group=new Group();
        if (file!=null){
            UploadFile uploadFile= fileService.upload(file,"avatar");
            Path avatarPath= Paths.get("static","avatar",uploadFile.getSavename());
            group.setAvatar(avatarPath.toString());
        }
        group.setGroupName(groupName);
        group.setGroupId(NumberUUidGenerator.numberUUID());
        group.setCreatedTime(new Date());
        group.setIntroduction("快来写点什么介绍一下吧");
        groupMapper.create(group);
        Message message=new Message(0, group.getGroupId(), "system","system",StpUtil.getSession().getString("username")+"创建了群组");
        messageService.addSystem(message);
        memberService.addAdmin(new GroupMember(group.getGroupId(),StpUtil.getSession().getString("username"),"creator"));
        return new Result<>(ResultCodes.SUCCESS,"success");
    }

}
