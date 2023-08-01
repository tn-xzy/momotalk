package com.momotalk_v1.mapper;

import com.momotalk_v1.entity.Group;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupMapper {
    @Insert("insert into `group` "+
            "(group_name, group_id, introduction, created_time) "+
            "VALUES (#{groupName},#{groupId},#{introduction},#{createdTime})"
    )
    public boolean create(Group group);
}
