package com.adtech.rts.model.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 反射util
 * 暂时只支持String类型方法
 */
public class InvokeUtil {
    /**
     * 执行get方法
     *
     * @param fieldName 属性
     */
    public static <T> Object invokeGet(String fieldName, T t) {
        try {
            Method method = createGetMethod(fieldName, t);
            return method.invoke(t, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 执行set方法
     *
     * @param fieldName 属性
     * @param value     值
     */
    public static <T> void invokeSet(String fieldName, Object value, Field field, T t) {
        Method method = createSetMethod(fieldName, field, t.getClass());
        try {
            method.invoke(t, new Object[]{value});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 拼装get方法名字
     */
    public static <T> Method createGetMethod(String name, T t) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(name.substring(0, 1).toUpperCase());
        sb.append(name.substring(1));
        try {
            return t.getClass().getMethod(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拼装set方法名字
     */
    private static <T> Method createSetMethod(String name, Field field, Class<T> t) {
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = field.getType();
        StringBuffer sb = new StringBuffer();
        sb.append("set");
        sb.append(name.substring(0, 1).toUpperCase());
        sb.append(name.substring(1));
        try {
            return t.getMethod(sb.toString(), parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
