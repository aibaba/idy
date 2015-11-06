package com.test.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.constant.SystemConfig;
import com.test.exception.resolver.StackTrace;

public class CookieUtil {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CookieUtil.class);
	
	public static boolean include(HttpServletRequest request,String name){
		Cookie[] cookies= request.getCookies();
		if(cookies == null || cookies.length == 0) return false;
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(name.trim())){
				return true;
			}
		}
		return false;
	}

	public static String getCookie(HttpServletRequest request,String name){
		if(name == null || "".equals(name)) return name;
		Cookie[] cookies= request.getCookies();
		String value= null;
		if(cookies == null || cookies.length == 0) return value;
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(name.trim())){
				try {
					value = cookie.getValue();
					if(!(value == null || "".equals(value))){
						value = Base64Util.decodeStr(value);
						if(value == null || "".equals(value)) continue ;
						value = URLDecoder.decode(value, SystemConfig.CHARSET_UTF8);
						break;
					}
				} catch (Exception e) {
					logger.info(String.format("getCookie: value=%s, ex=%s", value, e.getLocalizedMessage()));
					value = null;
				}
			}
		}
		return value;
	}
	
	public static void setCookie(HttpServletResponse response,String name,String value){
		if(value == null || "".equals(value)) return ;
		try {
			value = URLEncoder.encode(value, SystemConfig.CHARSET_UTF8);
			value = Base64Util.encodeStr(value);
		}catch (Exception e){
			logger.info(String.format("setCookie: value=%s, ex=%s", value, e.getLocalizedMessage()));
		}
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static String getVA(HttpServletRequest request){
		Cookie[] cookies= request.getCookies();
		String value= null;
		if(cookies == null || cookies.length == 0) return value;
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(SystemConfig.LOGON_COOKIE_NAME)){
				try {
					return value = cookie.getValue();
				} catch (Exception e) {
					logger.error(StackTrace.getExceptionTrace(e));
				}
			}
		}
		return value;
	}
}
