package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
