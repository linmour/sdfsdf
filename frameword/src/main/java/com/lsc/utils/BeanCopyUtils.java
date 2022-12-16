package com.lsc.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname BeanCopyUtils
 * @Description
 * @Date 2022/12/10 21:15
 * @Created by linmour
 */
public class BeanCopyUtils {

    private BeanCopyUtils(){}

    public static <V> V copyBean(Object source,Class<V> clazz) {
        V result = null;
        try {
            //用反射创建目标对象
            result = clazz.newInstance();
            //进行copy
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //返回结果
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list ,Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }
}
