package com.idy.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.idy.domain.UserInfo;

@Service
public class UserInfoServiceImpl implements UserInfoService{
	
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public List<UserInfo> find() {
		return mongoTemplate.find(new Query(), UserInfo.class); 
	}

	@Override
	public UserInfo findById(Integer userId) {
		return mongoTemplate.findOne(new Query(Criteria.where("userId").is(userId)), UserInfo.class);
	}

	@Override
	public void insert(UserInfo user) {
		mongoTemplate.insert(user);
	}

	@Override
	public void delete(UserInfo user) {
		mongoTemplate.remove(user);
	}

	@Override
	public void update(UserInfo user) {
		mongoTemplate.updateFirst(new Query(Criteria.where("userId").is(user.getUserId())), new Update().inc("age", 3), UserInfo.class);  
	}

}
