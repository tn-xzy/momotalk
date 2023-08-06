package com.momotalk_v1.entity;

import com.momotalk_v1.entity.constant.ResultCodes;
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
    public static Result<String> ok(String info){
        return new Result<>(ResultCodes.SUCCESS,info);
    }
    public static Result<String> fail(String info){
        return new Result<>(ResultCodes.FAIL,info);
    }
    public static Result<String> error(int errcode,String info){
        return new Result<>(ResultCodes.ERROR,errcode,info);
    }
}

