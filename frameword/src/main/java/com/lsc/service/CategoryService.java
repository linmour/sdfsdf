package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.dto.CategoryDto;
import com.lsc.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
public interface CategoryService extends IService<Category> {

    Result getCategorylist();

    Result listL();

    void export(HttpServletResponse response);

    Result delById(Long id);

    Result pageL(Integer pageNum, Integer pageSize, String name, String status);

    Result add(Category category);

    Result getByIdL(Long id);

    Result updateL(Category category);
}
