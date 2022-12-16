package com.lsc.filter;

import com.alibaba.fastjson.JSON;
import com.lsc.domain.Result;
import com.lsc.domain.entity.LoginUser;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.utils.JwtUtil;
import com.lsc.utils.RedisCache;
import com.lsc.utils.WebUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.lsc.constants.Constants.USER_REIDS_KEY;

/**
 * @Classname JwtAuthenticationTokenFilter
 * @Description
 * @Date 2022/12/12 6:21
 * @Created by linmour
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取token
        String token = request.getHeader("token");
        //这个为空说明用户未注册，就直接放行，让他就像往下面的过滤链走
        if(StringUtils.isEmpty(token)){
            filterChain.doFilter(request,response);
            //这个ruturn是不让他继续执行下面的代码
            return;
        }
        String id;
        //从token解析出id
        try {
            id = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            //这有两种情况，1.token时间过了，2.token不合法，这里不能直接抛异常，因为异常处理针对的是controller,这里是过滤器
            Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //从redis中获取数据
        LoginUser user = redisCache.getCacheObject(USER_REIDS_KEY+id);
        if (Objects.isNull(user)){
            Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入SecurityHolder，方便后续过滤连进行状态判断,
        // 三个参数的重载表示用户已认证，第一个就是用户名，第二个是密码，第三个是权限，
        // 两个参数的重载，那么默认这个用户是未认证状态
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
