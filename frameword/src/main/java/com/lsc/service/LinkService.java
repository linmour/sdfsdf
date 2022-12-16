package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 友链 服务类
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
public interface LinkService extends IService<Link> {

    Result getAllLink();

}
