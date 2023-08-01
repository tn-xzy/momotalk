package com.momotalk_v1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    int code;
    int errcode;
    String errinfo;
    T data;

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, int errcode, String errinfo) {
        this.code = code;
        this.errcode = errcode;
        this.errinfo = errinfo;
    }
}

