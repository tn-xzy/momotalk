package com.momotalk_v1.mapper;

import com.momotalk_v1.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Value("momotalk.num:10")
    int num=10;
    @Insert("insert into message "+
    "(uid, group_id, type, sender, content) "+
    "VALUES (#{uid},#{groupId},#{type},#{sender},#{content})")
    public boolean add(Message message);
    @Select("select uid, group_id, type, sender, content from message where group_id=#{groupId} and uid<=#{start} order by uid desc limit #{num}")
    public List<Message> get(@Param("groupId") String groupId,@Param("start") long start,@Param("num") int num);
    @Select("select uid, group_id, type, sender, content from message where group_id=#{groupId} and uid>=#{start} and uid<#{end}")
    public List<Message> range(@Param("groupId") String groupId,@Param("start") long start,@Param("end") long end);
    @Select("SELECT * FROM message where group_id=#{groupId} order by uid desc limit "+num)
    public List<Message> init(String groupId);
    @Select("select count(*) from message where group_id=#{groupId}")
    public int size(String groupId);
}

