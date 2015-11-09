package com.idy.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;  

import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey;  
  
/** 
 * 这个类用来给一个较短的字符串加、解密<br/> 
 */  
public class KeyUtil {  
  
    private static final String EncodeAlgorithm = "DES";  
    private static KeyUtil instance = null;  
  
    public synchronized static KeyUtil getInstance() {  
        if (instance == null) {  
            instance = new KeyUtil();  
            if (!instance.init()) {  
                instance = null;  
            }  
        }  
        return instance;  
    }  
  
    private SecretKey key = null;  
  
    private boolean init() {  
        try {  
            KeyGenerator keygen = KeyGenerator.getInstance(EncodeAlgorithm);  
            SecureRandom random = new SecureRandom();  
            keygen.init(random);  
            key = keygen.generateKey();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return key != null;  
    }  
  
    private Cipher getCipher(int mode) {  
        try {  
            Cipher cipher = Cipher.getInstance(EncodeAlgorithm);  
            cipher.init(mode, key);  
            return cipher;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    public Cipher getEncodeCipher() {  
        return this.getCipher(Cipher.ENCRYPT_MODE);  
    }  
  
    public Cipher getDecodeCipher() {  
        return this.getCipher(Cipher.DECRYPT_MODE);  
    }  
  
    /** 
     * 解密，若输入为null或加/解密过程出现异常，则输出为null <br/> 
     * 作者：wallimn　时间：2009-8-12　上午08:09:44<br/> 
     * 博客：http://wallimn.iteye.com<br/> 
     * 参数：<br/> 
     *  
     * @param str 
     * @return 
     */  
    public String decode(String str) throws Exception {  
        if(str==null)return null;  
        Cipher cipher = getDecodeCipher();  
        StringBuffer sb = new StringBuffer();  
        int blockSize = cipher.getBlockSize();  
        int outputSize = cipher.getOutputSize(blockSize);  
        byte[] src = stringToBytes(str);  
        byte[] outBytes = new byte[outputSize];  
        int i = 0;  
        for (; i <= src.length - blockSize; i = i + blockSize) {  
            int outLength = cipher.update(src, i, blockSize, outBytes);  
            sb.append(new String(outBytes, 0,outLength));  
        }  
        if (i == src.length)  
            outBytes = cipher.doFinal();  
        else {  
            outBytes = cipher.doFinal(src, i, src.length - i);  
        }  
        sb.append(new String(outBytes));  
        return sb.toString();   
    }  
  
    /** 
     * 加密，若输入为null或加/解密过程出现异常，则输出为null <br/> 
     * 作者：wallimn　时间：2009-8-12　上午08:09:59<br/> 
     * 博客：http://wallimn.iteye.com<br/> 
     * 参数：<br/> 
     *  
     * @param str 
     * @return 
     */  
    public String encode(String str) throws Exception {  
        if(str==null)return null;  
        Cipher cipher = getEncodeCipher();  
        StringBuffer sb = new StringBuffer();  
        int blockSize = cipher.getBlockSize();  
        int outputSize = cipher.getOutputSize(blockSize);  
        byte[] src = str.getBytes();  
        byte[] outBytes = new byte[outputSize];  
        int i = 0;  
        for (; i <= src.length - blockSize; i = i + blockSize) {  
            int outLength = cipher.update(src, i, blockSize, outBytes);  
            sb.append(bytesToString(outBytes, outLength));  
        }  
        if (i == src.length)  
            outBytes = cipher.doFinal();  
        else {  
            outBytes = cipher.doFinal(src, i, src.length - i);  
        }  
        sb.append(bytesToString(outBytes));  
        return sb.toString();  
    }  
  
    private String bytesToString(byte[] bs) {  
        if (bs == null || bs.length == 0)  
            return "";  
        return bytesToString(bs, bs.length);  
    }  
  
    private String bytesToString(byte[] bs, int len) {  
        if (bs == null || bs.length == 0)  
            return "";  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < len; i++) {  
            // System.out.println(bs[i]+":"+String.format("%02X", bs[i]));  
            sb.append(String.format("%02X", bs[i]));  
        }  
        return sb.toString();  
    }  
  
    private byte[] stringToBytes(String str) {  
        if (str == null || str.length() < 2 || str.length() % 2 != 0)  
            return new byte[0];  
        int len = str.length();  
        byte[] bs = new byte[len / 2];  
        for (int i = 0; i * 2 < len; i++) {  
            bs[i] = (byte) (Integer.parseInt(str.substring(i * 2, i * 2 + 2),  
                    16) & 0xFF);  
            // System.out.println(str.substring(i * 2, i * 2 + 2)+":"+bs[i]);  
        }  
        return bs;  
    }  
    
    public static void main(String[] arr) throws Exception{
    	String str = "佩恩撒福建按实际道1234/static/img.com";
    	System.out.println("加密前："+ str);  
    	String tmp = URLEncoder.encode(str, "UTF-8"); 
    	System.out.println("Encoder结果："+ tmp);
	    tmp = KeyUtil.getInstance().encode(tmp);  
	    System.out.println("加密结果："+ tmp);  
	    String res = URLDecoder.decode(KeyUtil.getInstance().decode(tmp),"UTF-8");
	    System.out.println("解密结果："+res + "," + (res.endsWith(str)));  
    }
}  

