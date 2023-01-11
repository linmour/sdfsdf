package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.service.MenuService;
import com.lsc.utils.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname RoterCotroller
 * @Description
 * @Date 2023/1/8 9:42
 * @Created by linmour
 */

@RestController
public class RoterController {

    @Resource
    private MenuService menuService;

    @GetMapping("/getRouters")
    public Result getRouters(){

        return menuService.getRouters(SecurityUtils.getUserId());
    }
}
