package com.lsc.controller;


import com.lsc.annotation.SystemLog;
import com.lsc.domain.Result;
import com.lsc.domain.entity.User;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.exception.SystemException;
import com.lsc.service.BlogLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
@RestController

public class BlogLoginController {

    @Resource
    BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog("登录")
    public Result login(@RequestBody User user){
        String userName = user.getUserName();
        //对用户的参数进行校验
        if(!StringUtils.hasText(userName)){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("logout")
    @SystemLog("登出")
    public Result logout(){
        return blogLoginService.logout();
    }
}
