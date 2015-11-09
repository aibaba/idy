package com.idy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5工具类：author的密码加密使用
 * @author gaopengbd
 *
 */
public class MD5Util {
	
	// 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    /**
     * php小说后台的MD5加密时已使用
     */
    private final static String secretKey = "./,;sldkfasjf";

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    protected static String byteToNum(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    
    /**
     * 作者加密用到，与最初php小说后台的MD5加密保持一致
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSecretMD5(String str) throws NoSuchAlgorithmException{
    	if(str == null) return null;
        String resultString = null;
        str = str + secretKey;
        resultString = new String(str);
        MessageDigest md = MessageDigest.getInstance("MD5");
        // md.digest() 该函数返回值为存放哈希值结果的byte数组
        resultString = byteToString(md.digest(str.getBytes()));
        return resultString;
    }

    public static String getMD5Code(String str) throws NoSuchAlgorithmException{
    	if(str == null) return null;
        String resultString = null;
        resultString = new String(str);
        MessageDigest md = MessageDigest.getInstance("MD5");
        // md.digest() 该函数返回值为存放哈希值结果的byte数组
        resultString = byteToString(md.digest(str.getBytes()));
        return resultString;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("98755b6207a455da4a063a4dc3b35707".equals(MD5Util.getSecretMD5("123456")));
    }
	
}
