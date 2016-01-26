package com.idy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.idy.constant.SystemConfig;
import com.idy.domain.Excel;

public class ExcelUtils {

	public static void main(String[] args) throws Exception {
		parseExcel97_2003();
		//parseExcel07_2013();
	}
	
	public static List<Excel> parse_07_2010(MultipartFile excelFile, Integer sheetId) throws Exception {
		List<Excel> list = new ArrayList<Excel>();;
		if(excelFile == null || excelFile.getInputStream() == null) {
			return list;
		}
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(excelFile.getInputStream());
			//只保存当前的sheet
			XSSFSheet sheet = wb.getSheetAt(sheetId-1);
			//TODO 第一行忽略 
			Date now = new Date();
			for(int i = 1; i< sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow _row = sheet.getRow(i);
				if(_row != null) {
					Excel e = getNewRow(excelFile, sheetId, _row, now);
					list.add(e);
				}
					
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(excelFile.getInputStream() != null){
				try {
					excelFile.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				excelFile = null;
			}
		}
		
		return list;
	}
	
	/**
	 * 以每个sheet为最小version的单位
	 * @param excelFile
	 * @return
	 * @throws Exception
	 */
	public static List<Excel> parse_97_2003(MultipartFile excelFile, Integer sheetId) throws Exception {
		List<Excel> list = new ArrayList<Excel>();;
		if(excelFile == null || excelFile.getInputStream() == null) {
			return list;
		}
		try {
			@SuppressWarnings("resource")
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelFile.getInputStream());
			Date now = new Date();
			//每个sheet
			//for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				//只保存当前sheetId的内容
				 HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetId-1);
				 //sheet的每行
				 int _lastRow = hssfSheet.getLastRowNum();
				 //TODO 第一行，列名暂时先过滤掉
				 for (int _rowNum = 1; _rowNum <= _lastRow; _rowNum++) {
					 HSSFRow _hssfRow = hssfSheet.getRow(_rowNum);
					 if (_hssfRow != null) {
						 list.add(getNewRow(excelFile, sheetId, _hssfRow, now));
					 }
				 }
			//}
		} finally{
			if(excelFile.getInputStream() != null){
				excelFile.getInputStream().close();
				excelFile = null;
			}
		}
		
		return list;
	}
	
	private static Excel getNewRow(MultipartFile excelFile, Integer sheetId,
			Row _row, Date now) {
		Excel e = new Excel();
		e.setExcelName(excelFile.getOriginalFilename());
		e.setSheetId(sheetId);
		e.setCreateTime(now);
		e.setUpdateTime(now);
		e.setC01(getValue(_row.getCell(0)));
		e.setC02(getValue(_row.getCell(1)));
		e.setC03(getValue(_row.getCell(2)));
		e.setC04(getValue(_row.getCell(3)));
		e.setC05(getValue(_row.getCell(4)));
		e.setC06(getValue(_row.getCell(5)));
		e.setC07(getValue(_row.getCell(6)));
		e.setC08(getValue(_row.getCell(7)));
		e.setC09(getValue(_row.getCell(8)));
		e.setC10(getValue(_row.getCell(9)));
		e.setC11(getValue(_row.getCell(10)));
		e.setC12(getValue(_row.getCell(11)));
		e.setC13(getValue(_row.getCell(12)));
		e.setC14(getValue(_row.getCell(13)));
		e.setC15(getValue(_row.getCell(14)));
		e.setC16(getValue(_row.getCell(15)));
		e.setC17(getValue(_row.getCell(16)));
		e.setC18(getValue(_row.getCell(17)));
		e.setC19(getValue(_row.getCell(18)));
		e.setC20(getValue(_row.getCell(19)));
		e.setC21(getValue(_row.getCell(20)));
		e.setC22(getValue(_row.getCell(21)));
		e.setC23(getValue(_row.getCell(22)));
		e.setC24(getValue(_row.getCell(23)));
		e.setC25(getValue(_row.getCell(24)));
		e.setC26(getValue(_row.getCell(25)));
		e.setC27(getValue(_row.getCell(26)));
		e.setC28(getValue(_row.getCell(27)));
		e.setC29(getValue(_row.getCell(28)));
		e.setC30(getValue(_row.getCell(29)));
		e.setC31(getValue(_row.getCell(30)));
		e.setC32(getValue(_row.getCell(31)));
		e.setC33(getValue(_row.getCell(32)));
		e.setC34(getValue(_row.getCell(33)));
		e.setC35(getValue(_row.getCell(34)));
		e.setC36(getValue(_row.getCell(35)));
		e.setC37(getValue(_row.getCell(36)));
		e.setC38(getValue(_row.getCell(37)));
		e.setC39(getValue(_row.getCell(38)));
		e.setC40(getValue(_row.getCell(39)));
		e.setC41(getValue(_row.getCell(40)));
		e.setC42(getValue(_row.getCell(41)));
		e.setC43(getValue(_row.getCell(42)));
		e.setC44(getValue(_row.getCell(43)));
		e.setC45(getValue(_row.getCell(44)));
		e.setC46(getValue(_row.getCell(45)));
		e.setC47(getValue(_row.getCell(46)));
		e.setC48(getValue(_row.getCell(47)));

		return e;
	}
	
	public static void parseExcel97_2003(){
		InputStream is = null;
		try {
			File f = new File("C:/Users/Administrator/Desktop/idy/花名册2015.11.6.xls");
			//File f = new File("C:/Users/Administrator/Desktop/idy/花名册2016.01.25.xlsx");
			System.out.println(f.getName());
			is = new FileInputStream(f);
			@SuppressWarnings("resource")
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				//每个sheet
				 HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				 if (hssfSheet == null) {
					 continue;
				 }
				 //sheet的每行
				 int _lastRow = hssfSheet.getLastRowNum();
				 for (int _rowNum = 0; _rowNum <= _lastRow; _rowNum++) {
					 //System.out.println("rowNum=" + _rowNum + ",lastRow=" + _lastRow);
					 HSSFRow _hssfRow = hssfSheet.getRow(_rowNum);
					 if (_hssfRow != null) {
						 for(int _k = 0 ; _k<_hssfRow.getLastCellNum() ; _k++){
							 String v = getValue(_hssfRow.getCell(_k));
							 //System.out.print(v);
							 System.out.println("{field:'c0" + _k + "',title:'" + v + "',width:100,align:'center'},");
						 }
					 }
					 System.out.println();
				 }
				 break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				is = null;
			}
		}
	}
	
	public static void parseExcel07_2013(){
		InputStream is = null;
		try {
			File f = new File("C:/Users/Administrator/Desktop/idy/花名册2016.01.25.xlsx");
			System.out.println(f.getName());
			is = new FileInputStream(f);
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			//第一行忽略 i=sheet.getFirstRowNum();
			for(int i = 0;i<sheet.getPhysicalNumberOfRows();i++) {
				XSSFRow _row = sheet.getRow(i);
				if(_row == null) {
					continue;
				}
				for (int j = _row.getFirstCellNum(); j <= _row.getLastCellNum(); j++) { 
					 XSSFCell cell = _row.getCell(j);
					 //System.out.println(cell.getStringCellValue());
					 System.out.print(getValue(cell)+ ",");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				is = null;
			}
		}
	}
	
	private static String getValue(Cell cell) {
		if(cell == null) {
			return "";
		}
		switch(cell.getCellType()){
			case HSSFCell.CELL_TYPE_FORMULA: 
				try {  
				     return SystemConfig.DF_DECUMAL.format(cell.getNumericCellValue());  
				} catch (IllegalStateException e) {  
				     return String.valueOf(cell.getRichStringCellValue());  
				} 
			case XSSFCell.CELL_TYPE_NUMERIC:
				if ("@".equals(cell.getCellStyle().getDataFormatString())) {
					return SystemConfig.YMS_FORMATE.format(cell.getNumericCellValue());
				} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
					return SystemConfig.DF_INT.format(cell.getNumericCellValue());
				} else {
					return SystemConfig.YMD_FORMATE.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				}
			case XSSFCell.CELL_TYPE_BOOLEAN:
				return "" + cell.getBooleanCellValue();
			case XSSFCell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case XSSFCell.CELL_TYPE_BLANK:
				return "";
			default:
				return cell.toString();
		}
	}
}
