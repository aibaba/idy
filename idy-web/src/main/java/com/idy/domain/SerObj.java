package com.idy.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.idy.base.BaseDomain;

/**
 * @Administrator 
 * 2016-02-01
*/
public class SerObj extends BaseDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private Integer id;

	@Setter
	@Getter
	private String objName;

	@Setter
	@Getter
	private byte[] blobVal;

	@Setter
	@Getter
	private String info;

	@Setter
	@Getter
	private java.util.Date createTime;

	@Setter
	@Getter
	private java.util.Date updateTime;

}