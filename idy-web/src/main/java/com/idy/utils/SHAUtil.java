package com.idy.utils;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idy.exception.resolver.StackTrace;

/**
 * SHA1加密方式
 * @author gaopengbd
 *
 */
public class SHAUtil {
	/**
	 * slf4j logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(SHAUtil.class);
			
			
	 /*** 
     * SHA加密 生成40位SHA码
     * @param 待加密字符串
     * @return 返回40位SHA码
     */
    public static String shaEncode(String inStr) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
	        byte[] byteArray = inStr.getBytes("UTF-8");
	        byte[] md5Bytes = sha.digest(byteArray);
	        StringBuffer hexValue = new StringBuffer();
	        for (int i = 0; i < md5Bytes.length; i++) {
	            int val = ((int) md5Bytes[i]) & 0xff;
	            if (val < 16) { 
	                hexValue.append("0");
	            }
	            hexValue.append(Integer.toHexString(val));
	        }
	        return hexValue.toString();
        } catch (Exception e) {
        	logger.error(StackTrace.getExceptionTrace(e));
            return "";
        }
    }

    /**
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String str = new String("amigoxiexiexingxing");
        System.out.println("原始：" + str);
        System.out.println("SHA后：" + shaEncode(str));
    }
}
