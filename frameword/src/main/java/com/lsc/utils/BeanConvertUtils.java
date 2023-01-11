package com.lsc.utils;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lsc.annotation.ConvertField;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 处理不同对象的不同属性映射
 *
 * @Description：
 * @date： 2021/6/17 11:49
 */
@Slf4j
public class BeanConvertUtils {

    public BeanConvertUtils() {
    }

    /**
     * 拷贝一个对象的属性至另一个对象。支持两个对象之间不同属性名称进行拷贝，使用注解{@link ConvertField}
     *
     * @param source       源对象 使用注解{@link ConvertField#targetName()}
     * @param targetClazz  目标对象 使用注解{@link ConvertField#sourceName()}
     */
    public static <T> T convert(@Nullable Object source, Class<T> targetClazz) {
        if (Objects.isNull(source)) {
            return null;
        }
        Map<String, Object> sourceFieldKeyAndValueMap = Maps.newHashMap();
        // 生成目标类对象
        T instance = org.springframework.beans.BeanUtils.instantiateClass(targetClazz);
        //生成源bean的属性及其值的字典
        createSourceFieldAndValue(source, sourceFieldKeyAndValueMap, source.getClass());
        //设置目标bean的属性值
        setTargetFieldOfValue(instance, sourceFieldKeyAndValueMap, instance.getClass());
        return instance;
    }

    /**
     * 拷贝一个对象集合的属性至另一个对象集合。支持两个对象之间不同属性名称进行拷贝，使用注解{@link ConvertField}
     *
     * @param source      源对象 使用注解{@link ConvertField#targetName()}
     * @param targetClazz 目标对象 使用注解{@link ConvertField#sourceName()}
     */
    public static <T> List<T> converts(@Nullable Collection<?> sourceList, Class<T> targetClazz) {
        if (null != sourceList && !sourceList.isEmpty()) {
            List<T> outList = Lists.newArrayList();
            for (Object source : sourceList) {
                T convert = convert(source, targetClazz);
                if (!Objects.isNull(convert)) {
                    outList.add(convert);
                }
            }
            return outList;
        }
        return CollUtil.newArrayList();
    }

    /**
     * 生成需要被拷贝的属性字典 属性-属性值（递归取父类属性值）
     *
     * @param source                 待拷贝的bean
     * @param sourceFieldKeyAndValueMap 存放待拷贝的属性和属性值
     * @param beanClass                  待拷贝的class[可能是超类的class]
     */
    private static void createSourceFieldAndValue(Object source, Map<String, Object> sourceFieldKeyAndValueMap, Class<?> beanClass) {
        // 如果不存在超类，那么跳出循环
        if (Objects.isNull(beanClass.getSuperclass())) {
            return;
        }
        Field[] originFieldList = beanClass.getDeclaredFields();
        for (Field field : originFieldList) {
            try {
                ConvertField annotation = field.getAnnotation(ConvertField.class);
                // 获取属性上的注解。如果不存在，使用属性名；存在使用注解名
                String targetName;
                if (Objects.isNull(annotation)) {
                    targetName = field.getName();
                } else {
                    targetName = annotation.targetName();
                }
                // 初始化当前属性的描述器，获取当前传入属性的（getter/setter）方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), beanClass);
                // 获取当前属性的get方法
                Method method = propertyDescriptor.getReadMethod();
                //设置值
                Object value = method.invoke(source);
                //设置值
                sourceFieldKeyAndValueMap.put(targetName, value);
            } catch (IntrospectionException e) {
                log.warn("【源对象】异常:" + field.getName() + "不存在对应的get方法，无法参与拷贝！");
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("【源对象】异常:" + field.getName() + "的get方法执行失败！");
            }
        }
        // 递归，遍历设置超类
        createSourceFieldAndValue(source, sourceFieldKeyAndValueMap, beanClass.getSuperclass());
    }

    /**
     * 设置目标对象属性
     *
     * @param propertyDescriptor         属性描述器，获取当前传入属性的（getter/setter）方法
     * @param target                     目标容器bean
     * @param sourceFieldKeyAndValueMap 待拷贝的属性和属性值
     * @param beanClass                  待设置的class[可能是超类的class]
     */
    private static void setTargetFieldOfValue(Object target, Map<String, Object> sourceFieldKeyAndValueMap, Class<?> beanClass) {
        // 如果不存在超类，那么跳出循环
        if (Objects.isNull(beanClass.getSuperclass())) {
            return;
        }
        Field[] targetFieldList = beanClass.getDeclaredFields();
        for (Field field : targetFieldList) {
            try {
                ConvertField annotation = field.getAnnotation(ConvertField.class);
                // 获取属性上的注解。如果不存在，使用属性名；存在使用注解名
                String originName;
                if (Objects.isNull(annotation)) {
                    originName = field.getName();
                } else {
                    originName = annotation.sourceName();
                }
                // 初始化当前属性的描述器，获取当前传入属性的（getter/setter）方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), beanClass);
                // 获取当前属性的set方法
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(target, sourceFieldKeyAndValueMap.get(originName));
            } catch (IntrospectionException e) {
                log.warn("【目标对象】异常:" + field.getName() + "不存在对应的set方法，无法参与拷贝！");
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("【目标对象】异常:" + field.getName() + "的set方法执行失败！");
            }
        }
        // 递归，遍历设置超类
        setTargetFieldOfValue(target, sourceFieldKeyAndValueMap, beanClass.getSuperclass());
    }

}
