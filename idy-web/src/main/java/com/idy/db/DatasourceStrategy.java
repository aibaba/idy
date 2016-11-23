package com.idy.db;

/**
 * 
 * @author gao
 *
 */
public enum DatasourceStrategy {
	
	Default(0,"默认数据源,策略与SingleWriter相同"),
	Reader(1,"简单轮训只读数据源"),
	Writer(2,"单个'可写'数据源"),
	DirectDS(3,"直接指定目标数据源");
	
	private Integer key;
	private String comment;
	private String toStr;
	
	private DatasourceStrategy(Integer keyVal, String commentVal) {
		this.key = keyVal;
		this.comment = commentVal;
		this.toStr = "datasource_key("+key+";"+comment+")";
	}

	public Integer getKey() {
		return key;
	}

	public String getComment() {
		return comment;
	}
	
	public String toString(){
		return toStr;
	}
	
}
