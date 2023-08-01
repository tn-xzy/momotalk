package com.momotalk_v1.service;

import com.momotalk_v1.entity.GroupMember;
import com.momotalk_v1.entity.Message;
import com.momotalk_v1.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    MessageService messageService;
    public void addAdmin(GroupMember member){
        Message message=new Message(0, member.getGroupId(), "system","system",member.getUsername()+"加入了群组");
        messageService.addSystem(message);
        memberMapper.addAdmin(member);
    }
    public void addMember(GroupMember member){
        Message message=new Message(0, member.getGroupId(), "system","system",member.getUsername()+"加入了群组");
        messageService.addSystem(message);
        memberMapper.addMember(member);
    }

}
