package com.lsc.Handler.security;

import com.alibaba.fastjson.JSON;
import com.lsc.domain.Result;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.lsc.domain.Result.errorResult;

/**
 * @Classname AuthenticationEntryPointImpl
 * @Description
 * @Date 2022/12/12 7:16
 * @Created by linmour
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        //InsufficientAuthenticationException
        //BadCredentialsException
        //对异常进行判断
        Result result = null;
        if(e instanceof BadCredentialsException){
            result = Result.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else if(e instanceof RuntimeException){
            result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
