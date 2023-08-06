package com.momotalk_v1.config;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionConfig implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        List<String> list=new ArrayList<>();
        list.add("user.get");
        list.add("group.send");
        list.add("group.get");
        return list;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        List<String> list = new ArrayList<String>();
//        list.add("admin");
        list.add("normal");
        return list;
    }
}
