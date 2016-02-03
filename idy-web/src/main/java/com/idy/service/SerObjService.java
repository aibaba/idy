package com.idy.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idy.domain.Excel;
import com.idy.domain.SerObj;
import com.idy.dao.SerObjMapper;
import com.idy.utils.ObjectSerialStoreUtil;

/**
 * @Administrator 
 * 2016-02-01
*/
@Service
public class SerObjService{

	@Autowired
	private SerObjMapper serObjMapper;

	@Transactional
	public int create(List<Excel> list) throws IOException {
		if(list == null || list.size() < 1) return 0;
		SerObj serObj = new SerObj();
		serObj.setObjName(Excel.class.getSimpleName());
		serObj.setCreateTime(new Date());
		serObj.setUpdateTime(new Date());
		serObj.setInfo("导入sheet: " + list.get(0).getSheetId() + "时，自动创建");
		serObj.setBlobVal(ObjectSerialStoreUtil.serializeObject(serObj));
		return serObjMapper.create(serObj);
	}

	@Transactional
	public int update(SerObj serObj) {
		return serObjMapper.update(serObj);
	}

	@Transactional
	public int delById(long id){
		return serObjMapper.delById(id);
	}

	@Transactional
	public int del(SerObj serObj) {
		return serObjMapper.del(serObj);
	}

	@Transactional
	public List<SerObj> find(SerObj serObj) {
		return serObjMapper.find(serObj);
	}

	@Transactional
	public SerObj findById(long id){
		return serObjMapper.findById(id);
	}

	@Transactional
	public long countAll(){
		return serObjMapper.countAll();
	}

	@Transactional
	public long count(SerObj serObj){
		return serObjMapper.count(serObj);
	}
}