package com.momotalk_v1.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import com.momotalk_v1.entity.Result;
import com.momotalk_v1.entity.constant.ErrCodes;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 全局异常拦截
    @ExceptionHandler(NotLoginException.class)
    public Result<String> handlerException(NotLoginException e) {
        return Result.error(ErrCodes.NOLOGIN,"用户未登录");
    }
}
