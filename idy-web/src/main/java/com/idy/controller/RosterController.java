package com.idy.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	
	public static Map<Integer, String> sheetMap = new HashMap<Integer, String>();
	
	static {
		sheetMap.put(1, "在职员工");
	}
	
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
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/serving/export/{sheetId}/{type}")
	@ResponseBody
    public void exportExcel(
            HttpServletRequest request,
            @PathVariable Integer sheetId,
            @PathVariable String type,
            String ids,
            HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		/*int res = 0;
		if(StringUtils.isEmpty(ids)){
			return -1;
		}*/
		String fileName = sheetMap.get(sheetId) + "." + type;
		response.setContentType("application/x-msdownload;");  
		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
		java.io.BufferedOutputStream bos = null;  
		java.io.BufferedInputStream bis = null;
		byte[] buff = new byte[2048];  
		int bytesRead;  
		FileOutputStream fos = null;
		try {
			//TODO config path
			File excelFile = new File("D:/idy/"+ fileName);
			String[] idArr = ids.split(",");
			List<Excel> list = new ArrayList<Excel>();
			if("all".equals(ids)){
				Integer version = excelService.selectMaxVersion(sheetId);
				Excel excel = new Excel();
				excel.setVersion(version);//每次查询最大的version的数据
				excel.setSheetId(sheetId);
				list = excelService.find(excel);
			}else {
				for(String idstr : idArr) {
					Excel e = excelService.findById(Long.parseLong(idstr));
					list.add(e);
				}
			}
			Workbook workBook = null;
			if("xls".equals(type)){
				workBook = new HSSFWorkbook();
			}else {
				workBook = new XSSFWorkbook();
			}
			Sheet sheet = workBook.createSheet();
			CellStyle style = workBook.createCellStyle();// 创建样式对象
			if(list != null) {
				for(int i=1; i<= list.size(); i++){
					Row rowData = sheet.createRow(i);// 创建一个行对象
					Cell c1 = rowData.createCell(0);// 创建单元格
					Cell c2 = rowData.createCell(1);// 创建单元格
					Cell c3 = rowData.createCell(2);// 创建单元格
					Cell c4 = rowData.createCell(3);// 创建单元格
					Cell c5 = rowData.createCell(4);// 创建单元格
					Cell c6 = rowData.createCell(5);// 创建单元格
					Cell c7 = rowData.createCell(6);// 创建单元格
					Cell c8 = rowData.createCell(7);// 创建单元格
					Cell c9 = rowData.createCell(8);// 创建单元格
					Cell c10 = rowData.createCell(9);// 创建单元格
					Cell c11 = rowData.createCell(10);// 创建单元格
					
					c1.setCellValue(list.get(i-1).getC01());
					c2.setCellValue(list.get(i-1).getC02());
					c3.setCellValue(list.get(i-1).getC03());
					c4.setCellValue(list.get(i-1).getC04());
					c5.setCellValue(list.get(i-1).getC05());
					c6.setCellValue(list.get(i-1).getC06());
					c7.setCellValue(list.get(i-1).getC07());
					c8.setCellValue(list.get(i-1).getC08());
					c9.setCellValue(list.get(i-1).getC09());
					c10.setCellValue(list.get(i-1).getC10());
					c11.setCellValue(list.get(i-1).getC11());
					
					c1.setCellStyle(style);// 应用样式对象
					c2.setCellStyle(style);// 应用样式对象
					c3.setCellStyle(style);// 应用样式对象
				}
			}
			
			if(!excelFile.getParentFile().exists()){
				excelFile.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(excelFile); 
			workBook.write(fos);
			response.addHeader("Content-Length", "" + excelFile.length());
			bos = new BufferedOutputStream(response.getOutputStream());
			bis = new BufferedInputStream(new FileInputStream(excelFile)); 
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
				bos.write(buff, 0, bytesRead);  
			} 
		} catch (Exception e) {
			logger.error("导入Excel文件时异常", e);
		} finally {
			if (bis != null) bis.close();  
			if (bos != null) bos.close();
			if (fos != null) fos.close();
		}
		
		//return res;
    }
}

