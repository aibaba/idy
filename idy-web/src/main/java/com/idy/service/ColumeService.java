package com.idy.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	
	//sheetId_enName , cloume
	private static Map<String ,Colume> columeMap = null;
	
	/*public Map<String ,Colume> getColumeMap(Integer sheetId){
		if(columeMap == null){
			List<Colume> list = this.findBySheetId(sheetId);
			if(list != null){
				columeMap = new ConcurrentHashMap<String, Colume>();
				for(Colume c : list){
					columeMap.put(sheetId + "+" + c.getEnName(), c);
				}
			}
		}
		return columeMap;
	}*/

	@Transactional
	public int create(Colume colume) {
		return columeMapper.create(colume);
	}
	
	@Transactional
	public int create(List<Colume> list) {
		int res = 0;
		if(list != null && list.size() > 0) {
			this.del(list.get(0).getSheetId());
			if(columeMap == null || columeMap.size() < 1){
				columeMap = new ConcurrentHashMap<String, Colume>();
			}
			for(int i=0; i<list.size(); i++){
				Colume c = list.get(i);
				String enName = "";
				if(i < 9) {
					enName = "C0" + (i + 1);
				}else {
					enName = "C" + (i + 1);
				}
				c.setEnName(enName);
				c.setType(1);
				c.setSequence(1);
				c.setStatus(1);
				c.setDescription("导入表格时自动添加");
				if(columeMapper.create(c) > 0) {
					columeMap.put(enName, c);
					res++;
				}
			}
		}
		return res;
	}
	
	public int del(Integer sheetId) {
		Colume d = new Colume();
		d.setSheetId(sheetId);
		return columeMapper.del(d);
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
	
	public List<Colume> findBySheetId(Integer sheetId) {
		Colume c = new Colume();
		c.setSheetId(sheetId);
		return columeMapper.find(c);
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