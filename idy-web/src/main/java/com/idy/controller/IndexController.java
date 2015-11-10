package com.idy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页、默认页控制
 *@Description: TODO
 *@author penggao15@creditease.cn
 *@date 2015年11月10日 下午5:34:28 
 *@version V1.0
 */
@Controller
@RequestMapping("")
public class IndexController {
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping(value = "/index")
    public String index(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		logger.warn("Catch:/index");
		return "index";
    }
	
	
	@RequestMapping(value = "/")
    public String defult(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		logger.warn("Catch:/");
		return "index";
    }
	
	@RequestMapping(value = "/404")
    public String page404(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		logger.warn("Catch:/404");
		return "404";
    }
	
	@RequestMapping(value = "/error/access-illegal")
    public String pageVm404(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		System.err.println("/error/access-illegal");
		logger.error("Catch:/error/access-illegal");
		return "404";
    }
	
	@RequestMapping(value = "/error/access-denied")
    public String pageDenied(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		System.err.println("/error/access-denied");
		logger.error("Catch:/error/access-denied");
		return "404";
    }
	
}
