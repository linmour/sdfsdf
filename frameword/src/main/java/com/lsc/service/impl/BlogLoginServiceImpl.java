package com.lsc.service.impl;

import com.lsc.domain.Result;
import com.lsc.domain.entity.LoginUser;
import com.lsc.domain.entity.User;
import com.lsc.domain.vo.BlogUserLoginVo;
import com.lsc.domain.vo.UserInfoVo;
import com.lsc.mapper.BlogLoginMapper;
import com.lsc.service.BlogLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.utils.BeanCopyUtils;
import com.lsc.utils.JwtUtil;
import com.lsc.utils.RedisCache;
import io.netty.util.internal.ObjectUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.lsc.constants.Constants.USER_REDIS_KEY;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
@Service
public class BlogLoginServiceImpl extends ServiceImpl<BlogLoginMapper, User> implements BlogLoginService {

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
        redisCache.setCacheObject(USER_REDIS_KEY+UserId,loginUser);

        //把用户信息封装成vo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        //再把token和用户信息封装成一个vo
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt,userInfoVo);
        return Result.okResult(blogUserLoginVo);
    }

    @Override
    public Result logout() {
        //从SecurityContextHolder中拿到用户信息
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取用户id
        Long id = user.getUser().getId();

        redisCache.deleteObject(USER_REDIS_KEY+id);
        return Result.okResult();

    }


}
