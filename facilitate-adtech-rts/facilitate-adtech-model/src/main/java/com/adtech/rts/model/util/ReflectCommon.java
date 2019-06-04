//package com.adtech.rts.model.util;
//
//
//import com.adtech.medical.DataInfo;
//import com.adtech.model.DomainHierarchy;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import java.beans.BeanInfo;
//import java.beans.Introspector;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ReflectCommon {
//
//    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
//        if (map == null || obj == null) {
//            return;
//        }
//        try {
//            BeanUtils.populate(obj, map);
//        } catch (Exception e) {
//            System.out.println("transMap2Bean2 Error " + e);
//        }
//    }
//
//    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
//    public static void transMap2Bean(Map<String, Object> map, Object obj) {
//
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//
//            for (PropertyDescriptor property : propertyDescriptors) {
//                String key = property.getName();
//
//                if (map.containsKey(key)) {
//                    Object value = map.get(key);
//                    // 得到property对应的setter方法
//                    Method setter = property.getWriteMethod();
//                    setter.invoke(obj, value);
//                }
//
//            }
//
//        } catch (Exception e) {
//            System.out.println("transMap2Bean Error " + e);
//        }
//
//        return;
//
//    }
//
//    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
//
//    //里面不包含空的值
//    public static Map<String, Object> transBean2FullMap(Object obj) {
//
//        if(obj == null){
//            return null;
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            for (PropertyDescriptor property : propertyDescriptors) {
//                String key = property.getName();
//
//                // 过滤class属性
//                if (!key.equals("class")) {
//                    // 得到property对应的getter方法
//                    Method getter = property.getReadMethod();
//                    Object value = getter.invoke(obj);
//
//
//                      map.put(key, value);
//
//
//                }
//
//            }
//        } catch (Exception e) {
//            System.out.println("transBean2Map Error " + e);
//        }
//
//        return map;
//
//    }
//
//
//
//
//    public static DomainHierarchy createDomainHierarchy(Object obj, String name, int index, String parentPath,boolean isArray)
//    {
//        DomainHierarchy child=new DomainHierarchy();
//        child.setArray(isArray);
//        child.setIndex(index);
//        child.setName(name);
//        if(!StringUtils.isEmpty(parentPath)) {
//            child.setPath(parentPath + "." + name);
//        }else
//        {
//            child.setPath(name);
//        }
//
//        Map<String, Object> dic =ReflectCommon.transBean2Map(obj);
//        Map<String,Object> leaf=new HashMap<>();
//        Map<String,Object> leafList=new HashMap<>();
//        List<DomainHierarchy> clist=new ArrayList<>();
//        for (Map.Entry<String, Object> entry : dic.entrySet()) {
//            if (entry.getValue()!=null&&entry.getValue() instanceof ArrayList) {
//                int childindex=0;
//                String full=child.getPath()+"."+entry.getKey();
//                String clz=ClassMap.getClassFullPath(full);
//                if(StringUtils.isEmpty(clz))
//                {
//                    System.out.println(full);
//                }
//                if(((ArrayList)entry.getValue()).size()>0) {
//                    if(!clz.equals("java.lang.String")) {
//                        for (Object o : (ArrayList) entry.getValue()) {
//                            DomainHierarchy s = createDomainHierarchy(o, entry.getKey(), childindex, child.getPath(), true);
//                            childindex++;
//                            clist.add(s);
//                        }
//                    }
//                    else {
//                        leafList.put(entry.getKey(),entry.getValue());
//                    }
//                }
//                else
//                {
//                    if(!clz.equals("java.lang.String")) {
//                        Object temp = ReflectCommon.createBaseInstance(clz);
//                        DomainHierarchy s = createDomainHierarchy(temp, entry.getKey(), childindex, child.getPath(), true);
//                        clist.add(s);
//                    }
//                    else
//                    {
//                        leafList.put(entry.getKey(),null);
//                    }
//                }
//
//            }
//            else  if (entry.getValue()!=null&&entry.getValue() instanceof DataInfo)
//            {
//                DomainHierarchy s=createDomainHierarchy(entry.getValue(),entry.getKey(),0,child.getPath(),false);
//                clist.add(s);
//            }
//            else
//            {
//                if(!entry.getKey().equals("_id"))
//                {
//                    leaf.put(entry.getKey(),entry.getValue());
//                }
//            }
//        }
//        child.setChildren(clist);
//        child.setLeaf(leaf);
//        child.setLeafList(leafList);
//        return child;
//    }
//
//    public static Map<String, Object> transBean2Map(Object obj) {
//
//        if(obj == null){
//            return null;
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            for (PropertyDescriptor property : propertyDescriptors) {
//                String key = property.getName();
//
//                // 过滤class属性
//                if (!key.equals("class")) {
//                    // 得到property对应的getter方法
//                    Method getter = property.getReadMethod();
//                    Object value = getter.invoke(obj);
//                    if(value!=null&&value instanceof ArrayList)
//                    {
//
//                            List<Map<String, Object>> test=ListBeanToListMap((List)value);
//                            map.put(key, test);
//
//
//                    }else
//                    {
//                        map.put(key, value);
//                    }
//
//                }
//
//            }
//        } catch (Exception e) {
//            System.out.println("transBean2Map Error " + e);
//        }
//
//        return map;
//
//    }
//
//
//    public static Object createBaseInstance(String packagePath,String clsName)
//    {
//        Class c1 = null;
//        try {
//            c1 = Class.forName(packagePath+"."+clsName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        //创建此Class对象所表示类的一个新实例,
//        //newInstance方法调用的是Person的空参数构造方法
//        Object o = null;
//        try {
//            o = c1.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return o;
//    }
//    public static Object createBaseInstance(String fullPath)
//    {
//        Class c1 = null;
//        try {
//            c1 = Class.forName(fullPath);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        //创建此Class对象所表示类的一个新实例,
//        //newInstance方法调用的是Person的空参数构造方法
//        Object o = null;
//        try {
//            o = c1.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return o;
//    }
//
//
//
//
//    /**
//     * 将List<Map<String,Object>>转换成List<javaBean>
//     *
//     * @param listm
//     * @param obj
//     * @return
//     * @throws InvocationTargetException
//     * @throws IllegalArgumentException
//     * @throws IllegalAccessException
//     * @throws SecurityException
//     * @throws NoSuchMethodException
//     */
//    public static List<Object> ListMapToListBean(List<Map<String, Object>> listm, Object obj)
//            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
//            InvocationTargetException {
//
//        List<Object> list = new ArrayList<Object>();
//        // 循环遍历出map对象
//        for (Map<String, Object> m : listm) {
//            // 调用将map转换为JavaBean的方法
//            Object objs =new Object();
//             transMap2Bean(m,objs );
//            // 添加进list集合
//            list.add(objs);
//        }
//
//        return list;
//    }
//
//    /**
//     * 将list<javabean>转换为List<Map>
//     *
//     * @param list
//     * @return
//     * @throws NoSuchMethodException
//     * @throws SecurityException
//     * @throws IllegalAccessException
//     * @throws IllegalArgumentException
//     * @throws InvocationTargetException
//     */
//    public static List<Map<String, Object>> ListBeanToListMap(List<Object> list)  {
//
//        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
//
//        for (Object ob : list) {
//
//            listmap.add(transBean2Map(ob));
//        }
//
//        return listmap;
//    }
//
//}
