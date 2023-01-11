package com.lsc.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.constants.Constants;
import com.lsc.domain.Result;
import com.lsc.domain.dto.CategoryDto;
import com.lsc.domain.entity.Article;
import com.lsc.domain.entity.Category;
import com.lsc.domain.vo.CatagoryVo;
import com.lsc.domain.vo.ExcelCategoryVo;
import com.lsc.domain.vo.PageVo;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.mapper.CategoryMapper;
import com.lsc.service.ArticleService;
import com.lsc.service.CategoryService;
import com.lsc.utils.BeanCopyUtils;
import com.lsc.utils.WebUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
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
    private ArticleService articleService;
    @Resource
    private CategoryService categoryService;

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

    @Override
    public Result listL() {
        LambdaUpdateWrapper<Category> categoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        categoryLambdaUpdateWrapper.eq(Category::getStatus,0)
                .eq(Category::getDelFlag,0);
        List<Category> list = categoryService.list(categoryLambdaUpdateWrapper);
        return Result.okResult(BeanCopyUtils.copyBeanList(list, CategoryDto.class));

    }

    @Override
    public void export(HttpServletResponse response) {
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);

            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常也要响应json
            Result result = Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @Override
    public Result delById(Long id) {

        LambdaUpdateWrapper<Category> categoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        categoryLambdaUpdateWrapper.eq(Category::getId,id)
                .set(Category::getDelFlag,1);
        boolean update = categoryService.update(categoryLambdaUpdateWrapper);
        if (update)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result pageL(Integer pageNum, Integer pageSize, String name, String status) {

        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(StringUtils.hasText(status),Category::getStatus,status)
                .like(StringUtils.hasText(name),Category::getName,name);
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,categoryLambdaQueryWrapper);
        return Result.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }

    @Override
    public Result add(Category category) {
        save(category);
        return Result.okResult();
    }

    @Override
    public Result getByIdL(Long id) {

        Category category = getById(id);
        return Result.okResult(category);
    }

    @Override
    public Result updateL(Category category) {
        boolean b = updateById(category);
        if (b)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }
}
