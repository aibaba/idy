package com.test.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @Description TODO
 * @author gaopengbd
 * @date 2014年11月25日 下午1:29:47 
 * @version V1.0
 */
public class Base64Util {

	public static Base64 base64 = new Base64();
	
	/**
	 * 使用Base64加密算法加密字符串
	 * 
	 * @param plainText
	 * @return
	 */
	public static String encodeStr(String plainText) {
		byte[] b = plainText.getBytes();
		b = base64.encode(b);
		String s = new String(b);
		return s;
	}

	/**
	 * 使用Base64加密
	 * 
	 * @param encodeStr
	 * @return
	 */
	public static String decodeStr(String encodeStr) {
		byte[] b = encodeStr.getBytes();
		b = base64.decode(b);
		String s = new String(b);
		return s;
	}
	
	public static void main(String[] arr) {
		String s = "佩恩六道123《》阿斯蒂芬padf<><ht,ml>";
		System.out.println(s);
		s = encodeStr(s);
		System.out.println("encode:" + s);
		System.out.println("decode:" + decodeStr(s));
	}
}
