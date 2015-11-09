package com.idy.utils;

public class StringUtil {
	
	public static boolean isNull(String str){
		return str==null;
	}
	
	public static boolean isEmpty(String str){
		return isNull(str)?true:"null".equalsIgnoreCase(str)?true:str.length()==0;
	}
	/**
	 * 比较两个字符串 字面量 是否相等
	 * 若其中一者为空   则返回 false
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equal(String str1,String str2){
		return (str1==null||str2==null)?false:str1.equals(str2);
	}
	
	public static boolean equalsIgnoreCase(String str1,String str2){
		return (str1==null||str2==null)?false:str1.equalsIgnoreCase(str2);
	}
	
	public static void main(String[] args) {
		System.out.println(isEmpty("null"));
		System.out.println(equalsIgnoreCase("abc", "ABC"));
	}
}
