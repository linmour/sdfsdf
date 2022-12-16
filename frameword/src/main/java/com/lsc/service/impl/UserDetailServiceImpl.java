package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsc.domain.entity.LoginUser;
import com.lsc.domain.entity.User;
import com.lsc.mapper.BlogLoginMapper;
import com.lsc.service.BlogLoginService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Classname UserDetailServiceImpl
 * @Description
 * @Date 2022/12/11 22:48
 * @Created by linmour
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    BlogLoginMapper blogLoginMapper;


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            //根据用户名查询用户信息
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserName,username);
            User user = blogLoginMapper.selectOne(queryWrapper);
            //判断是否查到用户  如果没查到抛出异常
            if(Objects.isNull(user)){
                throw new RuntimeException("用户不存在");
            }
            //返回用户信息
            // TODO 查询权限信息封装
            return new LoginUser(user);
        }
}
