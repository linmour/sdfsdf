package com.lsc.controller;


import com.lsc.domain.Result;
import com.lsc.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 友链 前端控制器
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public Result getAllLink(){
        return linkService.getAllLink();
    }
}
