package com.idy.constant;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统配置
 * @author gaopeng
 *
 */
public class SystemConfig {
	
	@Getter
	@Setter
	public static String basePath= "D:/idy/";
	
	@Getter
	@Setter
	public static Boolean printStackTrace = true;
	
	public static final SimpleDateFormat YMD_FORMATE = new SimpleDateFormat("yyyy/MM/dd");
	
	public static final SimpleDateFormat YMS_FORMATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final DecimalFormat DF_INT = new DecimalFormat("0");// 格式化数字

	public static final DecimalFormat DF_DECUMAL = new DecimalFormat("0.0");// 格式化数字
}
