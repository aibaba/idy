package com.idy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idy.redis.DgShardedJedis;

/**
 * 对外合作接口：分销
 * 2015-04-15 与小米对接
 * @author gaopengbd
 *
 */
@Controller
@RequestMapping("/360Book")
public class CoopDistriTo360Controller {
	
	@Resource(name = "jedisClient")
	private DgShardedJedis jedis;
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CoopDistriTo360Controller.class);
	
	public CoopDistriTo360Controller(){
		super();
	}
	
	/**
	 * 获取全部的书,包括增量接口
	 * @param request
	 * @param response
	 * @param bookId
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/booklist.aspx", method = RequestMethod.GET)
	@ResponseBody
    public void bookList(
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
    }
	
}

