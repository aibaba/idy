package com.idy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.idy.base.BaseController;
import com.idy.domain.UserInfo;
import com.idy.service.UserInfoService;

/**
 *@Description: 
 *@author pein
 *@date 2015年11月9日 下午4:01:51 
 *@version V1.0
 */
@Controller
@RequestMapping("/testmongo")
public class TestMongoController extends BaseController {
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestMongoController.class);
	
	@Autowired
	private UserInfoService userInfoService;

	
	@RequestMapping(value = "/{num}")
    public String mapping(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String num,//sheet的编号
            Model model) {
		model.addAttribute("num", num);
		UserInfo u = new UserInfo();
		u.setAge(20);
		u.setIsPass(false);
		u.setUserId(Integer.parseInt(num));
		u.setUserName("卡卡西");
		List<UserInfo> userList = userInfoService.find();
		System.out.println(JSON.toJSONString(userList));
		userInfoService.insert(u);
		userList = userInfoService.find();
		System.out.println(JSON.toJSONString(userList));
		u = userInfoService.findById(1);
		System.out.println(JSON.toJSONString(u));
		u.setIsPass(true);
		userInfoService.update(u);
		u = userInfoService.findById(1);
		System.out.println(JSON.toJSONString(u));
		userInfoService.delete(u);
		userList = userInfoService.find();
		System.out.println(JSON.toJSONString(userList));
		return "excel/sheet" + num;
    }
	
}

