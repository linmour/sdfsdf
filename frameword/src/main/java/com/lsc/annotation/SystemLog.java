package com.lsc.annotation;

import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname SystemLog
 * @Description
 * @Date 2022/12/15 14:33
 * @Created by linmour
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemLog {
    String value();
}
