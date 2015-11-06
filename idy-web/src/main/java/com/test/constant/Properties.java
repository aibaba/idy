package com.test.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * 加载一些配置数据
 * @author gaopengbd
 *
 */
public class Properties {
	
	/**
	 * 项目的根路径
	 */
	@Getter
	@Setter
	private static String basePath= "/export/dg-coop/";
	
	/**
	 * 社区默认的域名
	 */
	@Getter
	@Setter
	private static String baseIndex = "http://doogua.dangdang.com";
	
	/**
	 * 社区默认的域名
	 */
	@Getter
	@Setter
	private static String indexUrl = "http://doogua.dangdang.com";
	
	/**
	 * 是否打印异常到控制台，开发阶段配置成true
	 */
	@Getter
	@Setter
	public static boolean printStackTrace = true;
	
	@Getter
	@Setter
	public static String ftpWorkPath = "/var/www/dg/web/media/cover/";
	
	@Getter
	@Setter
	public static String ftpClientsUrl = "10.255.209.126:8021:root:dell1950";
	
	@Getter
	@Setter
	public static String rightBookList360 = "/export/dg-coop/WEB-INF/classes/excel/360list.xlsx";
	
}
