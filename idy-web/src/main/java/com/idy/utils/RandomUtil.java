package com.idy.utils;

public class RandomUtil {
	
	/**
	 * 得到min到mix之间的整数
	 * @param min
	 * @param max
	 * @return
	 */
	public static long getRandomLong(int min, int max){
		 return Math.round(Math.random()*(max-min)+min);
	}
}
