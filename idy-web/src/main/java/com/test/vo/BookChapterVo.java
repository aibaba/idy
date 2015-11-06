package com.test.vo;

import lombok.Data;

/**
 * 作品目录之章节
 * @author gaopengbd
 *
 */
public @Data class BookChapterVo {

	private Integer chapter_id;
	
	private Integer book_id;
	
	//非数据库字段:作品名
	private String book_title;
	
	//非数据库字段:频道
	private String channel;
	
	//非数据库字段:分类
	private String type;
	
	private Integer volume_id;
	
	private String title;
	
	private String intro;
	
	private String content;

	private Integer word;
	
	private Integer create;
	
	//非数据库字段：创建时间字符串
	private String create_str;
	
	private Integer release;

	private Integer browse;

	private Integer price;

	private Integer cprice;

	private Integer sale;

	private Integer sell;

	private Integer status;

	private Integer is_vip;
	
	//非数据库字段
	private String author_name;
	
	private String order_by;

}
