package com.lsc.service;


import com.lsc.domain.Result;
import com.lsc.domain.entity.User;

/**
 * @Classname AdminLogService
 * @Description
 * @Date 2023/1/6 18:57
 * @Created by linmour
 */
public interface AdminLogService {

    Result login(User user);

    Result logout();

}
