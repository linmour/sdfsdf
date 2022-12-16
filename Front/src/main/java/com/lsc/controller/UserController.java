package com.lsc.controller;


import com.lsc.annotation.SystemLog;
import com.lsc.domain.Result;
import com.lsc.domain.entity.User;
import com.lsc.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author linmour
 * @since 2022-12-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/userInfo")
    public Result userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog("更新用户信息")
    public Result updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }
}
