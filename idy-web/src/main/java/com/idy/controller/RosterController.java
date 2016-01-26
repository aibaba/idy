package com.idy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.idy.base.BaseController;
import com.idy.base.EUIDataGridPage;
import com.idy.domain.Excel;
import com.idy.service.ExcelService;
import com.idy.utils.ExcelUtils;

/**
 *@Description: 
 *@author penggao15@creditease.cn
 *@date 2015年11月9日 下午4:01:51 
 *@version V1.0
 */
@Controller
@RequestMapping("/roster")
public class RosterController extends BaseController {
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RosterController.class);
	
	@Autowired
	private ExcelService excelService;
	
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
		initBasicInfo(request, model, "花名册", "/roster/serving", "在职员工", "/roster/serving");
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
	@RequestMapping(value = "/serving/list.json")
	@ResponseBody
    public EUIDataGridPage<Excel> bookList(
            HttpServletRequest request, 
            Excel excel,
            Integer rows,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Integer version = excelService.selectMaxVersion(excel.getSheetId());
		excel.setVersion(version);//每次查询最大的version的数据
		List<Excel> list = excelService.find(excel);
		EUIDataGridPage<Excel> rt = new EUIDataGridPage<Excel>();
		rt.setRows(list);
		rt.setTotal(rows);
		return rt;
    }
	
	@RequestMapping(value = "/serving/import.json")
	@ResponseBody
    public int importExcel(
            HttpServletRequest request,
            @RequestParam(required = true) Integer sheetId,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		int res = 0;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("excelFile");
			if(excelFile != null) {
				String type = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf("."),excelFile.getOriginalFilename().length());
				List<Excel> list = null;
				if(".xls".equals(type)){
					list = ExcelUtils.parse_97_2003(excelFile, sheetId);
				} else if(".xlsx".equals(type) ){
					list = ExcelUtils.parse_07_2010(excelFile, sheetId);
				}
				res = excelService.create(list, sheetId);
			}
		} catch (Exception e) {
			logger.error("导入Excel文件时异常", e);
			return 0;
		}
		
		return res;
    }
}

