package com.test.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 转换php接口数据工具类
 * @author gaopengbd
 *
 */
public class PhpArrUtil {

	public static <T> T  phpArrToObject(String phpArr, Class<T> clazz) {
		try {
			String json = JSONArray.toJSONString(phpArrToMap(phpArr));
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String phpArrToJson(String phpArr) {
		return JSONArray.toJSONString(phpArrToMap(phpArr));
	}
	
	public static Map<String, String> phpArrToMap(String phpArr) {
		if(StringUtil.isEmpty(phpArr)){
			return null;
		}
		String[] arr = phpArr.split(",");
		Map<String, String> resMap = new HashMap<String, String>();
		for(String s : arr) {
			try {
				if(s != null && s.contains("=>")) {
					String[] kv = s.split("=>");
					String key = kv[0].substring(kv[0].indexOf("'")+1, kv[0].lastIndexOf("'")).trim();
					String val = kv[1].trim().replaceAll("'", "");
					//System.out.println(key+"="+val);
					resMap.put(key, val);
				}
			} catch (Exception e) {
				//;
			}
		}
		return resMap;
	}
}
