package com.momotalk_v1.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.momotalk_v1.entity.Result;
import com.momotalk_v1.entity.User;
import com.momotalk_v1.entity.constant.ResultCodes;
import com.momotalk_v1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @GetMapping
    public Result<String> login(String username,String password,HttpSession session){
        System.out.println(username+":"+password);
        User auth=userMapper.login(username);
        if (Objects.equals(auth.getPassword(),password)){
            session.setAttribute("username",username);
            return new Result<>(ResultCodes.SUCCESS,"success");
        }else {
            return new Result<>(ResultCodes.FAIL,"fail");
        }
    }
    @PostMapping
    public Result<String> register(@RequestBody User user){
        user.setRegisterTime(new Date());
        userMapper.register(user);
        return new Result<>(ResultCodes.SUCCESS,"SUCCESS");
    }
}
