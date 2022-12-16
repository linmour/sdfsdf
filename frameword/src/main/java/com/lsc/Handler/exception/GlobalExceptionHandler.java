package com.lsc.Handler.exception;

import com.lsc.domain.Result;
import com.lsc.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Classname GlobalExceptionHandler
 * @Description
 * @Date 2022/12/13 6:31
 * @Created by linmour
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public Result systemExceptionHandler(SystemException e){
        //打印异常
        log.error("出现异常",e);
        //从异常中拿到code和msg封装后返回给前端
        return Result.errorResult(e.getCode(),e.getMsg());
    }
}
