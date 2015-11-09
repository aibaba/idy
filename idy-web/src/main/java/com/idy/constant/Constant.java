package com.idy.constant;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * 系统配置
 * @author gaopeng
 *
 */
public class Constant {
	
	public static final DateFormat YM_DATEFORMAT = new SimpleDateFormat("yyyyMM");
	
	public static final DateFormat YMDH_DATEFORMAT = new SimpleDateFormat("yyyyMMddHH");
	
	public static final DateFormat YMD_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final DateFormat YHS_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final DateFormat YMH_DATEFORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static final DateFormat YMS_DATEFORMAT = new SimpleDateFormat("HH:mm:ss");
	
	public static final DecimalFormat DF_INT = new DecimalFormat("0");// 格式化数字
	
	public static final String CHARSET_UTF8 = "UTF-8";
	
}
