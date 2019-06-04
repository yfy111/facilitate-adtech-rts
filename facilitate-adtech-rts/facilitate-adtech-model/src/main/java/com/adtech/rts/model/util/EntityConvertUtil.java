package com.adtech.rts.model.util;

import com.adtech.rts.model.annotation.ConvertProperty;
import lombok.Data;
import org.bson.Document;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 数据转换util
 * 暂时只支持String类型
 */
public class EntityConvertUtil {


    /**
     * 判断字段
     *
     * @param filedName
     * @param names
     * @return
     */
    public static boolean hasFiled(String filedName, Field[] names) {
        for (Field f : names) {
            if (f.getName().equals(filedName)) return true;
        }
        return false;
    }

    /**
     * 传入list
     * 转成对应clazz List
     *
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertForEntity(List list, Class<T> clazz) {
        List<T> list1 = new ArrayList<>();
        if (list1 == null) return list;
        try {
            for (int i = 0; i < list.size(); i++) {
                list1.add(transBean2Bean(list.get(i), clazz.newInstance()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list1;
    }



    /**
     * 传入list
     * 转成对应clazz List
     *
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertForEntity(List list, Class<T> clazz,Map<String, String> fields) {
        List<T> list1 = new ArrayList<>();
        if (list1 == null) return list;
        try {
            for (int i = 0; i < list.size(); i++) {
                list1.add(canverted(list.get(i),clazz.newInstance(),fields));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list1;
    }




    private <T> T getNewInstance(Class<T> clazz) {
        T t1 = null;
        try {
            t1 = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t1;
    }


    /**
     * obj 转换成 t1
     * 需要标识ConvertProperty
     *
     * @param t1
     * @param <T>
     * @return
     */
    private static <T> T canverted(Object obj, T t1) throws Exception {
        Field[] T2fields = t1.getClass().getDeclaredFields();
        for (Field f : T2fields) {
            ConvertProperty property = f.getAnnotation(ConvertProperty.class);
            if (property != null) {
                if (StringUtils.isEmpty(property.columnName()))
                    InvokeUtil.invokeSet(f.getName(), InvokeUtil.invokeGet(property.columnName(), obj)
                            , t1.getClass().getDeclaredField(f.getName()), t1);
            }
        }
        return t1;
    }


    /**
     * obj 转换成 t1
     * fields里存放所需转换字段
     * @param t1
     * @param <T>
     * @return
     */
    private static <T> T canverted(Object obj, T t1, Map<String, String> fields) throws Exception {
        Field[] T2fields = t1.getClass().getDeclaredFields();
        for (Field f : T2fields) {
            if (!StringUtils.isEmpty(fields)){
                if(fields.containsKey(f.getName())){
                    InvokeUtil.invokeSet(f.getName(), InvokeUtil.invokeGet(fields.get(f.getName()), obj)
                            , t1.getClass().getDeclaredField(f.getName()), t1);
                }
            }
        }
        return t1;
    }


    /**
     * obj 转换成 t1
     * 无需标识ConvertProperty
     * 针对mongodb Document
     *
     * @param t1
     * @param <T>
     * @return
     */
    public static <T> T canverted1(Document obj, T t1) throws Exception {
        List<Field> fieldList = new ArrayList<>();
        Field[] T2fields = null;
        Class tempClass = t1.getClass();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            if (tempClass.getSuperclass().getName().equals("java.lang.Object")) break;
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
            T2fields = tempClass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(T2fields));
        }
        for (Field f : fieldList) {
            InvokeUtil.invokeSet(f.getName(), obj.get(f.getName())
                    , t1.getClass().getDeclaredField(f.getName()), t1);
        }
        return t1;
    }

    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static <T> T transMap2Bean(Map<String, Object> map, T t) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(t, value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 通过内省反射
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 实体转换
     *
     * @param obj
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T transBean2Bean(Object obj, T t) {
        return transMap2Bean(transBean2Map(obj), t);
    }


    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.setAge("1");
        dog.setName("2");
        Cat cat = new Cat();
        Cat t = transMap2Bean(transBean2Map(dog), cat);
        System.err.println(t.getAge());
    }

}

@Data
class Dog {
    private String name;
    private String age;
}

@Data
class Cat {
    private String name;
    private String age;
}
