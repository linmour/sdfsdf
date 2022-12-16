package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.domain.Result;
import com.lsc.domain.entity.Article;
import com.lsc.domain.entity.Category;
import com.lsc.domain.vo.ArticleDetailVo;
import com.lsc.domain.vo.ArticleListVo;
import com.lsc.domain.vo.HotArticleVo;
import com.lsc.domain.vo.PageVo;
import com.lsc.mapper.ArticleMapper;
import com.lsc.service.ArticleService;
import com.lsc.service.CategoryService;
import com.lsc.utils.BeanCopyUtils;
import com.lsc.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lsc.constants.Constants.ARTICLE_STATUS_NORMAL;
import static com.lsc.constants.Constants.ARTICLE_VIEWCOUNT_KEY;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2022-12-10
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    /**
     * 需要查询浏览量最高的前10篇文章的信息。要求展示文章标题和浏览量。把能让用户自己点击跳转到具体的文章详情进行浏览。
     * 注意：不能把草稿展示出来，不能把删除了的文章查询出来。要按照浏览量进行降序排序。
     * @return
     */
    @Override
    public Result hotArticleList() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //首先文章要是发布状态 status = 0
        lambdaQueryWrapper.eq(Article::getStatus ,ARTICLE_STATUS_NORMAL)
                            //按浏览量降序排序
                          .orderByDesc(Article::getViewCount)
                            //查询前十个
                          .last("LIMIT 10");
        List<Article> articles = list(lambdaQueryWrapper);
        //把查询到的数据经过vo的封装在返回

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return Result.okResult(vs);
    }


    /**
     * 在首页和分类页面都需要查询文章列表。
     *
     * 	首页：查询所有的文章
     *
     * 	分类页面：查询对应分类下的文章
     *
     * 	要求：①只能查询正式发布的文章 ②置顶的文章要显示在最前面
     */
    @Resource
    private CategoryService categoryService;


    @Resource
    private RedisCache redisCache;

    @Override
    public Result ArticleList(Integer pageNum, Integer pageSize, Long categoryId) {

        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //判断是否有分类的id，有的话就查找出这个分类下的文章
        lambdaQueryWrapper.eq( Objects.nonNull(categoryId) && categoryId>0 ,Article::getCategoryId,categoryId);
        //查找这个分类下 发布的文章
        lambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //按是否置顶排序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //对文章进行分页
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        //这里面就是所有的文章
        List<Article> articles = page.getRecords();


// TODO
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ARTICLE_VIEWCOUNT_KEY);
        List<Article> articless = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        for (Article article : articles) {
            for (Article article1 : articless) {
                if (article.getId() == article1.getId()){
                    article.setViewCount(article1.getViewCount());
                }
            }

        }

        //查询categoryName
        articles.stream()
                //  把分类名设置到vo对象，等下返回                       //获取分类名
                .map(article -> article.setCategoryName( categoryService.getById(article.getCategoryId()).getName() ))
                .collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        //用分页类进行封装然后在返回
        return Result.okResult(pageVo);
    }

    @Override
    public Result getArticleDetail(Long id) {
        //获取文章信息
        Article article = getById(id);

        //从redis查取浏览量
        Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEWCOUNT_KEY, id.toString());
        article.setViewCount(viewCount.longValue());

        //封装成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据文章的分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return Result.okResult(articleDetailVo);

    }

    @Override
    public Result updateViewCount(String id) {
        redisCache.incrementCacheMapValue(ARTICLE_VIEWCOUNT_KEY,id,1);
        return Result.okResult();
    }
}
