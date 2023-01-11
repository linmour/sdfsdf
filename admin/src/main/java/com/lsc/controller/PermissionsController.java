package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.domain.entity.User;
import com.lsc.service.PermissionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname PermissionsController
 * @Description
 * @Date 2023/1/6 21:28
 * @Created by linmour
 */
@RestController
public class PermissionsController {

    @Resource
    private PermissionsService permissionsService;

    @GetMapping("/getInfo")
    public Result getInfo(){
        return permissionsService.getInfo();
    }
}
