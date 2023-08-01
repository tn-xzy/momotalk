package com.momotalk_v1.mapper;

import com.momotalk_v1.entity.Group;
import com.momotalk_v1.entity.GroupMember;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {
    @Insert("insert into group_member "+
    "(group_id, username) VALUES (#{groupId},#{username})")
    public boolean addMember(GroupMember member);
    @Insert("insert into group_member "+
            " VALUES (#{groupId},#{username},#{privilege})")
    public boolean addAdmin(GroupMember member);
    @Select("SELECT `group`.group_name, `group`.avatar, `group`.group_id\n" +
            "FROM `group` inner join\n" +
            "     group_member\n" +
            "on `group`.group_id=group_member.group_id\n" +
            "where group_member.`username` =#{username}")
    public List<Group> checkGroups(String username);
}
