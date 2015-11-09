package com.idy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.idy.exception.resolver.StackTrace;

/**
 * 处理http请求的客户端
 * 
 * @author gaopengbd
 * 
 */
@Service
public class HttpUtil {

	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(HttpUtil.class);

	/**
	 * post方式调用，返回json
	 * 
	 * @param 地址格式
	 *            http://dg.dangdang.com/reg
	 * @return json
	 * @throws IOException
	 */
	public static String doPost(String uri, NameValuePair[] param) throws IOException {
		StringBuffer response = new StringBuffer();
		PostMethod postMethod = null;
		HttpClient httpClient = null;
		try {
			httpClient = new HttpClient();
			postMethod = new PostMethod(uri);
			// 设置http request头
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("Content-Type","text/plain"));
			// 使用系统提供的默认的恢复策略
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			if(param != null && param.length >0) {
				postMethod.setRequestBody(param);
			}
			httpClient.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								postMethod.getResponseBodyAsStream(), "UTF-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line).append(
							System.getProperty("line.separator"));
				}
				reader.close();
			}
		} finally {
			close(postMethod);
		}

		return response.toString();
	}

	/**
	 * get方式调用，返回json
	 * 
	 * @param 地址格式
	 *            http://dg.dangdang.com/reg
	 * @return json
	 * @throws IOException
	 */
	public static String doGet(String uri) throws IOException {
		StringBuffer response = new StringBuffer();
		HttpMethod getMethod = null;
		HttpClient httpClient = new HttpClient();
		try {
			getMethod = new GetMethod(uri);
			httpClient.getParams().setParameter("http.socket.timeout", 15000);
			httpClient.getParams().setParameter("http.connection.timeout", 15000);
			httpClient.getParams().setParameter("http.connection-manager.timeout", 1500000000L);

			// 设置http request头
			List<Header> headers = new ArrayList<Header>();
			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			// 使用系统提供的默认的恢复策略
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			getMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(getMethod);
			if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "UTF-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line).append(System.getProperty("line.separator"));
				}
				reader.close();
			}
		}catch(Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
		} finally {
			close(getMethod);
		}

		return response.toString();
	}
	
	/**
	 * get方式调用，返回json
	 * 
	 * @param 地址格式
	 *            http://dg.dangdang.com/reg
	 * @return json
	 * @throws IOException
	 */
	public static <T> T doGet(String uri,Class<T> clazz) throws IOException {
		String json = doGet(uri);
		if(json == null || "".equals(json)){
			return null;
		}
		return JSON.parseObject(json, clazz);
	}
	
	/**
	 * post方式调用，返回json
	 * 
	 * @param 地址格式
	 *            http://dg.dangdang.com/reg
	 * @return json
	 * @throws IOException
	 */
	public static <T> T doPost(String uri,Class<T> clazz, NameValuePair[] param) throws IOException {
		String json = doPost(uri, param);
		if(json == null || "".equals(json)){
			return null;
		}
		return JSON.parseObject(json, clazz);
	}
	
	/**
	 * get方式调用，返回xml转换后的对象
	 * 
	 * @param 地址格式
	 *            http://dg.dangdang.com/reg
	 * @return json
	 * @throws IOException
	 */
	public static <T> T doGetXmlObj(String uri,Class<T> clazz) throws Exception {
		String xml = doGet(uri);
		return JAXBUtil.xmlToObj(xml, clazz);
	}
	
	
	public static void close(HttpMethod method) {
		if (method != null) {
			method.releaseConnection();
		}
	}
	
	public static String doPut(String uri,String character) throws IOException {
		StringBuffer response = new StringBuffer();
		HttpMethod putMethod = null;
		HttpClient httpClient = new HttpClient();
		if (character == null || "".equals(character)) {
			character = "UTF-8";
		}
		try {
			putMethod = new PutMethod(uri);
			httpClient.getParams().setParameter("http.socket.timeout", 2000);
			httpClient.getParams().setParameter("http.connection.timeout", 2000);
			httpClient.getParams().setParameter("http.connection-manager.timeout", 200000000L);

			// 设置http request头
			List<Header> headers = new ArrayList<Header>();
			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			// 使用系统提供的默认的恢复策略

			putMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			putMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(putMethod);
			if (putMethod.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(putMethod.getResponseBodyAsStream(), character));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line).append(System.getProperty("line.separator"));
				}
				reader.close();
			}
		} finally {
			close(putMethod);
		}

		return response.toString();
	}
	
	public static String doDelete(String uri,String character) throws IOException {
		StringBuffer response = new StringBuffer();
		HttpMethod delMethod = null;
		HttpClient httpClient = new HttpClient();
		if (character == null || "".equals(character)) {
			character = "UTF-8";
		}
		try {
			delMethod = new DeleteMethod(uri);
			httpClient.getParams().setParameter("http.socket.timeout", 2000);
			httpClient.getParams().setParameter("http.connection.timeout", 2000);
			httpClient.getParams().setParameter("http.connection-manager.timeout", 200000000L);

			// 设置http request头
			List<Header> headers = new ArrayList<Header>();
			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			// 使用系统提供的默认的恢复策略

			delMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			delMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(delMethod);
			if (delMethod.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(delMethod.getResponseBodyAsStream(), character));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line).append(System.getProperty("line.separator"));
				}
				reader.close();
			}
		} finally {
			close(delMethod);
		}

		return response.toString();
	}
	
}
