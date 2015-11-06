package com.test.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * @Description 话题审核的页请求处理控制器
 * @author gaopengbd
 * @date 2014年9月2日 下午3:58:16 
 * @version V1.0
 */
@Controller
@RequestMapping("/cooperation")
public class CooperationController {
	
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CooperationController.class);
	
	@RequestMapping(value = "/")
    public String defult(
            HttpServletRequest request, HttpServletResponse response,
            Model model) {
		ControllerHelper.initBasicInfo(request, model, "对外合作管理", "/cooperation/");
		
		return "cooperation/list";
    }
	
	@RequestMapping(value = "/{id}/mapping")
    public String mapping(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable Integer id,
            CooperationManager coop,
            Model model) {
		ControllerHelper.initBasicInfo(request, model,"对外合作管理", "/cooperation/", "分类对应管理", "/cooperation/" + id + "/mapping");
		CooperationManager manager = cooperationManagerService.findById(id);
		model.addAttribute("manager", manager);
		
		return "cooperation/mapping";
    }
	
	@RequestMapping(value = "/{id}/bookList")
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public String bookList(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable Integer id,
            CooperationManager coop,
            Model model) {
		ControllerHelper.initBasicInfo(request, model,"对外合作管理", "/cooperation/", "书单管理", "/cooperation/" + id + "/bookList");
		CooperationManager manager = cooperationManagerService.findById(id);
		model.addAttribute("manager", manager);
		
		return "cooperation/bookList";
    }
	
	@RequestMapping(value = "/list", produces = "application/json")//, method = RequestMethod.GET
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public EUIDataGridPage<CooperationManager> list(
            HttpServletRequest request, HttpServletResponse response,
			CooperationManager coop,
            Model model) {
		//无分页
		EUIDataGridPage<CooperationManager> rt = new EUIDataGridPage<CooperationManager>();
		
		List<CooperationManager> coopList = cooperationManagerService.find(coop);
		if(coopList != null) {
			for(CooperationManager c : coopList){
				try {
					c.setInductiveNum(introductionService.countByThirdName(c.getName()));
					if(c.getInductiveNum() == null || c.getInductiveNum() < 1){
						c.setInductiveNum((int)introDetailService.countBook(c.getName()));
					}
					c.setDistributionNum(distributionService.countByThirdName(c.getName()));
					if(c.getDistributionNum() == null || c.getDistributionNum() < 1){
						c.setDistributionNum((int)distriDetailService.countBook(c.getName()));
					}
				} catch (Exception e) {
					logger.error(StackTrace.getExceptionTrace(e));
				}
			}
		}
		long num = cooperationManagerService.count(coop);
		
		rt.setRows(coopList);
		rt.setTotal(num);
		
		return rt;
    }
	
	@RequestMapping(value = "/dist/list", produces = "application/json")//, method = RequestMethod.GET
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public EUIDataGridPage<CooperationDistribution> distributionList(
            HttpServletRequest request, HttpServletResponse response,
            CooperationDistribution dist,
            Model model) {
		if(dist.getPageSize() == null || dist.getPageSize() <1){
			dist.setStartPage(1L);
			dist.setPageSize(10);
		}
		//无分页
		EUIDataGridPage<CooperationDistribution> rt = new EUIDataGridPage<CooperationDistribution>();
		
		List<CooperationDistribution> distList = distributionService.find(dist);
		long num = distributionService.count(dist);
		
		rt.setRows(distList);
		rt.setTotal(num);
		
		return rt;
    }
	
	@RequestMapping(value = "/intro/list", produces = "application/json")//, method = RequestMethod.GET
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public EUIDataGridPage<CooperationIntroduction> introductionList(
            HttpServletRequest request, HttpServletResponse response,
            CooperationIntroduction intro,
            Model model) {
		//默认显示10条
		if(intro.getPageSize() == null || intro.getPageSize() <1){
			intro.setStartPage(1L);
			intro.setPageSize(10);
		}
		//无分页
		EUIDataGridPage<CooperationIntroduction> rt = new EUIDataGridPage<CooperationIntroduction>();
		List<CooperationIntroduction> introList = introductionService.find(intro);
		long num = introductionService.count(intro);
		
		rt.setRows(introList);
		rt.setTotal(num);
		
		return rt;
    }
	
	@RequestMapping(value = "/{id}/bookList/list", produces = "application/json")//, method = RequestMethod.GET
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public EUIDataGridPage<CooperationGenreMapping> bookList(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable int id,
            Model model) {
		//无分页
		EUIDataGridPage<CooperationGenreMapping> rt = new EUIDataGridPage<CooperationGenreMapping>();
		
		List<CooperationGenreMapping> mappingList = cooperationGenreMappingService.findByThirdId(id);
		long num = cooperationGenreMappingService.countByThirdId(id);
		
		rt.setRows(mappingList);
		rt.setTotal(num);
		
		return rt;
    }
	
	@RequestMapping(value = "/{id}/mapping/list", produces = "application/json")//, method = RequestMethod.GET
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public EUIDataGridPage<CooperationGenreMapping> mapping(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable int id,
            Model model) {
		//无分页
		EUIDataGridPage<CooperationGenreMapping> rt = new EUIDataGridPage<CooperationGenreMapping>();
		List<CooperationGenreMapping> mappingList = cooperationGenreMappingService.findByThirdId(id);
		long num = cooperationGenreMappingService.countByThirdId(id);
		
		rt.setRows(mappingList);
		rt.setTotal(num);
		
		return rt;
    }
	
	@RequestMapping(value = "/{id}/mapping/download")
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public void mappingDownload(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable int id,
            Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");  
        request.setCharacterEncoding("UTF-8");
		//查看文件是否存在
		File mappingExcelFile = new File(Properties.getCoopExcelPath() + "mapping/"+ id + ".xlsx");
		response.setContentType("application/x-msdownload;");  
		CooperationManager coop = cooperationManagerService.findById(id);
		response.setHeader("Content-disposition", "attachment; filename="  
				+ new String(String.valueOf(coop.getName()+ "分类.xlsx").getBytes("utf-8"), "ISO8859-1"));  
		java.io.BufferedOutputStream bos = null;  
		bos = new BufferedOutputStream(response.getOutputStream());  
		java.io.BufferedInputStream bis = null; 
		byte[] buff = new byte[2048];  
		int bytesRead;  
		FileOutputStream fos = null;
		try {
			//读取数据库
			List<CooperationGenreMapping> mappingList = cooperationGenreMappingService.findByThirdId(id);
			@SuppressWarnings("resource")
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象
			sheet.setColumnWidth(1, 5000);// 设置第二列的宽度为
			sheet.setColumnWidth(2, 5000);// 设置第二列的宽度为
			sheet.setColumnWidth(3, 5000);// 设置第二列的宽度为
			XSSFCellStyle style = workBook.createCellStyle();// 创建样式对象
			// 设置对齐方式
			//setStyle(style);
			//写excel，第一行是头
			writeMapping(sheet, style, mappingList, coop.getName());
			if(!mappingExcelFile.getParentFile().exists()){
				mappingExcelFile.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(mappingExcelFile); 
			workBook.write(fos);// 
				
			response.setHeader("Content-Length",  String.valueOf(mappingExcelFile.length()));
			bis = new BufferedInputStream(new FileInputStream(mappingExcelFile)); 
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
			    bos.write(buff, 0, bytesRead);  
			}  
		} catch (Exception e) {
			logger.error(StackTrace.getExceptionTrace(e));
		} finally {
			if (bis != null) bis.close();  
			if (bos != null) bos.close();
			if (fos != null) fos.close();
		}
    }
	
	@RequestMapping(value = "/intro/download")
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public void introDownload(
            HttpServletRequest request, HttpServletResponse response,
            String thirdName,
            Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");  
        request.setCharacterEncoding("UTF-8");
		//查看文件是否存在
		File excelFile = new File(Properties.getCoopExcelPath() + "intro/"+ thirdName + ".xlsx");
		response.setContentType("application/x-msdownload;");  
		response.setHeader("Content-disposition", "attachment; filename=" 
				+ new String((thirdName + "-版权引入.xlsx").getBytes("utf-8"), "ISO8859-1"));  
		java.io.BufferedOutputStream bos = null;  
		bos = new BufferedOutputStream(response.getOutputStream());  
		java.io.BufferedInputStream bis = null; 
		byte[] buff = new byte[2048];  
		int bytesRead;  
		FileOutputStream fos = null;
		try {
			if(!excelFile.exists()){
				//读取数据库
				List<CooperationIntroduction> introList = introductionService.findByThirdName(thirdName);
				@SuppressWarnings("resource")
				XSSFWorkbook workBook = new XSSFWorkbook();
				XSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象
				sheet.setColumnWidth(1, 5000);// 设置第二列的宽度为
				sheet.setColumnWidth(2, 5000);// 设置第二列的宽度为
				sheet.setColumnWidth(3, 5000);// 设置第二列的宽度为
				XSSFCellStyle style = workBook.createCellStyle();// 创建样式对象
				// 设置对齐方式
				//setStyle(style);
				writeIntroduction(sheet, style, introList);
				if(!excelFile.getParentFile().exists()){
					excelFile.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(excelFile); 
				workBook.write(fos);// 
			}else {
				response.setHeader("Content-Length",  String.valueOf(excelFile.length()));
			}
				
			bis = new BufferedInputStream(new FileInputStream(excelFile)); 
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
			    bos.write(buff, 0, bytesRead);  
			}  
		} catch (Exception e) {
			logger.error(StackTrace.getExceptionTrace(e));
		} finally {
			if (bis != null) bis.close();  
			if (bos != null) bos.close();
			if (fos != null) fos.close();
		}
    }
	
	@RequestMapping(value = "/dist/download")
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public void distDownload(
            HttpServletRequest request, HttpServletResponse response,
            String thirdName,
            Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");  
        request.setCharacterEncoding("UTF-8");
		//查看文件是否存在
		File excelFile = new File(Properties.getCoopExcelPath() + "dist/"+ thirdName + ".xlsx");
		response.setContentType("application/x-msdownload;");  
		response.setHeader("Content-disposition", "attachment; filename=" 
				+ new String((thirdName + "-版权分销.xlsx").getBytes("utf-8"), "ISO8859-1"));  
		java.io.BufferedOutputStream bos = null;  
		bos = new BufferedOutputStream(response.getOutputStream());  
		java.io.BufferedInputStream bis = null; 
		byte[] buff = new byte[2048];  
		int bytesRead;  
		FileOutputStream fos = null;
		try {
			if(!excelFile.exists()){
				//读取数据库
				List<CooperationDistribution> distList = distributionService.findByThirdName(thirdName);
				@SuppressWarnings("resource")
				XSSFWorkbook workBook = new XSSFWorkbook();
				XSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象
				sheet.setColumnWidth(1, 5000);// 设置第二列的宽度为
				sheet.setColumnWidth(2, 5000);// 设置第二列的宽度为
				sheet.setColumnWidth(3, 5000);// 设置第二列的宽度为
				sheet.setColumnWidth(4, 5000);// 设置第二列的宽度为
				XSSFCellStyle style = workBook.createCellStyle();// 创建样式对象
				// 设置对齐方式
				//setStyle(style);
				writeDistribution(sheet, style, distList);
				if(!excelFile.getParentFile().exists()){
					excelFile.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(excelFile); 
				workBook.write(fos);// 
			}else {
				response.setHeader("Content-Length",  String.valueOf(excelFile.length()));
			}
				
			bis = new BufferedInputStream(new FileInputStream(excelFile)); 
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
			    bos.write(buff, 0, bytesRead);  
			}  
		} catch (Exception e) {
			logger.error(StackTrace.getExceptionTrace(e));
		} finally {
			if (bis != null) bis.close();  
			if (bos != null) bos.close();
			if (fos != null) fos.close();
		}
    }
	
	static void setStyle(XSSFCellStyle style){
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置边框
		style.setBorderTop(HSSFCellStyle.BORDER_THICK);// 顶部边框粗线
		style.setTopBorderColor(HSSFColor.RED.index);// 设置为红色
		style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 底部边框双线
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);// 左边边框
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);// 右边边框
		// 格式化日期
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
	}
	
	private static void writeMapping(XSSFSheet sheet,XSSFCellStyle style,List<CooperationGenreMapping> mappingList, String thirdName){
		XSSFRow row = sheet.createRow(0);// 创建一个行对象
		XSSFCell cell1 = row.createCell(0);// 创建单元格
		XSSFCell cell2 = row.createCell(1);// 创建单元格
		XSSFCell cell3 = row.createCell(2);// 创建单元格
		cell1.setCellStyle(style);// 应用样式对象
		cell2.setCellStyle(style);// 应用样式对象
		cell3.setCellStyle(style);// 应用样式对象
		cell1.setCellValue("序号");
		cell2.setCellValue(thirdName + "分类");
		cell3.setCellValue("豆瓜分类");
		if(mappingList != null ){
			int index = 1;
			for(CooperationGenreMapping mapping : mappingList){
				XSSFRow rowData = sheet.createRow(index);// 创建一个行对象
				XSSFCell cellData1 = rowData.createCell(0);// 创建单元格
				XSSFCell cellData2 = rowData.createCell(1);// 创建单元格
				XSSFCell cellData3 = rowData.createCell(2);// 创建单元格
				
				cellData1.setCellValue(index);
				cellData2.setCellValue(mapping.getThirdGenre());
				cellData3.setCellValue(mapping.getDgGenre());
				
				cellData1.setCellStyle(style);// 应用样式对象
				cellData2.setCellStyle(style);// 应用样式对象
				cellData3.setCellStyle(style);// 应用样式对象
				index++;
			}
		}
	}
	
	private static void writeIntroduction(XSSFSheet sheet,XSSFCellStyle style,List<CooperationIntroduction> introList){
		XSSFRow row = sheet.createRow(0);// 创建一个行对象
		XSSFCell cell1 = row.createCell(0);// 创建单元格
		XSSFCell cell2 = row.createCell(1);// 创建单元格
		XSSFCell cell3 = row.createCell(2);// 创建单元格
		cell1.setCellStyle(style);// 应用样式对象
		cell2.setCellStyle(style);// 应用样式对象
		cell3.setCellStyle(style);// 应用样式对象
		cell1.setCellValue("序号");
		cell2.setCellValue("书名");
		cell3.setCellValue("引入合作方");
		if(introList != null ){
			int index = 1;
			for(CooperationIntroduction intro : introList){
				XSSFRow rowData = sheet.createRow(index);// 创建一个行对象
				XSSFCell cellData1 = rowData.createCell(0);// 创建单元格
				XSSFCell cellData2 = rowData.createCell(1);// 创建单元格
				XSSFCell cellData3 = rowData.createCell(2);// 创建单元格
				
				cellData1.setCellValue(index);
				cellData2.setCellValue(intro.getBookName());
				cellData3.setCellValue(intro.getThirdName());
				
				cellData1.setCellStyle(style);// 应用样式对象
				cellData2.setCellStyle(style);// 应用样式对象
				cellData3.setCellStyle(style);// 应用样式对象
				index++;
			}
		}
	}
	
	private static void writeDistribution(XSSFSheet sheet,XSSFCellStyle style,List<CooperationDistribution> distList){
		XSSFRow row = sheet.createRow(0);// 创建一个行对象
		XSSFCell cell1 = row.createCell(0);// 创建单元格
		XSSFCell cell2 = row.createCell(1);// 创建单元格
		XSSFCell cell3 = row.createCell(2);// 创建单元格
		XSSFCell cell4 = row.createCell(3);// 创建单元格
		cell1.setCellStyle(style);// 应用样式对象
		cell2.setCellStyle(style);// 应用样式对象
		cell3.setCellStyle(style);// 应用样式对象
		cell4.setCellStyle(style);// 应用样式对象
		cell1.setCellValue("序号");
		cell2.setCellValue("书名");
		cell3.setCellValue("分销合作方");
		cell4.setCellValue("VIP起始章节");
		if(distList != null ){
			int index = 1;
			for(CooperationDistribution dist : distList){
				XSSFRow rowData = sheet.createRow(index);// 创建一个行对象
				XSSFCell cellData1 = rowData.createCell(0);// 创建单元格
				XSSFCell cellData2 = rowData.createCell(1);// 创建单元格
				XSSFCell cellData3 = rowData.createCell(2);// 创建单元格
				XSSFCell cellData4 = rowData.createCell(3);// 创建单元格
				
				cellData1.setCellValue(index);
				cellData2.setCellValue(dist.getBookName());
				cellData3.setCellValue(dist.getThirdName());
				cellData4.setCellValue(dist.getVipChaptersStart());
				
				cellData1.setCellStyle(style);// 应用样式对象
				cellData2.setCellStyle(style);// 应用样式对象
				cellData3.setCellStyle(style);// 应用样式对象
				cellData4.setCellStyle(style);// 应用样式对象
				index++;
			}
		}
	}
	
	/**
	 * 创建合作方
	 * @param request
	 * @param response
	 * @param category
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
    public int managerAdd(
            HttpServletRequest request, HttpServletResponse response,
            CooperationManager coop
    		) throws IllegalStateException, IOException {
		
		if(StringUtils.isEmpty(coop.getName())){
			return 3;
		}
		if(coop.getName().length() > 32){
			return 3;
		}
		if(cooperationManagerService.findByName(coop.getName()) != null){
			return 2;
		}
		try {
			return cooperationManagerService.create(coop);
		} catch (Exception e) {
			logger.error(StackTrace.getExceptionTrace(e));
		}
		return 0;
		
    }
	
	/**
	 * 修改合作方
	 * @param request
	 * @param response
	 * @param category
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/doUpdate")
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public int doUpdate(
            HttpServletRequest request, HttpServletResponse response,
            CooperationManager coop,
            String cooperName
    		) throws IllegalStateException, IOException {
		//check
		if(coop.getId() == null || coop.getId() < 1){
			return 3;
		}
		if(coop.getName() != null && coop.getName().length() > 32){
			return 3;
		}
		//检测是否有变更
		if(cooperationManagerService.findByName(coop.getName()) != null){
			return 2;
		}
		//判断是否有作品分销或者引入过
		if(!StringUtils.isEmpty(cooperName)){
			if(introDetailService.countByName(cooperName) > 0) {
				return 4;
			}
			if(distriDetailService.countByName(cooperName) > 0) {
				return 4;
			}
		}
		try {
			return cooperationManagerService.update(coop);
		} catch (Exception e) {
			logger.error(StackTrace.getExceptionTrace(e));
		}
		return 0;
		
    }
	
	/**
	 * 设置与合作方的分类映射
	 * @param request
	 * @param response
	 * @param mapping
	 * @param model
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveMappingExcel")
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public int saveMappingExcel(
            HttpServletRequest request, HttpServletResponse response,
            CooperationGenreMapping mapping,
            Model model) throws IllegalStateException, IOException {
		int res = 0;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("mappingExcelFile");//获得图片
			if(excelFile == null) {
				return 6;
			}
			String type = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf("."),excelFile.getOriginalFilename().length());
			//校验 2007
			if(".xls".equals(type)){
				return 4;
			}
			if(!".xlsx".equals(type) ){//&& !
				return 2;
			}
			if(excelFile.getSize() > (5 * 1024 * 1024)) {
				return 3;
			}
			
			List<CooperationGenreMapping> mappingList = readExcel2007ForMapping(excelFile.getInputStream(), true);
			if(mappingList == null) {
				//文件内容错误，解析失败
				return 5;
			}
			//更新到数据库
			cooperationGenreMappingService.delByThirdId(mapping.getThirdId());
			cooperationGenreMappingService.create(mappingList, mapping.getThirdId());
			//TODO 保存到本地,bms不考虑集群，所以此处文件直接给下载使用 
			File mappingExcelFile = new File(Properties.getCoopExcelPath()  + mapping.getThirdId() + ".xlsx");
			if(!mappingExcelFile.getParentFile().exists()){
				mappingExcelFile.getParentFile().mkdirs();
			}
			excelFile.transferTo(mappingExcelFile);
			res = 1;
		}catch (Exception e) {  
            logger.error(StackTrace.getExceptionTrace(e));
        }
		return res;
	}
	
	/**
	 * 保存版权引入书单
	 * @param request
	 * @param response
	 * @param intro
	 * @param model
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveIntroExcel")
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public int saveIntroExcel(
            HttpServletRequest request, HttpServletResponse response,
            CooperationIntroduction intro,
            Model model) throws IllegalStateException, IOException {
		int res = 0;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("excelFile");//获得图片
			if(excelFile == null) {
				return 6;
			}
			intro.setThirdName(URLDecoder.decode(intro.getThirdName(),"UTF-8"));
			String type = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf("."),excelFile.getOriginalFilename().length());
			//校验 2007
			if(".xls".equals(type)){
				return 4;
			}
			if(!".xlsx".equals(type) ){//&& !
				return 2;
			}
			if(excelFile.getSize() > (5 * 1024 * 1024)) {
				return 3;
			}
			
			List<CooperationIntroduction> introList = readExcel2007ForIntroduction(excelFile.getInputStream(), true);
			if(introList == null) {
				//文件内容错误，解析失败
				//TODO 与清空的情况混淆
				return 5;
			}
			//更新到数据库
			introductionService.del(intro);
			for(CooperationIntroduction i : introList){
				try {
					introductionService.create(i);
				} catch(Exception e){
					logger.error(StackTrace.getExceptionTrace(e));
				}
			}
			//TODO 保存到本地,bms不考虑集群，所以此处文件直接给下载使用 
			File tempExcelFile = new File(Properties.getCoopExcelPath() + "intro/" + intro.getThirdName() + ".xlsx");
			if(!tempExcelFile.getParentFile().exists()){
				tempExcelFile.getParentFile().mkdirs();
			}
			excelFile.transferTo(tempExcelFile);
			//更新合作方的版权引入数量
			CooperationManager coop = new CooperationManager();
			coop.setName(intro.getThirdName());;
			coop.setInductiveNum(introList.size());
			cooperationManagerService.updateByName(coop);
			res = 1;
		}catch (Exception e) {  
            logger.error(StackTrace.getExceptionTrace(e));
        }
		return res;
	}
	
	/**
	 * 保存版权分销书单
	 * @param request
	 * @param response
	 * @param dist
	 * @param model
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveDistExcel")
	@ResponseBody
	@DrmPermission(roles = {AuthRoles.Admin, AuthRoles.Book_Club, AuthRoles.Operation, AuthRoles.Senior_Editor, AuthRoles.Boss})
    public int saveDistExcel(
            HttpServletRequest request, HttpServletResponse response,
            CooperationDistribution dist,
            Model model) throws IllegalStateException, IOException {
		int res = 0;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("excelFile");//获得图片
			if(excelFile == null) {
				return 6;
			}
			dist.setThirdName(URLDecoder.decode(dist.getThirdName(),"UTF-8"));
			String type = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf("."),excelFile.getOriginalFilename().length());
			//校验 2007
			if(".xls".equals(type)){
				return 4;
			}
			if(!".xlsx".equals(type) ){//&& !
				return 2;
			}
			if(excelFile.getSize() > (5 * 1024 * 1024)) {
				return 3;
			}
			
			List<CooperationDistribution> distList = readExcel2007ForDistribution(excelFile.getInputStream(), true);
			if(distList == null) {
				//文件内容错误，解析失败
				//TODO 与清空的情况混淆
				return 5;
			}
			//更新到数据库
			distributionService.del(dist);
			for(CooperationDistribution d : distList){
				try {
					distributionService.create(d);
				} catch(Exception e){
					logger.error(StackTrace.getExceptionTrace(e));
				}
			}
			//TODO 保存到本地,bms不考虑集群，所以此处文件直接给下载使用 
			File tempExcelFile = new File(Properties.getCoopExcelPath() + "dist/" + dist.getThirdName() + ".xlsx");
			if(!tempExcelFile.getParentFile().exists()){
				tempExcelFile.getParentFile().mkdirs();
			}
			excelFile.transferTo(tempExcelFile);
			
			//更新合作方的版权分销数量
			CooperationManager coop = new CooperationManager();
			coop.setName(dist.getThirdName());;
			coop.setDistributionNum(distList.size());
			cooperationManagerService.updateByName(coop);
			
			res = 1;
		}catch (Exception e) {  
            logger.error(StackTrace.getExceptionTrace(e));
        }
		return res;
	}
	
	public static List<CooperationGenreMapping> readExcel2007ForMapping(InputStream is, boolean close) throws IOException {
		List<CooperationGenreMapping> mappingList = new ArrayList<CooperationGenreMapping>();
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			//第一行忽略 i=sheet.getFirstRowNum();
			for(int i=1;i<sheet.getPhysicalNumberOfRows();i++) {
				 XSSFRow row = sheet.getRow(i);
				 if(row == null) {
					 continue;
				 }
				 Object value;
				 CooperationGenreMapping mapping = new CooperationGenreMapping();
				 for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) { 
					 XSSFCell cell = row.getCell(j);
					 if(cell == null) {
						 continue;
					 }
					 switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							if ("@".equals(cell.getCellStyle().getDataFormatString())) {
								value = SystemConfig.YHS_DATEFORMAT.format(cell.getNumericCellValue());

							} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
								value = SystemConfig.DF_INT.format(cell.getNumericCellValue());
							} else {
								value = SystemConfig.YHS_DATEFORMAT.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
							}
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							value = cell.getBooleanCellValue();
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							value = "";
							break;
						default:
							value = cell.toString();
					}
					if (value == null || "".equals(value)) {
						continue;
					}
					
					if(j == 0){
						mapping.setId(Integer.parseInt(value.toString()));
					}
					if(j == 1){
						mapping.setThirdGenre(value.toString());
					}
					if(j == 2){
						mapping.setDgGenre(value.toString());;
					}
				 }
				 mappingList.add(mapping);
			}
			
		}catch (Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
			return null;
		} finally{
			if(close && is != null){
				try {
					is.close();
				} catch (IOException e) {
					logger.error(StackTrace.getExceptionTrace(e));;
				}
				is = null;
			}
		}
		
		return mappingList;
	}
	
	public static List<CooperationIntroduction> readExcel2007ForIntroduction(InputStream is, boolean close) throws IOException {
		List<CooperationIntroduction> introList = new ArrayList<CooperationIntroduction>();
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			//第一行忽略 i=sheet.getFirstRowNum();
			for(int i=1;i<sheet.getPhysicalNumberOfRows();i++) {
				 XSSFRow row = sheet.getRow(i);
				 if(row == null) {
					 continue;
				 }
				 Object value;
				 CooperationIntroduction intro = new CooperationIntroduction();
				 for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) { 
					 XSSFCell cell = row.getCell(j);
					 if(cell == null) {
						 continue;
					 }
					 switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							if ("@".equals(cell.getCellStyle().getDataFormatString())) {
								value = SystemConfig.YHS_DATEFORMAT.format(cell.getNumericCellValue());

							} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
								value = SystemConfig.DF_INT.format(cell.getNumericCellValue());
							} else {
								value = SystemConfig.YHS_DATEFORMAT.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
							}
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							value = cell.getBooleanCellValue();
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							value = "";
							break;
						default:
							value = cell.toString();
					}
					if (value == null || "".equals(value)) {
						continue;
					}
					
					if(j == 0){
						//intro.setId(Integer.parseInt(value.toString()));
					}
					if(j == 1){
						intro.setBookName(value.toString());
					}
					if(j == 2){
						intro.setThirdName(value.toString());
					}
				 }
				 introList.add(intro);
			}
			
		}catch (Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
			return null;
		} finally{
			if(close && is != null){
				try {
					is.close();
				} catch (IOException e) {
					logger.error(StackTrace.getExceptionTrace(e));;
				}
				is = null;
			}
		}
		
		return introList;
	}
	
	public static List<CooperationDistribution> readExcel2007ForDistribution(InputStream is, boolean close) throws IOException {
		List<CooperationDistribution> distList = new ArrayList<CooperationDistribution>();
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			//第一行忽略 i=sheet.getFirstRowNum();
			for(int i=1;i<sheet.getPhysicalNumberOfRows();i++) {
				 XSSFRow row = sheet.getRow(i);
				 if(row == null) {
					 continue;
				 }
				 Object value;
				 CooperationDistribution dist = new CooperationDistribution();
				 for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) { 
					 XSSFCell cell = row.getCell(j);
					 if(cell == null) {
						 continue;
					 }
					 switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							if ("@".equals(cell.getCellStyle().getDataFormatString())) {
								value = SystemConfig.YHS_DATEFORMAT.format(cell.getNumericCellValue());

							} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
								value = SystemConfig.DF_INT.format(cell.getNumericCellValue());
							} else {
								value = SystemConfig.YHS_DATEFORMAT.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
							}
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							value = cell.getBooleanCellValue();
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							value = "";
							break;
						default:
							value = cell.toString();
					}
					if (value == null || "".equals(value)) {
						continue;
					}
					
					if(j == 0){
						//dist.setId(Integer.parseInt(value.toString()));
					}
					if(j == 1){
						dist.setBookName(value.toString());
					}
					if(j == 2){
						dist.setThirdName(value.toString());
					}
					if(j == 3){
						dist.setVipChaptersStart(Integer.parseInt(value.toString()));
					}
				 }
				 distList.add(dist);
			}
			
		}catch (Exception e){
			logger.error(StackTrace.getExceptionTrace(e));
			return null;
		} finally{
			if(close && is != null){
				try {
					is.close();
				} catch (IOException e) {
					logger.error(StackTrace.getExceptionTrace(e));;
				}
				is = null;
			}
		}
		
		return distList;
	}
	
	/**
	 * 处理匹配不到url的情况
	 */
	@RequestMapping(value = "*")
    public String error(
            HttpServletRequest request, HttpServletResponse response,Model model) {
		
		return "error/404";
    }
	
}
