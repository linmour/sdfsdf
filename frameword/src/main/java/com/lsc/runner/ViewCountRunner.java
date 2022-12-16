package com.lsc.runner;

import com.lsc.domain.entity.Article;
import com.lsc.service.ArticleService;
import com.lsc.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lsc.constants.Constants.ARTICLE_VIEWCOUNT_KEY;

/**
 * @Classname ViewCountRunner
 * @Description
 * @Date 2022/12/15 17:46
 * @Created by linmour
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    ArticleService articleService;

    @Resource
    RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {

        List<Article> articles = articleService.list();
        //这个是用Strign的类型到redis里
//        articles.stream()
//                .forEach(article -> redisCache.setCacheObject("ArticleViewCount:"+article.getId(),article.getViewCount().intValue()));

        //这种是用map的形式
        Map<String, Integer> ArticleViewCount = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap(ARTICLE_VIEWCOUNT_KEY,ArticleViewCount);
    }
}
