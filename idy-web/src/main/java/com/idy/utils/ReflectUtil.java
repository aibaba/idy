package com.idy.utils;

import java.lang.reflect.Field;

/**
 * 反射工具类
 * @author gaopengbd
 *
 */
public class ReflectUtil {

	/** 
       * 利用反射获取指定对象的指定属性 
       * @param obj 目标对象 
       * @param fieldName 目标属性 
       * @return 目标属性的值 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
       */  
      public static Object getFieldValue(Object obj, String fieldName) throws IllegalArgumentException, IllegalAccessException {  
          Object result = null;  
          Field field = ReflectUtil.getField(obj, fieldName);  
          if (field != null) {  
             field.setAccessible(true);  
             result = field.get(obj);  
          }  
          return result;  
      }  
       
      /** 
       * 利用反射获取指定对象里面的指定属性 
       * @param obj 目标对象 
       * @param fieldName 目标属性 
       * @return 目标字段 
       */  
      private static Field getField(Object obj, String fieldName) {  
          Field field = null;  
         for (Class<?> clazz=obj.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {  
             try {  
                 field = clazz.getDeclaredField(fieldName);  
                 break;  
             } catch (NoSuchFieldException e) {  
                 //返回null。  
             }  
          }  
          return field;  
      }  
  
      /** 
       * 利用反射设置指定对象的指定属性为指定的值 
       * @param obj 目标对象 
       * @param fieldName 目标属性 
        * @param fieldValue 目标值 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
       */  
      public static void setFieldValue(Object obj, String fieldName,  
             String fieldValue) throws IllegalArgumentException, IllegalAccessException {  
          Field field = ReflectUtil.getField(obj, fieldName);  
          if (field != null) {  
        	  field.setAccessible(true);  
              field.set(obj, fieldValue); 
          }  
       }  
}
