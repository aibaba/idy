package com.test.base;

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
	private Long start;
	
	/**
	 * mysql分页参数limit
	 */
	@Setter
	@JSONField(serialize = false)
	private Integer limit;
	
	/**
	 * 起始页
	 */
	@Getter
	@Setter
	@JSONField(serialize = false)
	private Long startPage;
	
	/**
	 * 页容量
	 */
	@Getter
	@Setter
	@JSONField(serialize = false)
	private Integer pageSize;

	public Long getStart() {
		if(startPage == null || pageSize == null) {
			return start;
		}
		return (startPage-1) * pageSize;
	}

	public Integer getLimit() {
		return limit == null ? pageSize : limit;
	}
	
}
