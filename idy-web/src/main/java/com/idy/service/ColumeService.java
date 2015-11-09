package com.idy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idy.domain.Colume;
import com.idy.dao.ColumeMapper;

/**
 * @Administrator 
 * 2015-11-09
*/
@Service
public class ColumeService{

	@Autowired
	private ColumeMapper columeMapper;

	@Transactional
	public int create(Colume colume) {
		return columeMapper.create(colume);
	}

	@Transactional
	public int update(Colume colume) {
		return columeMapper.update(colume);
	}

	@Transactional
	public int delById(long id){
		return columeMapper.delById(id);
	}

	@Transactional
	public int del(Colume colume) {
		return columeMapper.del(colume);
	}

	@Transactional
	public List<Colume> find(Colume colume) {
		return columeMapper.find(colume);
	}

	@Transactional
	public Colume findById(long id){
		return columeMapper.findById(id);
	}

	@Transactional
	public long countAll(){
		return columeMapper.countAll();
	}

	@Transactional
	public long count(Colume colume){
		return columeMapper.count(colume);
	}
}