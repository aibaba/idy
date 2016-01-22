package com.idy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.idy.constant.SystemConfig;

public class ExcelUtils {

	public static void main(String[] args) {
		parseExcel2003();
	}
	
	public static void parseExcel2003(){
		InputStream is = null;
		try {
			File f = new File("C:/Users/Administrator/Desktop/idy/花名册2015.11.6.xls");
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
				 int lastRow = hssfSheet.getLastRowNum();
				 for (int rowNum = 0; rowNum <= lastRow; rowNum++) {
					 HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					 if (hssfRow != null) {
						 for(int k = 0 ; k<hssfRow.getLastCellNum() ; k++){
							 System.out.print(getValue(hssfRow.getCell(k))+ "$");
						 }
					 }
				 }
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
	
	public static void parseExcel2007(){
		InputStream is = null;
		try {
			File f = new File("C:/Users/Administrator/Desktop/idy/花名册2015.11.6.xlsx");
			System.out.println(f.getName());
			is = new FileInputStream(f);
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
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) { 
					 XSSFCell cell = row.getCell(j);
					 System.out.println(cell.getStringCellValue());
				}
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
	
	public static void parseExcel2010(){
		
	}
	
	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		}else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			 return String.valueOf(xssfRow.getNumericCellValue());
		}else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}
	
	private static String getValue(HSSFCell hssfCell) {
		if(hssfCell == null) {
			return "";
		}
		switch(hssfCell.getCellType()){
			case XSSFCell.CELL_TYPE_STRING:
				return hssfCell.getStringCellValue();
			case XSSFCell.CELL_TYPE_NUMERIC:
				if ("@".equals(hssfCell.getCellStyle().getDataFormatString())) {
					return SystemConfig.YMS_FORMATE.format(hssfCell.getNumericCellValue());
				} else if ("General".equals(hssfCell.getCellStyle().getDataFormatString())) {
					return SystemConfig.DF_INT.format(hssfCell.getNumericCellValue());
				} else {
					return SystemConfig.YMD_FORMATE.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue()));
				}
			case XSSFCell.CELL_TYPE_BOOLEAN:
				return "" + hssfCell.getBooleanCellValue();
			case XSSFCell.CELL_TYPE_BLANK:
				return "";
			default:
				return hssfCell.toString();
		}
	}
}
