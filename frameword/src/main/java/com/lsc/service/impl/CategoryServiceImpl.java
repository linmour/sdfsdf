package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.constants.Constants;
import com.lsc.domain.Result;
import com.lsc.domain.entity.Article;
import com.lsc.domain.entity.Category;
import com.lsc.domain.vo.CatagoryVo;
import com.lsc.mapper.CategoryMapper;
import com.lsc.service.ArticleService;
import com.lsc.service.CategoryService;
import com.lsc.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    ArticleService articleService;

    @Override
    public Result getCategorylist() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查询发布状态的文章
        lambdaQueryWrapper.eq(Article::getStatus,Constants.ARTICLE_STATUS_NORMAL);
        //用stream进行去重
        List<Article> list = articleService.list(lambdaQueryWrapper);
        Set<Long> Ids = list.stream()
                //拿到里面id
                .map(Article::getCategoryId)
                //用set集合直接去重
                .collect(Collectors.toSet());

        //把查到的id集合放进去，找出相对应的分类
        List<Category> categories = listByIds(Ids);
        //判断分类的状态 0为正常
        List<Category> categoryList = categories.stream()
                .filter(categorie -> Constants.CATEGORY_STATUS_NORMAL.equals(categorie.getStatus()))
                .collect(Collectors.toList());
        List<CatagoryVo> catagoryVos = BeanCopyUtils.copyBeanList(categoryList, CatagoryVo.class);
        return Result.okResult(catagoryVos);
    }
}
