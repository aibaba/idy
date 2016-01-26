package com.idy.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.idy.base.BaseMapper;
import com.idy.domain.Excel;

/**
 * @Administrator 
 * 2016-01-26
*/
@Repository
public interface ExcelMapper extends BaseMapper<Excel>{
	
	public Integer selectMaxVersion(@Param("sheetId") Integer sheetId);

}