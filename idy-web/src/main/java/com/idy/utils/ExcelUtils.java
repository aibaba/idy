package com.idy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
				 for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					 HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					 if (hssfRow != null) {
						 HSSFCell c = hssfRow.getCell(0);
						 int last = hssfRow.getLastCellNum();
						 for(int k = 0 ; k<last ; k++){
							 System.out.println(getValue(c));
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
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
		   return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		   }
		}
}
