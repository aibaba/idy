package com.test.base;


import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * DAO基类 提供通用的操作方法,特定方法须在子类中实现
 * @author gaopengbd
 *
 * @param <T>
 */
@Repository
public interface BaseMapper<T> {

	/**
	 * 可包含objId保存
	 * @param obj
	 * @return
	 */
	public int create(T obj);
	
	/**
	 * 只更新not null字段
	 * @param obj
	 * @return
	 */
	public int update(T obj);

	/**
	 * 按主键删除
	 * @param id
	 * @return
	 */
	public int delById(long id);

	/**
	 * 按条件删除，谨慎使用
	 * @param obj
	 * @return
	 */
	public int del(T obj);
	
	public T findById(long id);
	
	/**
	 * 分页查询和普通查询的综合方法，不支持模糊查询
	 * @param obj: 如包含分页信息则分页查询,否则返回满足条件的全部记录
	 * @return
	 */
	public List<T> find(T obj);
	
	/**
	 * 按条件查询数量
	 * @param obj
	 * @return
	 */
	public long count(T obj);
	
	/**
	 * 返回record总数
	 * @return
	 */
	public long countAll();
	
}
