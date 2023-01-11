package com.lsc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lsc.domain.Result;
import com.lsc.domain.entity.User;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linmour
 * @since 2022-12-12
 */
public interface UserService extends IService<User> {

    Result userInfo();

    Result updateUserInfo(User user);

    Result register(User user);

    Result listL(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    Result delById(Long id);


    Result getByIdL(Long id);

    Result updateL(User user);
}
