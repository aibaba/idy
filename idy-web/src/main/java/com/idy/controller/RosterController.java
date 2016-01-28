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
import com.idy.domain.Colume;
import com.idy.domain.Excel;
import com.idy.service.ColumeService;
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
	
	@Autowired
	private ColumeService columeService;

	public static Map<Integer, String> sheetMap = new HashMap<Integer, String>();
	
	static {
		sheetMap.put(1, "在职员工");
	}
	
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
				//保存列
				res = excelService.create(list.subList(1, list.size()), sheetId);
				Excel excel = list.subList(0, 1).get(0);
				columeService.create(this.getColumes(excel, sheetId));
			}
		} catch (Exception e) {
			logger.error("导入Excel文件时异常", e);
			return 0;
		}
		
		return res;
    }
	
	private List<Colume> getColumes(Excel e, Integer sheetId) {
		List<Colume> list = new ArrayList<Colume>();
		Colume c1 = new Colume();
		c1.setSheetId(sheetId);
		c1.setZnName(e.getC01());
		list.add(c1);
				
		Colume c2 = new Colume();
		c2.setSheetId(sheetId);
		c2.setZnName(e.getC02());
		list.add(c2);

		Colume c3 = new Colume();
		c3.setSheetId(sheetId);
		c3.setZnName(e.getC03());
		list.add(c3);
				
		Colume c4 = new Colume();
		c4.setSheetId(sheetId);
		c4.setZnName(e.getC04());
		list.add(c4);

		Colume c5 = new Colume();
		c5.setSheetId(sheetId);
		c5.setZnName(e.getC05());
		list.add(c5);
				
		Colume c6 = new Colume();
		c6.setSheetId(sheetId);
		c6.setZnName(e.getC06());
		list.add(c6);

		Colume c7 = new Colume();
		c7.setSheetId(sheetId);
		c7.setZnName(e.getC07());
		list.add(c7);
				
		Colume c8 = new Colume();
		c8.setSheetId(sheetId);
		c8.setZnName(e.getC08());
		list.add(c8);

		Colume c9 = new Colume();
		c9.setSheetId(sheetId);
		c9.setZnName(e.getC09());
		list.add(c9);
				
		Colume c10 = new Colume();
		c10.setSheetId(sheetId);
		c10.setZnName(e.getC10());
		list.add(c10);

		Colume c11 = new Colume();
		c11.setSheetId(sheetId);
		c11.setZnName(e.getC11());
		list.add(c11);
				
		Colume c12 = new Colume();
		c12.setSheetId(sheetId);
		c12.setZnName(e.getC12());
		list.add(c12);

		Colume c13 = new Colume();
		c13.setSheetId(sheetId);
		c13.setZnName(e.getC13());
		list.add(c13);
				
		Colume c14 = new Colume();
		c14.setSheetId(sheetId);
		c14.setZnName(e.getC14());
		list.add(c14);

		Colume c15 = new Colume();
		c15.setSheetId(sheetId);
		c15.setZnName(e.getC15());
		list.add(c15);
				
		Colume c16 = new Colume();
		c16.setSheetId(sheetId);
		c16.setZnName(e.getC16());
		list.add(c16);

		Colume c17 = new Colume();
		c17.setSheetId(sheetId);
		c17.setZnName(e.getC17());
		list.add(c17);
				
		Colume c18 = new Colume();
		c18.setSheetId(sheetId);
		c18.setZnName(e.getC18());
		list.add(c18);

		Colume c19 = new Colume();
		c19.setSheetId(sheetId);
		c19.setZnName(e.getC19());
		list.add(c19);

		Colume c20 = new Colume();
		c20.setSheetId(sheetId);
		c20.setZnName(e.getC20());
		list.add(c20);
		
		Colume c21 = new Colume();
		c21.setSheetId(sheetId);
		c21.setZnName(e.getC21());
		list.add(c21);
				
		Colume c22 = new Colume();
		c22.setSheetId(sheetId);
		c22.setZnName(e.getC22());
		list.add(c22);

		Colume c23 = new Colume();
		c23.setSheetId(sheetId);
		c23.setZnName(e.getC23());
		list.add(c23);
				
		Colume c24 = new Colume();
		c24.setSheetId(sheetId);
		c24.setZnName(e.getC24());
		list.add(c24);

		Colume c25 = new Colume();
		c25.setSheetId(sheetId);
		c25.setZnName(e.getC25());
		list.add(c25);
				
		Colume c26 = new Colume();
		c26.setSheetId(sheetId);
		c26.setZnName(e.getC26());
		list.add(c26);

		Colume c27 = new Colume();
		c27.setSheetId(sheetId);
		c27.setZnName(e.getC27());
		list.add(c27);
				
		Colume c28 = new Colume();
		c28.setSheetId(sheetId);
		c28.setZnName(e.getC28());
		list.add(c28);

		Colume c29 = new Colume();
		c29.setSheetId(sheetId);
		c29.setZnName(e.getC29());
		list.add(c29);

		Colume c30 = new Colume();
		c30.setSheetId(sheetId);
		c30.setZnName(e.getC30());
		list.add(c30);

		Colume c31 = new Colume();
		c31.setSheetId(sheetId);
		c31.setZnName(e.getC31());
		list.add(c31);
				
		Colume c32 = new Colume();
		c32.setSheetId(sheetId);
		c32.setZnName(e.getC32());
		list.add(c32);

		Colume c33 = new Colume();
		c33.setSheetId(sheetId);
		c33.setZnName(e.getC33());
		list.add(c33);
				
		Colume c34 = new Colume();
		c34.setSheetId(sheetId);
		c34.setZnName(e.getC34());
		list.add(c34);

		Colume c35 = new Colume();
		c35.setSheetId(sheetId);
		c35.setZnName(e.getC35());
		list.add(c35);
				
		Colume c36 = new Colume();
		c36.setSheetId(sheetId);
		c36.setZnName(e.getC36());
		list.add(c36);

		Colume c37 = new Colume();
		c37.setSheetId(sheetId);
		c37.setZnName(e.getC37());
		list.add(c37);
				
		Colume c38 = new Colume();
		c38.setSheetId(sheetId);
		c38.setZnName(e.getC38());
		list.add(c38);

		Colume c39 = new Colume();
		c39.setSheetId(sheetId);
		c39.setZnName(e.getC39());
		list.add(c39);

		Colume c40 = new Colume();
		c40.setSheetId(sheetId);
		c40.setZnName(e.getC40());
		list.add(c40);

		Colume c41 = new Colume();
		c41.setSheetId(sheetId);
		c41.setZnName(e.getC41());
		list.add(c41);
				
		Colume c42 = new Colume();
		c42.setSheetId(sheetId);
		c42.setZnName(e.getC42());
		list.add(c42);

		Colume c43 = new Colume();
		c43.setSheetId(sheetId);
		c43.setZnName(e.getC43());
		list.add(c43);
				
		Colume c44 = new Colume();
		c44.setSheetId(sheetId);
		c44.setZnName(e.getC44());
		list.add(c44);

		Colume c45 = new Colume();
		c45.setSheetId(sheetId);
		c45.setZnName(e.getC45());
		list.add(c45);
				
		Colume c46 = new Colume();
		c46.setSheetId(sheetId);
		c46.setZnName(e.getC46());
		list.add(c46);

		Colume c47 = new Colume();
		c47.setSheetId(sheetId);
		c47.setZnName(e.getC47());
		list.add(c47);
				
		Colume c48 = new Colume();
		c48.setSheetId(sheetId);
		c48.setZnName(e.getC48());
		list.add(c48);
		
		return list;
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
			//第一行
			List<Colume> first = columeService.findBySheetId(sheetId);
			if(first != null){
				Row rowData = sheet.createRow(0);
				for(int i= 0; i<first.size(); i++){
					Cell c = rowData.createCell(i);
					c.setCellValue(first.get(i).getZnName());
				}
			}
			//设置内容
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
					Cell c12 = rowData.createCell(11);// 创建单元格
					Cell c13 = rowData.createCell(12);// 创建单元格
					Cell c14 = rowData.createCell(13);// 创建单元格
					Cell c15 = rowData.createCell(14);// 创建单元格
					Cell c16 = rowData.createCell(15);// 创建单元格
					Cell c17 = rowData.createCell(16);// 创建单元格
					Cell c18 = rowData.createCell(17);// 创建单元格
					Cell c19 = rowData.createCell(18);// 创建单元格
					Cell c20 = rowData.createCell(19);// 创建单元格
					Cell c21 = rowData.createCell(20);// 创建单元格
					Cell c22 = rowData.createCell(21);// 创建单元格
					Cell c23 = rowData.createCell(22);// 创建单元格
					Cell c24 = rowData.createCell(23);// 创建单元格
					Cell c25 = rowData.createCell(24);// 创建单元格
					Cell c26 = rowData.createCell(25);// 创建单元格
					Cell c27 = rowData.createCell(26);// 创建单元格
					Cell c28 = rowData.createCell(27);// 创建单元格
					Cell c29 = rowData.createCell(28);// 创建单元格
					Cell c30 = rowData.createCell(29);// 创建单元格
					Cell c31 = rowData.createCell(30);// 创建单元格
					Cell c32 = rowData.createCell(31);// 创建单元格
					Cell c33 = rowData.createCell(32);// 创建单元格
					Cell c34 = rowData.createCell(33);// 创建单元格
					Cell c35 = rowData.createCell(34);// 创建单元格
					Cell c36 = rowData.createCell(35);// 创建单元格
					Cell c37 = rowData.createCell(36);// 创建单元格
					Cell c38 = rowData.createCell(37);// 创建单元格
					Cell c39 = rowData.createCell(38);// 创建单元格
					Cell c40 = rowData.createCell(39);// 创建单元格
					Cell c41 = rowData.createCell(40);// 创建单元格
					Cell c42 = rowData.createCell(41);// 创建单元格
					Cell c43 = rowData.createCell(42);// 创建单元格
					Cell c44 = rowData.createCell(43);// 创建单元格
					Cell c45 = rowData.createCell(44);// 创建单元格
					Cell c46 = rowData.createCell(45);// 创建单元格
					Cell c47 = rowData.createCell(46);// 创建单元格
					Cell c48 = rowData.createCell(47);// 创建单元格
					
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
					c12.setCellValue(list.get(i-1).getC12());
					c13.setCellValue(list.get(i-1).getC13());
					c14.setCellValue(list.get(i-1).getC14());
					c15.setCellValue(list.get(i-1).getC15());
					c16.setCellValue(list.get(i-1).getC16());
					c17.setCellValue(list.get(i-1).getC17());
					c18.setCellValue(list.get(i-1).getC18());
					c19.setCellValue(list.get(i-1).getC19());
					c20.setCellValue(list.get(i-1).getC20());
					c21.setCellValue(list.get(i-1).getC21());
					c22.setCellValue(list.get(i-1).getC22());
					c23.setCellValue(list.get(i-1).getC23());
					c24.setCellValue(list.get(i-1).getC24());
					c25.setCellValue(list.get(i-1).getC25());
					c26.setCellValue(list.get(i-1).getC26());
					c27.setCellValue(list.get(i-1).getC27());
					c28.setCellValue(list.get(i-1).getC28());
					c29.setCellValue(list.get(i-1).getC29());
					c30.setCellValue(list.get(i-1).getC30());
					c31.setCellValue(list.get(i-1).getC31());
					c32.setCellValue(list.get(i-1).getC32());
					c33.setCellValue(list.get(i-1).getC33());
					c34.setCellValue(list.get(i-1).getC34());
					c35.setCellValue(list.get(i-1).getC35());
					c36.setCellValue(list.get(i-1).getC36());
					c37.setCellValue(list.get(i-1).getC37());
					c38.setCellValue(list.get(i-1).getC38());
					c39.setCellValue(list.get(i-1).getC39());
					c40.setCellValue(list.get(i-1).getC40());
					c41.setCellValue(list.get(i-1).getC41());
					c42.setCellValue(list.get(i-1).getC42());
					c43.setCellValue(list.get(i-1).getC43());
					c44.setCellValue(list.get(i-1).getC44());
					c45.setCellValue(list.get(i-1).getC45());
					c46.setCellValue(list.get(i-1).getC46());
					c47.setCellValue(list.get(i-1).getC47());
					c48.setCellValue(list.get(i-1).getC48());
					
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

