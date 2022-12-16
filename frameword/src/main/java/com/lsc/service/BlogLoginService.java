package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
public interface BlogLoginService extends IService<User> {

    Result login(User user);

    Result logout();
}
