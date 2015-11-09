package com.idy.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * ip处理的工具类
 * 
 * @author gaopengbd
 * 
 */
public class IPUtil {

	/**
	 * 区分使用代理时获取IP的方式
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
