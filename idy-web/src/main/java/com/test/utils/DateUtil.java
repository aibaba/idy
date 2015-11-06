package com.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.test.constant.SystemConfig;

public class DateUtil {

	public static void main(String[] args) throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.parse("2015-04-03 15:30:00").getTime()/1000);
        System.out.println(sdf.format(new Date(1428076800*1000L)));
        System.out.println((int)(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-04-03 15:00:37").getTime()/1000));
        System.out.println(sdf.parse("2015-04-07 17:50:37").getTime()/1000);
        System.out.println(sdf.format(new Date(1428396822*1000L)));
        System.out.println(sdf.format(new Date(1413525790*1000L)));
        System.out.println(sdf.format(new Date(1428476437*1000L)));
        System.out.println(sdf.format(new Date(1428390037*1000L)));
        String specifiedDay = sdf.format(date);
        System.out.println(getSpecifiedDayBefore(specifiedDay));
        System.out.println(getSpecifiedDayAfter(specifiedDay));
        System.out.println(getSpecifiedDayBefore(new Date(), 0));
    }
    
    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }
    
    /**
     * 获得指定日期的前@param days天
     * @param specifiedDay
     * @param day
     * @return
     */
    public static String getSpecifiedDayBefore(String specifiedDay, int days) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int now = c.get(Calendar.DATE);
        c.set(Calendar.DATE, now - days);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }
    
    /**
     * 获得指定日期的前@param days天
     * @param specifiedDay
     * @param day
     * @return
     */
    public static String getSpecifiedDayBefore(Date specifiedDate, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(specifiedDate);
        int now = c.get(Calendar.DATE);
        c.set(Calendar.DATE, now - days);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }
    
    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }
    
    public static String getDate(int date) {
    	Date d = new Date(date * 1000L);
    	return SystemConfig.YMD_DATEFORMAT.format(d);
    }
    
    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static int getSpecifiedDayAfter(int specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
        	date = new Date(specifiedDay*1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        
        return (int)(c.getTimeInMillis()/1000);
    }
}
