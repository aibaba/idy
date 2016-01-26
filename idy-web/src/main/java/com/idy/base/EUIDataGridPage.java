package com.idy.base;

import java.util.List;

import lombok.Data;

/**
 * Model data for jeasyui data grid which should be converted to json.
 * @author gaopeng
 *
 * @param <T>
 */
public @Data class EUIDataGridPage <T> {
	
	public long total; 
	
	public List<T> rows;
	
}
