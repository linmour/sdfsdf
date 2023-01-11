package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.domain.entity.User;
import com.lsc.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname UserController
 * @Description
 * @Date 2023/1/10 21:15
 * @Created by linmour
 */

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/list")
    public Result list(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status){
        return userService.listL(pageNum,pageSize,userName,phonenumber,status);
    }

    @DeleteMapping("/{id}")
    public Result delById(@PathVariable("id")Long id){
        return userService.delById(id);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        return (userService.getByIdL(id));
    }

    @PutMapping
    public Result update(@RequestBody User user){
        return userService.updateL(user);
    }
}
