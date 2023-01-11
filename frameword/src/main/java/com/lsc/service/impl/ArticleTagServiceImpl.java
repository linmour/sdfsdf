package com.lsc.service.impl;

import com.lsc.domain.entity.ArticleTag;
import com.lsc.mapper.ArticleTagMapper;
import com.lsc.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章标签关联表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2023-01-09
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
