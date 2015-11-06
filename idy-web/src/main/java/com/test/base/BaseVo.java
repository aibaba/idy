package com.test.base;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * domain基类 
 * 封装必要的通用字段
 * @author gaopengbd
 *
 */
public @Data class BaseVo {

	@JSONField(serialize = false)
	private Integer page = null;
	
	@JSONField(serialize = false)
	private Integer max = 9;
	
	@JSONField(serialize = false)
	private Integer start = 0;
	
	@JSONField(serialize = false)
	private Integer limit = 9;
	
	public Integer getStart() {
		if(page == null || max == null) {
			return start;
		}
		start = (page-1) * max;
		return start < 0 ? 0 : start;
	}

	public Integer getLimit() {
		return limit == null ? page : limit;
	}
	
}
