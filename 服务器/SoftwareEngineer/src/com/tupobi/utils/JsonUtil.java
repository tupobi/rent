package com.tupobi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class JsonUtil {

   public static String bean2Json(Object bean) {
      new StringBuffer();
      JSONObject json = JSONObject.fromObject(bean);
      return json.toString();
   }

   public static Object json2Bean(String jsonString, Class beanCalss) {
      JSONObject jsonObject = JSONObject.fromObject(jsonString);
      Object bean = JSONObject.toBean(jsonObject, beanCalss);
      return bean;
   }

   public static String beanList2JsonList(List list) {
      JSONArray jsonArray = JSONArray.fromObject(list);
      return jsonArray.toString();
   }

   public static List jsonList2BeanList(String jsonString, Class beanClass) {
      JSONArray jsonArray = JSONArray.fromObject(jsonString);
      int size = jsonArray.size();
      ArrayList list = new ArrayList(size);

      for(int i = 0; i < size; ++i) {
         JSONObject jsonObject = jsonArray.getJSONObject(i);
         Object bean = JSONObject.toBean(jsonObject, beanClass);
         list.add(bean);
      }

      return list;
   }

   public static String beanListToJson(List beans) {
      StringBuffer rest = new StringBuffer();
      rest.append("[");
      int size = beans.size();

      for(int i = 0; i < size; ++i) {
         rest.append(beanToJson(beans.get(i)) + (i < size - 1?",":""));
      }

      rest.append("]");
      return rest.toString();
   }

   public static String beanToJson(Object bean) {
      JSONObject json = JSONObject.fromObject(bean);
      return json.toString();
   }

   public static String javaArray2Json(Object[] array) {
      JSONArray jsonarray = JSONArray.fromObject(array);
      return jsonarray.toString();
   }

   public static Object jsonArray2JavaArrray(String jsonArray, Class clas) {
      JsonConfig jconfig = new JsonConfig();
      jconfig.setArrayMode(2);
      jconfig.setRootClass(clas);
      JSONArray jarr = JSONArray.fromObject(jsonArray);
      return JSONSerializer.toJava(jarr, jconfig);
   }

   public static String javaMap2Json(Map map) {
      return JSONObject.fromObject(map).toString();
   }

   public static Object javaMap2Json(String jsonString, Class pojoCalss) {
      return jsonString2Object(jsonString, pojoCalss);
   }

   public static Object jsonString2Object(String jsonString, Class pojoCalss) {
      JSONObject jsonObject = JSONObject.fromObject(jsonString);
      Object pojo = JSONObject.toBean(jsonObject, pojoCalss);
      return pojo;
   }

   public static Object jsonString2Object(String jsonString, Class pojoCalss, Map classMap) {
      JSONObject jobj = JSONObject.fromObject(jsonString);
      return JSONObject.toBean(jobj, pojoCalss, classMap);
   }
}
