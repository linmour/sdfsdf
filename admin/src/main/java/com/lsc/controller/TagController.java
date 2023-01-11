package com.lsc.controller;


import com.lsc.domain.Result;
import com.lsc.domain.dto.TagDto;
import com.lsc.service.TagService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Resource
    TagService tagService;

    @GetMapping("/list")
    public Result list(Integer pageNum,Integer pageSize,String name,String remark){
        return tagService.listL(pageNum,pageSize,name,remark);
    }

    @PostMapping()
    public Result add(@RequestBody TagDto tagDto){
        return tagService.add(tagDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id")Long id){
        return tagService.delete(id);
    }

    @GetMapping("/{id}")
    public Result getTagById(@PathVariable("id") Long id){
        return tagService.getTagById(id);
    }

    @PutMapping
    public Result updata(@RequestBody TagDto tagDto){
        return tagService.updateByIdL(tagDto);
    }


    @GetMapping("/listAllTag")
    public Result AllTag(){
        return tagService.listL();
    }
}
