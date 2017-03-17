package com.idy.service;

import java.util.List;

import com.idy.domain.UserInfo;

public interface UserInfoService {

	List<UserInfo> find();
	
	UserInfo findById(Integer userId);
	
	void insert(UserInfo user);
	
	void delete(UserInfo user);
	
	void update(UserInfo user);
	
}
