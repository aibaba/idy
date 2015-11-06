package com.test.constant;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * 系统配置
 * @author gaopengbd
 *
 */
public class SystemConfig {

	public static final DateFormat YM_DATEFORMAT = new SimpleDateFormat("yyyyMM");
	
	public static final DateFormat YMDH_DATEFORMAT = new SimpleDateFormat("yyyyMMddHH");
	
	public static final DateFormat YMD_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final DateFormat YHS_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final DateFormat YMH_DATEFORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static final DateFormat YMS_DATEFORMAT = new SimpleDateFormat("HH:mm:ss");
	
	public static final DecimalFormat DF_INT = new DecimalFormat("0");// 格式化数字
	
	public static final String CHARSET_UTF8 = "UTF-8";
	
	public static final String USER_ID = "userId";
	
	public static final String USER_NAME = "userName";
	
	public static final String BMS_USER_ID = "bmsUserId";
	
	public static final String BMS_USER_NAME = "bmsUserName";
	
	public static final String USER_IMG = "userImg";
	
	public static final String RETURN_URL = "rdURL";
	
	public static final String LOGON_TOKEN = "v-s-c";
	
	public static final String LOGON_COOKIE_NAME = "VA";//TKPaoPao
	
	public static final String LOGON_COOKIE_DIMAIN = "doogua.dangdang.com";
	
	public static final String[] COOKIE_NAMES = new String[]{SystemConfig.USER_ID,SystemConfig.USER_NAME,SystemConfig.LOGON_COOKIE_NAME};
	
	public static final String IS_LOGIN = "isLogin";
	
	public static final String API_DATA = "data";
	
	public static final String API_MSG = "msg";
	
	public static final String API_ERR = "err";
	
	public static final String BOOK_USER_GROUP_ROOT = "root";
	
	public static final String BOOK_USER_GROUP_AUTHOR = "author";
	
	public static final String BOOK_USER_GROUP_EDITOR = "editor";
	
	public static final String WEB_SITE_NAME = "豆瓜网";
	
	public static final String WEB_SITE_DOMAIN = "http://doogua.dangdang.com";
}
