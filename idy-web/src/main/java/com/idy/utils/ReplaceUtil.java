package com.idy.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;


public class ReplaceUtil {

	private volatile static Set<String> senWord = null;
	
	

	/**
	 * 
	 * @param keyword 敏感词汇
	 * @param str 原字符
	 * @return 替换后的字符
	 */
	private static String rp(Set<String> keyword,String str){
		Iterator<String> it = keyword.iterator();
		while (it.hasNext()) {
			String a = it.next();
			StringBuffer b = new StringBuffer();
			int n = a.length();
			for (int i = 0; i < n; i++) {
				b.append("*");
			}
			if(str.contains(a)){
				str = str.replace(a, b);
			}
		}
		return str;
	}
	
	/**
	 * 
	 * @param str 原字符
	 * @return 是否有敏感词汇 
	 */
	
	public static boolean isExist(String str){
		Iterator<String> it = senWord.iterator();
		boolean flag=false;
		while (it.hasNext()) {
			String a = it.next();
			if(str.contains(a)){
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 替换
	 * @param str 原字符
	 * @return 替换后的字符
	 */
	public static String replace(String str){
		return rp(senWord,str);
	}
	
	
	public static void main(String[] args) throws IOException {
//		long time = System.currentTimeMillis();
//		Set<String> s = getKeyWord("e:/a");
//		String ss = "荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍荡妇 斯蒂芬天安门r 左派 c连根 s灼热 a買到槍";
//		Map<String,Object> map = replay(s,ss);
//		Iterator ii = s.iterator();
//		while(ii.hasNext()){
//			System.out.println(ii.next());
//		}
////		System.out.println(s.size());
//		System.out.println(map.get("s"));
//		System.out.println(map.get("flag"));
//		System.out.println(System.currentTimeMillis()-time);
		
	}

}
