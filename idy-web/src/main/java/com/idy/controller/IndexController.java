package com.idy.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping(value = "/index")
    public String index(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		//System.err.println("index.........page");
		return "index";
    }
	
	
	@RequestMapping(value = "/")
    public String defult(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		//System.err.println("defult.........page");
		return "index";
    }
	
	@RequestMapping(value = "/404")
    public String page404(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		return "404";
    }
	
	@RequestMapping(value = "/404.vm")
    public String pageVm404(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		return "404";
    }
	
	@RequestMapping(value = "/error/access-denied")
	@ResponseBody
    public String accessDenied(
            HttpServletRequest request, 
            HttpServletResponse response
            ) throws IOException {
		return "Illegal status!";
    }
}
