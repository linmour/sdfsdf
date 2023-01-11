package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.domain.dto.CategoryDto;
import com.lsc.domain.entity.Category;
import com.lsc.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname CategoryController
 * @Description
 * @Date 2023/1/9 10:12
 * @Created by linmour
 */

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;


    @GetMapping("/listAllCategory")
    public Result AllCategory(){
        return categoryService.listL();
    }


    @PreAuthorize("@ps.hasAuthority('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        categoryService.export(response);
    }

    @DeleteMapping("/{id}")
    public Result delById(@PathVariable("id")Long id){
        return categoryService.delById(id);
    }

    @GetMapping("/list")
    public Result page(Integer pageNum,Integer pageSize,String name,String status){
        return categoryService.pageL(pageNum,pageSize,name,status);
    }

    @PostMapping
    public Result add(@RequestBody Category category){
        return categoryService.add(category);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        return categoryService.getByIdL(id);
    }

    @PutMapping
    public Result update(@RequestBody Category category){
        return categoryService.updateL(category);
    }


}
