package com.idy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idy.domain.Excel;
import com.idy.dao.ExcelMapper;

/**
 * @Administrator 
 * 2015-11-09
*/
@Service
public class ExcelService{

	@Autowired
	private ExcelMapper excelMapper;

	@Transactional
	public int create(Excel excel) {
		return excelMapper.create(excel);
	}

	@Transactional
	public int update(Excel excel) {
		return excelMapper.update(excel);
	}

	@Transactional
	public int delById(long id){
		return excelMapper.delById(id);
	}

	@Transactional
	public int del(Excel excel) {
		return excelMapper.del(excel);
	}

	@Transactional
	public List<Excel> find(Excel excel) {
		return excelMapper.find(excel);
	}

	@Transactional
	public Excel findById(long id){
		return excelMapper.findById(id);
	}

	@Transactional
	public long countAll(){
		return excelMapper.countAll();
	}

	@Transactional
	public long count(Excel excel){
		return excelMapper.count(excel);
	}
}