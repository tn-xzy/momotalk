package com.momotalk_v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {
    @JsonIgnore
    private String hash;
    @JsonIgnore
    private String savename;
    private String size;
    private String unit;
}
