package com.idy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idy.base.BaseController;
import com.idy.base.EUIDataGridPage;
import com.idy.constant.Constant;
import com.idy.domain.SheetLog;
import com.idy.service.SheetLogService;

/**
 * 首页、默认页控制
 *@Description: TODO
 *@author pein
 *@date 2015年11月10日 下午5:34:28 
 *@version V1.0
 */
@Controller
@RequestMapping("")
public class IndexController extends BaseController{
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private SheetLogService sheetLogService;
	
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
	
	@RequestMapping(value = "/log/roster")
    public String logRoster(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		initBasicInfo(request, model, "日志", "/log/roster", "在职-操作日志", "/log/roster");
		return "log/rosterLog";
    }
	
	@RequestMapping(value = "/log/list.json")
	@ResponseBody
    public EUIDataGridPage<SheetLog> logList(
            HttpServletRequest request, 
            SheetLog log,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<SheetLog> list = sheetLogService.find(log);
		if(list != null) {
			for(SheetLog l : list) {
				l.setCreateTimeStr(Constant.YHS_DATEFORMAT.format(l.getCreateTime()));
			}
		}
		long rows = sheetLogService.count(log);
		EUIDataGridPage<SheetLog> rt = new EUIDataGridPage<SheetLog>();
		rt.setRows(list);
		rt.setTotal(rows);
		return rt;
    }
}
