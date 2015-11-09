package com.idy.exception.resolver;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.idy.constant.SystemConfig;


/**
 * 打印详细异常信息类
 * @author gaopengbd
 * @date 2014年10月10日 上午10:40:57 
 * @version V1.0
 */
public class StackTrace{

	/**
	 * Get the stack trace of the exception.
	 * 
	 * @param e
	 *            The exception instance.
	 * @return The full stack trace of the exception.
	 */
	public static String getExceptionTrace(Throwable e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			if(SystemConfig.getPrintStackTrace()){
				e.printStackTrace();
			}
			return sw.toString();
		}
		return "No Exception";
	}

	public static void main(String[] args) {
		print(args);
	}

	private static void print(String[] args) {
		try{
			System.err.println("------------");
			System.err.println(2/0);
		}catch(Exception e){
			System.out.println(getExceptionTrace(e));
		}
		System.out.println(args[0]);
	}
}
