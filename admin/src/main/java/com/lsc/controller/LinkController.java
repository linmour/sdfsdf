package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.domain.entity.Link;
import com.lsc.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname LinkController
 * @Description
 * @Date 2023/1/11 11:39
 * @Created by linmour
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/list")
    public Result list(Integer pageNum,Integer pageSize,String name,String status){
        return linkService.listL(pageNum,pageSize,name,status);
    }

    @PostMapping
    public Result add(@RequestBody Link link){
        return linkService.add(link);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        return linkService.getByIdL(id);
    }

    @PutMapping
    public Result update(@RequestBody Link link){
        return linkService.updateL(link);
    }

    @DeleteMapping("/{id}")
    public Result del(@PathVariable Long id){
        return  linkService.del(id);
    }

    @PutMapping("/changeLinkStatus")
    public Result changeLinkStatus(@RequestBody Link link){
        return linkService.changeLinkStatus(link);
    }

}
