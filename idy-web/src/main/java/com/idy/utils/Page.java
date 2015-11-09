package com.idy.utils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author Zhuhoufei
 *
 */


public class Page<Entity> {
	/**
	 * 数据库总行数
	 * 
	 */
	private int rowCount;

	/**
	 * 一页多少行
	 */
	@Getter
	@Setter
	private int pageSize = 10;

	/**
	 * 点击的导航数
	 */
	private int num;

	/**
	 * 查询起始行
	 */
	@Getter
	@Setter
	private long startRow;

	/**
	 * 下一页
	 */
	private int next;

	/**
	 * 上一页
	 */
	private int prev;

	/**
	 * 一共多少页
	 */
	private int pageCount;

	/**
	 * 显示导航起始
	 */
	private int begin;

	/**
	 * 显示的导航结束
	 */
	private int end;

	/**
	 * 首页
	 */
	private int first = 1;

	/**
	 * 尾页
	 */
	private int last;

	/**
	 * 一共显示多少导航
	 */
	private int navNum = 10;
	
	/**
	 * 分页查询之后的数据
	 */
	private List<Entity> pageData;
	
	public List<Entity> getPageData() {
		return pageData;
	}

	public void setPageData(List<Entity> pageData) {
		this.pageData = pageData;
	}

	public int getRowCount() {
		return rowCount;
	}

	public Page() {
		super();
	}
	
	

	
	public int getPageCount() {
		int pageSize = getPageSize();
		int pCount = rowCount/pageSize;
		if(!(rowCount%pageSize==0)){
			pCount =pCount+1;
		}
		return pCount;
	}


	public Page(int num, int rowCount, int pageSize, int navNum) {
		this.navNum = navNum;
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		this.pageCount = (int) Math.ceil(this.rowCount * 1.0 / this.pageSize);
		this.last = this.pageCount;
		this.num = Math.max(this.first, num);
		this.num = Math.min(this.last, this.num);

		this.startRow = Math.max(0, (this.num - 1) * this.pageSize);

		this.next = Math.min(this.last, this.num + 1);
		this.prev = Math.max(this.first, this.num - 1);

		this.begin = Math.max(this.first, this.num - (this.navNum / 2));
		this.end = Math.min(this.last, this.begin + this.navNum - 1);

		if ((this.end - this.begin) < (this.navNum - 1)) {
			this.begin = Math.max(this.first, this.last - this.navNum + 1);
		}

	}

	public int getNavNum() {
		return navNum;
	}

	public void setNavNum(int navNum) {
		this.navNum = navNum;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getNavCount() {
		return pageCount;
	}

	public void setNavCount(int navCount) {
		this.pageCount = navCount;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

}
