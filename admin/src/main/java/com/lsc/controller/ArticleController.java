package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.domain.dto.ArticleAddDto;
import com.lsc.domain.entity.Article;
import com.lsc.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname ArticleController
 * @Description
 * @Date 2023/1/9 11:11
 * @Created by linmour
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping()
    public Result addArticle(@RequestBody ArticleAddDto articleAddDto){
        return articleService.saveOrUpdateL(articleAddDto);
    }

    @GetMapping("/list")
    public Result list(Integer pageNum,Integer pageSize,String title,String summary ){
        return articleService.listL(pageNum,pageSize,title,summary);
    }

    @GetMapping("/{id}")
    public Result getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }

    @PutMapping
    public Result update(@RequestBody Article article){
        return articleService.updateL(article);
    }

    @DeleteMapping("/{id}")
    public Result delById(@PathVariable("id")Long id){
        return articleService.delById(id);
    }
}
