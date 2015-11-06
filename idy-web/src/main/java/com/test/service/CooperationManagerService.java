package com.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @gaopengbd 
 * 2015-02-05
*/
@Service
public class CooperationManagerService{
	
	protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CooperationManagerService.class);

	@Transactional
	public int create() {
		return 0;
	}
}