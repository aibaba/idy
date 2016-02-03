package com.idy.domain;

import lombok.Getter;
import lombok.Setter;

import com.idy.base.BaseDomain;

/**
 * @Administrator 
 * 2016-02-01
*/
public class SheetLog extends BaseDomain {

	@Setter
	@Getter
	private Integer id;

	@Setter
	@Getter
	private String theme;

	@Setter
	@Getter
	private String info;

	@Setter
	@Getter
	private String type;

	@Setter
	@Getter
	private java.util.Date createTime;

	@Setter
	@Getter
	private Integer sheetId;

	@Setter
	@Getter
	private Integer sheetVersion;
	
	@Setter
	@Getter
	private String createTimeStr;
	
	@Setter
	@Getter
	private String startTime;
    
	@Setter
	@Getter
	private String endTime;

}