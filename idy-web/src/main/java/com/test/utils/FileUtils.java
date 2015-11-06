package com.test.utils;

import java.io.File;

public class FileUtils {
	
	/**
	 * 
	 * @param fileName 
	 * @return
	 */
	public static String getRealType(String fileName){
		if(fileName != null){
			return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		}
		return null;
	}
	
	public static String getType(String fileName){
		if(fileName != null){
			return fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
		return null;
	}

	public static String getType(File file){
		if(file != null && file.isFile()){
			return file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
		}
		return null;
	}
}
