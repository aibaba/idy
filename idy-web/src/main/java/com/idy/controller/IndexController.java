package com.idy.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 对外合作接口：分销
 * 2015-04-15 与小米对接
 * @author gaopengbd
 *
 */
@Controller
@RequestMapping("")
public class IndexController {
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping(value = "/error/access-denied")
	@ResponseBody
    public String accessDenied(
            HttpServletRequest request, 
            HttpServletResponse response
            ) throws IOException {
		return "Illegal status!";
    }
}
