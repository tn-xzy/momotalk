package com.momotalk_v1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private String groupName;
    private String groupId;
    private String avatar;
    private String introduction;
    private Date createdTime;
}
