package com.idy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idy.domain.SheetLog;
import com.idy.dao.SheetLogMapper;

/**
 * @Administrator 
 * 2016-02-01
*/
@Service
public class SheetLogService{

	@Autowired
	private SheetLogMapper sheetLogMapper;

	@Transactional
	public int create(SheetLog sheetLog) {
		return sheetLogMapper.create(sheetLog);
	}

	@Transactional
	public int update(SheetLog sheetLog) {
		return sheetLogMapper.update(sheetLog);
	}

	@Transactional
	public int delById(long id){
		return sheetLogMapper.delById(id);
	}

	@Transactional
	public int del(SheetLog sheetLog) {
		return sheetLogMapper.del(sheetLog);
	}

	@Transactional
	public List<SheetLog> find(SheetLog sheetLog) {
		return sheetLogMapper.find(sheetLog);
	}

	@Transactional
	public SheetLog findById(long id){
		return sheetLogMapper.findById(id);
	}

	@Transactional
	public long countAll(){
		return sheetLogMapper.countAll();
	}

	@Transactional
	public long count(SheetLog sheetLog){
		return sheetLogMapper.count(sheetLog);
	}
}