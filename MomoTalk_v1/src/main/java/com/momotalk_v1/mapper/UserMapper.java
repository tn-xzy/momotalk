package com.momotalk_v1.mapper;

import com.momotalk_v1.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    public User login(String username);
    @Insert("insert into user (username, password, register_time) "+
    "values (#{username},#{password},#{registerTime})")
    public int register(User user);
}
