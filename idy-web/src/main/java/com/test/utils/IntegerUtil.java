package com.test.utils;

public class IntegerUtil {

	//判断字符串是否为数字串
		public static boolean isNum(String num){
			boolean res = false;
			try {
				Integer.parseInt(num);
				res = true;
			} catch (Exception e) {
			}
			
			return res;
		}
}
