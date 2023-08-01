package com.momotalk_v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private String type;
    @JsonIgnore
    private String contentBefore;//如果是新消息为空
    @JsonIgnore
    private String contentAfter;//如果是删除为空，这两条一样要加密
    @JsonIgnore
    private String messageHash;//这条是外键，策略使用级联删除
    @JsonIgnore
    private long timestamp;//修改时间，在数据库里记录一下
    private Message message;//这条和数据库没关系，是添加/修改后的数据

    public Operation(String type, Message message) {
        this.type = type;
        this.message = message;
    }
}
