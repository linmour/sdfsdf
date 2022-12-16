package com.lsc.controller;


import com.lsc.domain.Result;
import com.lsc.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public Result getCategorylist(){
        return categoryService.getCategorylist();
    }
}
