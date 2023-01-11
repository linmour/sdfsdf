package com.lsc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 适用不同属性拷贝（可设置属性别名）
 *
 * @Description：
 * @date： 2021/6/17 11:36
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertField {
    /**
     * 在将被拷贝的属性上面，设置目标属性名
     */
    String targetName() default "";

    /**
     * 在将拷贝至改属性上面，设置源属性名
     */
    String sourceName() default "";

}
