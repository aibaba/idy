package com.idy.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class ReadWriteDataSource extends AbstractRoutingDataSource {
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ReadWriteDataSource.class);
	
	private String writeDataSourceKey;//写库只有1个
	
	private String[] readDataSourceKeyArr;//读库多个
	
	private List<String> readDataSourceKeyList = new ArrayList<String>();//读库多个
	
	//java.util.Random random = new java.util.Random(); 
	
	private int seed = 0;
	
	@Override
	public void setTargetDataSources(java.util.Map<Object,Object> targetDataSources) {
		if(targetDataSources == null || targetDataSources.size() < 1) {
			throw new RuntimeException("没有配置数据源");
		}
		super.setTargetDataSources(targetDataSources);
		
		for(Entry<Object,Object> entry : targetDataSources.entrySet()){
			String key = (String)entry.getKey();
			if(key.trim().contains("read")){
				readDataSourceKeyList.add(key);
			}else if(key.trim().contains("write")) {
				writeDataSourceKey = key;
			}else {
				throw new RuntimeException("配置了未知的数据源 ：" + key);
			}
		}
		
		if(StringUtils.isEmpty(writeDataSourceKey)){
			logger.warn("没有配置写数据源！");
		}
		
		if(readDataSourceKeyList.size() == 0){
			logger.warn("没有配置写数据源！");
		}
		
		readDataSourceKeyArr = (String[]) readDataSourceKeyList.toArray();
	}
	
	@Override
	protected Object determineCurrentLookupKey() {
		//根据注解返回数据源
		DatasourceStrategy strategy = null;//DatasourceHelper.getCurrentDStrategy();
		if(strategy.getKey() == DatasourceStrategy.Reader.getKey()){
			int i = seed++%readDataSourceKeyArr.length;
			if(i > 100)
			return readDataSourceKeyArr[i];
		}
		/*if(strategy.getKey() == DatasourceStrategy.Writer.getKey()){
			return writeDataSourceKey;
		}*/
			
		return writeDataSourceKey;
	}

}
