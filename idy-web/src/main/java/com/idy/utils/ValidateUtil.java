package com.idy.utils;

/**
 * 验证的工具类
 * @author gaopengbd
 *
 */
public class ValidateUtil {
	
	
	/**
	 * 书名只可以是 [汉字 /英文/ , /:]
	 * @param bookName
	 * @return
	 */
	public static boolean checkName(String bookName){
		return true;
	}
	
	/**
	 * 校验身份证
	 * @param idCard
	 * @return
	 */
	public static boolean checkIdentityCard(String idCard){
		return true;
	}
	
	/**
	 * 
	 * @param authorRelName
	 * @return
	 */
	public static boolean authorRelName(String authorRelName){
		return true;
	}
	
	public static boolean authorPenName(String authorPenName){
		return true;
	}
	
	/**
	 * 过滤敏感词汇
	 * @param key
	 * @return
	 */
	public static String sensitiveWordsFilter(String content){
		//替换敏感词库
		
		return content;
	}
}
