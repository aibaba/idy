package com.idy.vo;

import lombok.Data;

public @Data class AuthorVo {

	private Integer author_id;

	private Integer id;

	private String penname;

	private String password;

	private String vita;

	private String mail;

	private String qq;

	private String mobile;

	private Integer create;
	
	private String create_str;

	private Integer status;
}
