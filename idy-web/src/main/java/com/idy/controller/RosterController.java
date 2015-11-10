package com.idy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *@Description: 
 *@author penggao15@creditease.cn
 *@date 2015年11月9日 下午4:01:51 
 *@version V1.0
 */
@Controller
@RequestMapping("/roster")
public class RosterController {
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RosterController.class);
	
	@RequestMapping(value = "/{num}")
    public String mapping(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String num,//sheet的编号
            Model model) {
		model.addAttribute("num", num);
		
		return "excel/sheet" + num;
    }
	
	@RequestMapping(value = "/serving")
    public String serving(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		
		return "roster/serving";
    }
	
	/**
	 * 获取全部的书,包括增量接口
	 * @param request
	 * @param response
	 * @param bookId
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/json.aspx", method = RequestMethod.GET)
	@ResponseBody
    public void bookList(
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
    }
	
}

