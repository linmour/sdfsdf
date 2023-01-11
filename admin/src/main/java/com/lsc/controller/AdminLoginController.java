package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.domain.entity.User;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.exception.SystemException;
import com.lsc.service.AdminLogService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname Login
 * @Description
 * @Date 2023/1/6 18:27
 * @Created by linmour
 */
@RestController
public class AdminLoginController {

    @Resource
    private AdminLogService adminLogService;

    @PostMapping("/user/login")
    public Result login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLogService.login(user);
    }

    @PostMapping("/user/logout")
    public Result logout(){
       return adminLogService.logout();
    }
}
