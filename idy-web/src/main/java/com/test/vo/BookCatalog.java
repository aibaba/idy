package com.test.vo;

import java.util.List;

import lombok.Data;

/**
 * 作品目录
 * @author gaopengbd
 *
 */
public @Data class BookCatalog {
	 
	private Integer book_id;
	
	private String title;
	
	private Integer is_vip;

	private Integer free_chapters;
	
	private List<BookVolumeVo> volumes;
}
