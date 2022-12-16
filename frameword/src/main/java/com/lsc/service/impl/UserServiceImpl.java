package com.lsc.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsc.domain.Result;
import com.lsc.domain.entity.User;
import com.lsc.domain.vo.UserInfoVo;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.exception.SystemException;
import com.lsc.mapper.UserMapper;
import com.lsc.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.utils.BeanCopyUtils;
import kotlin.jvm.internal.Lambda;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

import static com.lsc.utils.SecurityUtils.getUserId;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2022-12-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public Result userInfo() {
        User user = getById(getUserId());
        UserInfoVo userInfoVo = null;
        if (Objects.nonNull(user)) {
            userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        }
        return Result.okResult(userInfoVo);

    }

    @Override
    public Result updateUserInfo(User user) {
        updateById(user);
        return Result.okResult();
    }

    @Override
    public Result register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        //对密码进行加密
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存入数据库
        save(user);
        return Result.okResult();
    }

    private boolean userNameExist(String userName){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,userName);
        return count(userLambdaQueryWrapper)>0;
    }

    private boolean nickNameExist(String nickName){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getNickName,nickName);
        return count(userLambdaQueryWrapper)>0;
    }
}
