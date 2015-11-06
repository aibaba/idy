package com.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	// yyyy-MM-dd hh:mm:ss
	public static long datestrToLong(String str,String dateformat){
		
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		if(str==null)
			return 0;
		try {
			return format.parse(str).getTime();
		} catch (ParseException e) {
		}
		return 0;
	}

	
	public static String formatDate(long time,String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return formatDate(calendar.getTime(),format);	
	}
	
	public static String formatDate(Date date,String format) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat(format);
			return formater.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	//将 时间戳（秒级别）转换成 yyyy-MM-dd hh:mm:ss  
	public static String convertDate(int time){
		Calendar calendar = Calendar.getInstance();
		long l = 1000;
		calendar.setTimeInMillis(time*l);
		return formatDate(calendar.getTime(),"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取当前 秒 
	 * @return
	 */
	public static Integer getTime(){
		Integer i;
		try {
			i = (int)Math.round(System.currentTimeMillis() / 1000.0);
		} catch (Exception e) {
			i = 0;
		} 
		
		return i;
	}
	
	public static void main(String[] args) {
		//System.out.println(convertDate(getTime()));
		System.out.println(getTime());
//		System.out.println(formatDate(System.currentTimeMillis(),"yyyy/MM"));
	}
	
	/**
	 * 将“字符串形式的时间”转化成“指定格式的时间字符串”
	 * @param dateStr1  原时间字符串
	 * @param sourceFormat 原时间字符串的格式
	 * @param dateFormat 目的时间字符串的格式
	 * @return
	 */
	public static String formatDate(String dateStr1,String sourceFormat, String aimFormat){
		//将字符串转换成时间对象
		SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat);
		try {
			return formatDate(sdf.parse(dateStr1), aimFormat);
		} catch (ParseException e) {
			return "";
		}
	}
	
	/**
	 * 得到days天之前的日期，以指定格式
	 * @param days
	 * @param format
	 * @return 日期字符串：yyyy-MM-dd
	 */
	public static String getBeforeDate(int days, String format){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return new SimpleDateFormat(format).format(cal.getTime());
	}
}
