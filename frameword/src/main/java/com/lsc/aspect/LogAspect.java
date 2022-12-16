package com.lsc.aspect;

import com.alibaba.fastjson.JSON;
import com.lsc.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * @Classname LogAspect
 * @Description
 * @Date 2022/12/15 14:35
 * @Created by linmour
 */
@Aspect
@Slf4j
@Component

public class LogAspect {

    //切点，这里定义的是标注了SystemLog这个自定义注解的执行
    @Pointcut("@annotation(com.lsc.annotation.SystemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object ret;
        //这个相当于调用我们要增强的方法，ret就是那个方法的返回值
        try {
            //方法执行前进行增强
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            // 结束后换行
            log.info("=============END============="+System.lineSeparator());
        }
        return ret;
    }
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        //获取到请求信息，从里面拿到访问的url
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog );
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getName(),((MethodSignature)joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));



    }



    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        //method就相当于被增强方法的封装形式，我们可以从里面拿到被增强方法的所有信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(SystemLog.class);

    }


}
