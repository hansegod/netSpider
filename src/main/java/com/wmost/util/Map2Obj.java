/**
@description  map与对象互转工具类
@author hanse/irene
@data	2017-04-08	00:00	初稿
		2017-04-21	00:00	整理代码
		
		
**/


package com.wmost.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Map2Obj {
/****方式1****/
//    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {    
//        if (map == null){
//            return null;
//        }
//  
//        Object obj = null;
//		try {
//			obj = beanClass.newInstance();
//			org.apache.commons.beanutils.BeanUtils.populate(obj, map); 
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		
//        return obj;  
//    }
//    public static Map<?, ?> objectToMap(Object obj) {  
//        if(obj == null){
//            return null;   
//        }
//        
//        return new org.apache.commons.beanutils.BeanMap(obj);  
//    }

	
/****方式2****/	
//    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {    
//        if (map == null)   
//            return null;    
//  
//        Object obj = null;
//		try {
//			obj = beanClass.newInstance();
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		}  
//  
//        BeanInfo beanInfo = null;
//		try {
//			beanInfo = Introspector.getBeanInfo(obj.getClass());
//		} catch (IntrospectionException e) {
//			e.printStackTrace();
//		}    
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
//        for (PropertyDescriptor property : propertyDescriptors) {  
//            Method setter = property.getWriteMethod();    
//            if (setter != null) {  
//                try {
//					setter.invoke(obj, map.get(property.getName()));
//				} catch (IllegalAccessException | IllegalArgumentException
//						| InvocationTargetException e) {
//					e.printStackTrace();
//				}   
//            }  
//        }  
//  
//        return obj;  
//    }    
//    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
//        if(obj == null)  
//            return null;      
//  
//        Map<String, Object> map = new HashMap<String, Object>();   
//  
//        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
//        for (PropertyDescriptor property : propertyDescriptors) {    
//            String key = property.getName();    
//            if (key.compareToIgnoreCase("class") == 0) {   
//                continue;  
//            }  
//            Method getter = property.getReadMethod();  
//            Object value = getter!=null ? getter.invoke(obj) : null;  
//            map.put(key, value);  
//        }    
//  
//        return map;  
//    }

	
	
/****方式3****/
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {    
        if (map == null)  
            return null;    
  
        Object obj = null;
		try {
			obj = beanClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}  
  
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
  
            field.setAccessible(true);    
            try {
				field.set(obj, map.get(field.getName()));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}   
        }   
  
        return obj;    
    }
    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null){    
            return null;    
        }   
  
        Map<String, Object> map = new HashMap<String, Object>();    
  
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            map.put(field.getName(), field.get(obj));  
        }    
  
        return map;  
    }
}
