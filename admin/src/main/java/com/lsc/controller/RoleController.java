package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.domain.dto.ChangeStatusDto;
import com.lsc.domain.entity.Role;
import com.lsc.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname RoleController
 * @Description
 * @Date 2023/1/10 10:37
 * @Created by linmour
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public Result list(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.listL(pageNum,pageSize,roleName,status);
    }

    @PutMapping("/changeStatus")
    public Result changStatus(@RequestBody ChangeStatusDto changStatusDto){
        return roleService.changeStatus(changStatusDto);
    }

    @PostMapping
    public Result add(@RequestBody Role role){
        return roleService.add(role);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable("id")Long id){
        return roleService.getByIdL(id);
    }

    @PutMapping
    public Result update(@RequestBody Role role){
        return roleService.updateL(role);
    }

    @DeleteMapping("/{id}")
    public Result delById(@PathVariable Long id){
        return roleService.delById(id);
    }
}
