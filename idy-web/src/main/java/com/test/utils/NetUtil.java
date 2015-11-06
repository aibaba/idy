package com.test.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.test.exception.resolver.StackTrace;

/**
 * 基于jdk的http工具类
 * 
 * @author gaopengbd
 * 
 */
public class NetUtil {
	
	protected final static String POST = "POST";
	
	protected final static String GET = "GET";
	
	protected final static String PUT = "PUT";
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NetUtil.class);
	
	/**
	 * post方式调用，返回json
	 * 
	 * @param 地址格式
	 *            http://dg.dangdang.com/reg
	 * @return json
	 * @throws IOException
	 */
	public static <T> T doPost(String uri,Class<T> clazz, StringBuffer buffer) throws IOException {
		String json = sendPost(uri, POST, buffer);
		if(json == null || "".equals(json)){
			return null;
		}
		return JSON.parseObject(json, clazz);
	}

	/**
	 * 
	 * @param uri 地址格式：http://dg.dangdang.com/reg
	 * @param buffer
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String doPost(String uri, StringBuffer buffer) throws FileNotFoundException {
		String json = sendPost2(uri, POST, buffer);
		return json;
	}
	
	public static String doGet(String uri, StringBuffer buffer) throws FileNotFoundException {
		String json = sendGet(uri, buffer);
		return json;
	}
	
	/**
	 *  向服务器要发送的POST请求
	 * @param method
	 * @param buffer
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String sendPost(String uri, String method, StringBuffer buffer) throws FileNotFoundException {
		URL url = null;
		HttpURLConnection conn = null;
		String str = "";
		try {
			url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", "UTF-8");
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			
			dos.write(buffer.toString().getBytes());
			dos.flush();
			dos.close();
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String aa = "";
			while ((aa = bufferReader.readLine()) != null) {
				str += aa;
			}
		} catch (Exception e) {
			logger.error(StackTrace.getExceptionTrace(e));
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return str;
	}

	/**
	 *  向服务器要发送的POST请求
	 * @param method
	 * @param buffer
	 * @return
	 * @throws FileNotFoundException
	 */
	protected static String sendPost2(String uri, String method, StringBuffer buffer) throws FileNotFoundException {
		URL url = null;
		HttpURLConnection conn = null;
		String str = "";
		try {
			url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestMethod(method);
			conn.setRequestProperty("Charset", "UTF-8");
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			
			dos.write(buffer.toString().getBytes());
			dos.flush();
			dos.close();
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String aa = "";
			while ((aa = bufferReader.readLine()) != null) {
				str += aa;
			}
		} catch (Exception e) {
			logger.error(StackTrace.getExceptionTrace(e));
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return str;
	}
	
	/**
	 * 模拟GET请求
	 * @param uri
	 * @param queryString
	 * @return
	 */
	protected static String sendGet(String uri, StringBuffer queryString) {
		String response = null; 
        HttpClient client = new HttpClient(); 
        HttpMethod method = new GetMethod(uri); 
        
        try { 
            if (StringUtils.isNotBlank(queryString.toString())) 
                    method.setQueryString(URIUtil.encodeQuery(queryString.toString())); 
            client.executeMethod(method); 
            if (method.getStatusCode() == HttpStatus.SC_OK) { 
                    response = method.getResponseBodyAsString(); 
            } 
	    } catch (URIException e) { 
	        logger.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e); 
	    } catch (IOException e) { 
	        logger.error("执行HTTP Get请求" + uri + "时，发生异常！", e); 
	    } finally { 
	        method.releaseConnection(); 
	    } 
        return response;
	}
	
	/*public static void main(String[] args) throws Exception {
		StringBuffer buffer = new StringBuffer();
		//密钥
		buffer.append("key="+TaduWorker.getTaduSec());
		//CP书籍ID
		buffer.append("&cpid=10000");
		//CP版权ID
		buffer.append("&copyrightid=172");
		//封面图
		buffer.append("&coverimage=http://media.tadu.com/0/3/6/d/036d787be20141fd9827f55d584d7ecf_a.jpg");
		//书籍名称
		buffer.append("&bookname="+URLEncoder.encode("第一本书", "UTF-8"));
		//作者名称
		buffer.append("&authorname="+URLEncoder.encode("第一作者", "UTF-8"));
		//书籍简介
		buffer.append("&intro="+URLEncoder.encode("第一简介,她世家女军医重生成为落魄世家嫡系的后代，拥有逆天回春术，只救该救之人，双瞳识宝，过目不忘，成为振兴家族的幕后人。助父兄得富贵，随夫君远行边防，献兵法，大败敌军，回京都，陷五龙夺谪之乱，与夫君一起携手终扶得太子登基，荣封一品诰命夫人。", "UTF-8"));
		//建议分类ID
		buffer.append("&classid=108");
		//是否连载  1连载0全本
		buffer.append("&serial=1");
		//是否收费  1收费0免费
		buffer.append("&isvip=1");
		NetUtil.doPost(TaduWorker.getAddBookUrl(), JsonMsg.class, buffer);
	}*/
}
