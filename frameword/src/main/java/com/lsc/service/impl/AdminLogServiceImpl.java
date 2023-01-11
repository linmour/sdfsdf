package com.lsc.service.impl;

import com.lsc.domain.Result;
import com.lsc.domain.entity.LoginUser;
import com.lsc.domain.entity.User;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.service.AdminLogService;
import com.lsc.utils.JwtUtil;
import com.lsc.utils.RedisCache;
import com.lsc.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.lsc.constants.Constants.ADMIN_REDIS_KEY;

/**
 * @Classname AdminLogServiceImpl
 * @Description
 * @Date 2023/1/6 18:58
 * @Created by linmour
 */
@Service
public class AdminLogServiceImpl implements AdminLogService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;


    @Override
    public Result login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        //这个方法就会调用我们写的UserDetailsService类进行数据库查询，如果返回null说明用户名错误
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(authenticate == null){
            throw new RuntimeException("密码错误");
        }
        //获取用户id
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String UserId = loginUser.getUser().getId().toString();
        //用id生成jwt
        String jwt = JwtUtil.createJWT(UserId);
        //把用户信息存入redis
        redisCache.setCacheObject(ADMIN_REDIS_KEY+UserId,loginUser);

        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return Result.okResult(map);
    }

    @Override
    public Result logout() {
        if (redisCache.deleteObject(ADMIN_REDIS_KEY + SecurityUtils.getUserId())) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.LOGOUT_ERROR);
    }


}
