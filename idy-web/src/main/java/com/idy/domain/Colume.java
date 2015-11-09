package com.idy.domain;

import lombok.Getter;
import lombok.Setter;

import com.idy.base.BaseDomain;

/**
 * @Administrator 
 * 2015-11-09
*/
public class Colume extends BaseDomain {

	@Setter
	@Getter
	private Integer id;

	@Setter
	@Getter
	private String enName;

	@Setter
	@Getter
	private String znName;

	@Setter
	@Getter
	private Integer sequence;

	@Setter
	@Getter
	private Integer type;

	@Setter
	@Getter
	private Integer status;

	@Setter
	@Getter
	private String description;

}