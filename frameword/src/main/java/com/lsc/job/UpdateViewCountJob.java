package com.lsc.job;

import com.lsc.domain.entity.Article;
import com.lsc.service.ArticleService;
import com.lsc.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lsc.constants.Constants.ARTICLE_VIEWCOUNT_KEY;

/**
 * @Classname UpdateViewCount
 * @Description
 * @Date 2022/12/15 19:49
 * @Created by linmour
 */

@Component
public class UpdateViewCountJob {

    @Resource
    private RedisCache redisCache;

    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0/50 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ARTICLE_VIEWCOUNT_KEY);
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);

    }
}

