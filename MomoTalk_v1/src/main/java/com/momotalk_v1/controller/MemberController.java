package com.momotalk_v1.controller;

import com.momotalk_v1.entity.GroupMember;
import com.momotalk_v1.entity.Result;
import com.momotalk_v1.entity.constant.ResultCodes;
import com.momotalk_v1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @PostMapping
    public Result<String> add(GroupMember member){
        memberService.addMember(member);
        return new Result<>(ResultCodes.SUCCESS,"success");
    }

}
