package com.momotalk_v1.mapper;

import com.momotalk_v1.entity.UploadFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {
    @Select("select count(*) from file where hash=#{hash}")
    public int exists(String hash);

    @Insert("insert into file " +
//            "values (hash,filename,size,unit) "+
            "values (#{hash}, #{savename}, #{size}, #{unit})")
    public boolean add(UploadFile uploadFile);

    @Select("select hash, savename, size, unit from file where hash=#{hash}")
    public UploadFile check(String hash);
}
