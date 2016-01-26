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

	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ExcelService.class);
			
	@Autowired
	private ExcelMapper excelMapper;

	@Transactional
	public int create(Excel excel) {
		return excelMapper.create(excel);
	}
	
	public int create(List<Excel> list, Integer sheetId) {
		int res = 0;
		if(list != null && list.size() >0 ) {
			Integer version = excelMapper.selectMaxVersion(sheetId);
			version = version == null ? 1 : ++version;
			for(Excel e : list) {
				try {
					//每次sheet的整体更新，都需要新的版本号
					e.setVersion(version);
					if(excelMapper.create(e) == 1){
						res++;
					}
				} catch (Exception e1) {
					logger.error("保存Excel到DB时异常", e);
				}
			}
		}
		return res;
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
	
	public int findMaxVersion(Integer sheetId) {
		Excel excel = new Excel();
		excel.setSheetId(sheetId);;
		List<Excel> list = excelMapper.find(excel);
		if(list == null || list.size() == 0) {
			return 1;
		}
		return list.get(0).getVersion();
	}
	
	public Integer selectMaxVersion(Integer sheetId) {
		return excelMapper.selectMaxVersion(sheetId);
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