package com.lsc.controller;


import com.lsc.annotation.SystemLog;
import com.lsc.domain.Result;
import com.lsc.domain.entity.Article;
import com.lsc.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author linmour
 * @since 2022-12-10
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    //获取热门文章
    @GetMapping("/hotArticleList")
    @SystemLog("热门文章列表")
    public Result test(){
        return articleService.hotArticleList();
    }

    //获取所有文章并进行分页
    @GetMapping("/articleList")
    @SystemLog("获取所有文章并进行分页")
    public Result articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.ArticleList(pageNum,pageSize,categoryId);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog("修改浏览数")
    public Result updateViewCount(@PathVariable String id){
        return articleService.updateViewCount(id);
    }
    @GetMapping("/{id}")
    @SystemLog("查看文章全文")
    public Result getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}
