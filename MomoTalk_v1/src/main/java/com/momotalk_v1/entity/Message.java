package com.momotalk_v1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private long uid;
    private String groupId;
    private String type;
    private String sender;
    private String content;

}
