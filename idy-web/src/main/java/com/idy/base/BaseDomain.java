package com.idy.base;

import lombok.Getter;
import lombok.Setter;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * domain基类 
 * 封装必要的通用字段
 * @author gaopengbd
 *
 */
public class BaseDomain {

	/**
	 * mysql分页参数start
	 */
	@Setter
	@JSONField(serialize = false)
	private Integer start;
	
	/**
	 * mysql分页参数limit
	 */
	@Setter
	@JSONField(serialize = true)
	private Integer limit;
	
	/**
	 * 起始页
	 */
	@Getter
	@Setter
	@JSONField(serialize = false)
	private Integer startPage;
	
	/**
	 * 页容量
	 */
	@Getter
	@Setter
	@JSONField(serialize = false)
	private Integer pageSize;

	public Integer getStart() {
		if(startPage == null || pageSize == null) {
			return start;
		}
		return (startPage-1) * pageSize;
	}

	public Integer getLimit() {
		return limit == null ? pageSize : limit;
	}
	
}
