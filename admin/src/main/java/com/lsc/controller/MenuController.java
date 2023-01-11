package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.domain.entity.Menu;
import com.lsc.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname MenuController
 * @Description
 * @Date 2023/1/10 9:21
 * @Created by linmour
 */

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("list")
    public Result List(String status,String menuName){
        return menuService.listL(status,menuName);
    }

    @PostMapping()
    public Result addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public Result getMenuById(@PathVariable("id")Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping("")
    public Result update(@RequestBody Menu menu){
        return menuService.updateL(menu);
    }

    @DeleteMapping("/{menuId}")
    public Result delById(@PathVariable("menuId")Long id){
        return menuService.delById(id);
    }

    @GetMapping("/treeselect")
    public Result treeSelect(){
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public Result roleMenuTreeselect(@PathVariable("id")Long id){
        return menuService.roleMenuTreeselect(id);
    }
}
