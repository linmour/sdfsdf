package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author linmour
 * @since 2022-12-10
 */
public interface ArticleService extends IService<Article> {

    Result hotArticleList();

    Result ArticleList(Integer pageNum, Integer pageSize, Long categoryId);

    Result getArticleDetail(Long id);

    Result updateViewCount(String id);

}
