package com.test.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

/**
 * 工具类：
 * 1.将数据库表映射为Bean类
 * 2.将数据库表映射为mapper文件
 * 3.生成dao类
 * 4.生成service类
 * 5.生成对应的TypeAlias
 * @author gaopengbd
 * 2014-08-26
 */
public class CodeUtils {
	
	static String jdbcDriver = "com.mysql.jdbc.Driver";
	
	static String DB = "idy";

	static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/" + DB;
	
	static String userName = "root";
	
	static String password= "243221";
	
	static final String java_file_type = ".java";
	
	static final String mapper_file_type = "Mapper.xml";
	
	static final String division = "_";
	
	static final String basePath = "D:/generate/";//生成文件的根目录
	
	static String author = "gaopeng";
	
	static Properties prop = System.getProperties();
	
	static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	static final Date current = new Date();
	
	static String now = df.format(current);
	
	static Map<String, String> fieldMap = new HashMap<String, String>();
	
	/**
	 * key : tableName , value : 主键
	 */
	static Map<String, String> primaryMap = new HashMap<String, String>();
	
	static String doMainPath = "com.idy.bean.";
	
	static String baseDomainPath = "com.idy.base.";
	
	static String daoPath = "com.idy.dao.";
	
	static String servicePath = "com.idy.service.";
	
	static boolean printAlias = true;
	
	static boolean tableToBean = true;
	
	static boolean tableToMapper = true;
	
	static boolean tableToDao = true;
	
	static boolean delFiles = false;
	
	static Set<String> javaKeywords = new HashSet<String>();
	/**
	 * 静态初始化
	 */
	static {
		//默认的根目录
		File base = new File(basePath);
		if(!base.exists()){
			base.mkdirs();
		}
		//作者
		author = prop.getProperty("user.name") == null ? author : prop.getProperty("user.name");
		
		//数据库类型映射java类型
		fieldMap.put("VARCHAR", "String");
		fieldMap.put("INT UNSIGNED", "Integer");
		fieldMap.put("BIGINT", "Long");
		fieldMap.put("INT", "Integer");
		fieldMap.put("DECIMAL", "String");
		fieldMap.put("DATETIME", "java.util.Date");//String
		fieldMap.put("LONGBLOB", "byte[]");
		fieldMap.put("SMALLINT", "Integer");
		fieldMap.put("BIGINT UNSIGNED", "Long");
		fieldMap.put("TINYINT", "Integer");
		fieldMap.put("SMALLINT UNSIGNED", "Integer");
		fieldMap.put("TINYINT UNSIGNED", "Integer");
		fieldMap.put("CHAR", "String");
		//delFiles();
		
		javaKeywords.add("class");
	}

	static String getAuthor(){
		//随机生成作者
		/*int m = (int) (Math.random()*100);
		if(m >= 0 && m<=30) return "ligaoyuan";
		else if(m >= 30 && m<=60) return "zhuhoufei";
		else return "gaopengbd";*/
		return prop.getProperty("user.name") == null ? author : prop.getProperty("user.name");
	}
	
	/**
	 * 主方法
	 * @throws Exception
	 */
	static void doTableToBean() throws Exception{
		List<String> tables = MysqlUtils.getAllTables();
		System.out.println(String.format("总计：%s张表", tables.size()));
		for(String table : tables){
			String fileName = table.replaceFirst(table.substring(0, 1), table.substring(0, 1).toUpperCase());
			fileName = buildName(fileName);
			writeBeanFile(fileName, table);
			//if(tableToBean) System.out.println(String.format("表%s成功映射为文件%s%s%s", table, doMainPath, fileName, java_file_type));
			writeMapperFile(fileName, table);
			//if(tableToMapper) System.out.println(String.format("表%s成功映射为文件%s%s%s", table, daoPath, fileName, mapper_file_type));
			printTypeAlias(fileName);
			writeDaoFile(fileName);
			writeServiceFile(fileName);
		}
	}
	
	/**
	 * 转换例：user_login_info -> userLoinInfo
	 * @param fileName
	 * @return
	 */
	static String buildName(String fileName){
		int index = fileName.indexOf(division);
		if(index >0){
			fileName = fileName.replaceFirst(fileName.substring(index, index+2), fileName.substring(index+1, index+2).toUpperCase());
			fileName = buildName(fileName);
		}
		//TODO java关键字则特殊处理
		if(javaKeywords.contains(fileName)) {
			return "_" + fileName;
		}
		return fileName;
	}
	
	/**
	 * 生成table对应的java实体类
	 * @param fileName
	 * @param table
	 * @throws Exception
	 */
	static void writeBeanFile(String fileName, String table) throws Exception{
		if(!tableToBean){
			return ;
		}
		author = getAuthor();
		//
		//File file = new File("D:/workspace bd/maven.1409210698437/dg-common/src/main/java/com/doogua/dg/common/domain/" + fileName + java_file_type);
		File file = new File(basePath + "domain/" + fileName + java_file_type);
		createPath(file);
		FileOutputStream os = new FileOutputStream(file);
		try {
			os.write(("package " + doMainPath.substring(0, doMainPath.length()-1) +";\r\n\n").getBytes());
			os.write("import lombok.Getter;\r\n".getBytes());
			os.write("import lombok.Setter;\r\n".getBytes());
			if(!doMainPath.equalsIgnoreCase(baseDomainPath)){
				os.write(("\r\nimport " + baseDomainPath +"BaseDomain;\r\n").getBytes());
			}
			os.write(("\n/**\r\n * @" + author + " \n * " + now +  "\n*/\n").getBytes());
			os.write(("public class " + fileName + " extends BaseDomain {\n").getBytes());
			List<Field> fields = MysqlUtils.getFields(table);
			for(Field field : fields){
				os.write("\r\n\t@Setter\r\n\t@Getter\n".getBytes());
				os.write(("\tprivate " + field + ";\n").getBytes());
			}
			os.write("\n}".getBytes());
		} finally {
			if(os != null) {
				os.close();
				os = null;
			}
		}
	}
	
	/**
	 * 生成table对应的mapper文件
	 * @param fileName
	 * @param table
	 * @throws Exception
	 */
	static void writeMapperFile(String fileName, String table) throws Exception{
		if(!tableToMapper) {
			return;
		}
		author = getAuthor();
		//
		File file = new File(basePath + "mapper/" + fileName + mapper_file_type);
		//File file = new File("D:/workspace bd/maven.1409210698437/dg-driver/src/main/resources/mappers/" + fileName + mapper_file_type);
		createPath(file);
		FileOutputStream os = null;
		List<Field> fields = MysqlUtils.getFields(table);
		try {
			os = new FileOutputStream(file);
			//header
			os.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<!DOCTYPE mapper\n\tPUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n\t\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">".getBytes());
			os.write(("\n<mapper namespace=\"" + daoPath + fileName + "Mapper\">").getBytes());
			
			//resultMap
			os.write(("\n\t<resultMap id=\"" + fileName + "_RM\" type=\"" + fileName + "\">").getBytes());
			for(Field field : fields){
				os.write(("\n\t\t<id property=\"" + buildName(field.getName()) + "\" column=\"" + field.getName() +"\" />").getBytes());
			}
			os.write("\n\t</resultMap>\n".getBytes());
			
			//base columns
			os.write("\n\t<sql id=\"Base_Column_List\">".getBytes());
			os.write(("\n\t\t" + allFields(fields)).getBytes());
			os.write("\n\t</sql>\n".getBytes());
			
			//count
			os.write("\n\t<select id=\"count\" resultType=\"long\">".getBytes());
			os.write(("\n\t\tSELECT COUNT(1) FROM " + table).getBytes());
			os.write("\n\t\t<where>".getBytes());
			writeWhere(os, fields);			
			os.write("\n\t\t</where>".getBytes());
			os.write("\n\t</select>\n".getBytes());
			
			//countAll
			os.write(("\n\t<select id=\"countAll\" resultType=\"long\">\n\t\tSELECT COUNT(1) FROM " + table + "\n\t</select>\n").getBytes());
			
			//insert
			os.write(("\n\t<insert id=\"create\" parameterType=\"map\" useGeneratedKeys=\"true\" keyProperty=\"id\">").getBytes());
			os.write(("\n\t\tINSERT INTO " + table + " (<include refid=\"Base_Column_List\" />)").getBytes());
			os.write(("\n\t\tVALUES (" + insertFields(fields) + ")").getBytes());
			os.write("\n\t</insert>\n".getBytes());
			
			//del
			os.write(("\n\t<delete id=\"del\" parameterType=\"" + fileName + "\">").getBytes());
			os.write(("\n\t\tDELETE FROM " + table).getBytes());
			os.write(("\n\t\t<where>").getBytes());
			writeWhere(os, fields);	
			os.write("\n\t\t</where>".getBytes());
			os.write("\n\t</delete>\n".getBytes());
			
			//必须要主键
			String primary = primaryMap.get(table);
			if(StringUtils.isEmpty(primary)){
				//已第一个含有id的为主键
				for(Field f : fields){
					if(f.getName().contains("id")) {
						primary = f.getName();
					}
				}
			}
			
			if(StringUtils.isEmpty(primary)){
				primary = "id";
			}
			//delById
			os.write(("\n\t<delete id=\"delById\" parameterType=\"java.lang.Long\">").getBytes());
			os.write(("\n\t\tDELETE FROM " + table + " WHERE " + primary + " = #{" + buildName(primary) +"}").getBytes());
			os.write("\n\t</delete>\n".getBytes());

			//findById
			os.write(("\n\t<select id=\"findById\" resultMap=\"" + fileName + "_RM\" parameterType=\"java.lang.Long\">").getBytes());
			os.write(("\n\t\tSELECT <include refid=\"Base_Column_List\" /> \n\t\tFROM " + table + " \n\t\tWHERE " + primary + " = #{" + buildName(primary) +"}").getBytes());
			os.write("\n\t</select>\n".getBytes());
			
			//update
			os.write(("\n\t<update id=\"update\" parameterType=\"" + fileName + "\">").getBytes());
			os.write(("\n\t\tUPDATE " + table).getBytes());
			os.write(("\n\t\tSET").getBytes());
			writeUpdate(os, fields, primary);	
			os.write("\n\t</update>\n".getBytes());
			
			//find
			os.write(("\n\t<select id=\"find\" resultMap=\"" + fileName + "_RM\">").getBytes());
			os.write(("\n\t\tSELECT <include refid=\"Base_Column_List\" /> \n\t\tFROM " + table).getBytes());
			os.write(("\n\t\t<where>").getBytes());
			writeWhere(os, fields);	
			os.write("\n\t\t</where>".getBytes());
			os.write("\n\t\t<if test=\" null != start and null != limit\">".getBytes());
			os.write("\n\t\t\tLIMIT  #{start},#{limit}".getBytes());
			os.write("\n\t\t</if>".getBytes());
			os.write("\n\t</select>\n".getBytes());
			
			if(containsName(fields)){
				//findByName
				os.write(("\n\t<select id=\"findByName\" resultMap=\"" + fileName + "_RM\">").getBytes());
				os.write(("\n\t\tSELECT <include refid=\"Base_Column_List\" /> \n\t\tFROM " + table + " \n\t\tWHERE name = #{name}").getBytes());
				os.write("\n\t</select>\n".getBytes());
				
				//delByName
				os.write(("\n\t<delete id=\"delByName\" parameterType=\"java.lang.String\">").getBytes());
				os.write(("\n\t\tDELETE FROM " + table + " \n\t\tWHERE name = #{name}").getBytes());
				os.write("\n\t</delete>\n".getBytes());
			}
			
			os.write("\n</mapper>".getBytes());
		} finally {
			if(os != null) {
				os.close();
				os = null;
			}
		}
	}
	
	/**
	 * 判断是否含有name字段
	 * @param fields
	 * @return true:字段含有name
	 */
	static boolean containsName(List<Field> fields){
		for(Field f : fields){
			if("name".equals(f.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 首字母转换为大写
	 * @param str：user->User
	 * @return
	 */
	static String firstUpper(String str){
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}
	
	/**
	 * 首字母转换为小写
	 * @param str：User->user
	 * @return
	 */
	static String firstLower(String str){
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
	}
	
	/**
	 * 写入where中间的内容
	 * @param os
	 * @param fields
	 * @throws IOException
	 */
	static void writeWhere(FileOutputStream os, List<Field> fields) throws IOException{
		for(Field field : fields){
			//单独的id查询方法
			if("id".equals(field.getName())) {
				continue;
			}
			os.write(("\n\t\t\t<if test=\" null != " + buildName(field.getName()) + " \">").getBytes());
			os.write(("\n\t\t\t\tAND `" + field.getName() + "` = #{" + buildName(field.getName()) +"}").getBytes());
			os.write("\n\t\t\t</if>".getBytes());
		}
	}
	
	/**
	 * 写入update语句中set段的内容
	 * @param os
	 * @param fields
	 * @throws IOException
	 */
	static void writeUpdate(FileOutputStream os, List<Field> fields, String primary) throws IOException{
		for(Field field : fields){
			//id不存在update的情况
			if(primary.equals(field.getName())) {
				continue;
			}
			os.write(("\n\t\t\t<if test=\" null != " + buildName(field.getName()) + " \">").getBytes());
			os.write(("\n\t\t\t\t `" + field.getName() + "` = #{" + buildName(field.getName()) +"},").getBytes());
			os.write("\n\t\t\t</if>".getBytes());
		}
		os.write(("\n\t\t\t" + primary + " = #{" + buildName(primary) + "}").getBytes());
		os.write(("\n\t\t\tWHERE " + primary + " = #{" + buildName(primary) + "}").getBytes());
	}
	
	/**
	 * 构建字符串形式的字段：id,name,time...
	 * @param fields
	 * @return
	 */
	static String allFields(List<Field> fields){
		StringBuilder sb = new StringBuilder(fields.get(0).getName());
		for(int i=1; i<fields.size() ; i++){
			//防止关键字冲突
			sb.append(",`").append(fields.get(i).getName()).append("`");
		}
		return sb.toString();
	}
	
	/**
	 * 构建相应的字符串：#{id},#{name}...
	 * @param fields
	 * @return
	 */
	static String insertFields(List<Field> fields){
		StringBuilder sb = new StringBuilder("#{").append(buildName(fields.get(0).getName())).append("}");
		for(int i=1; i<fields.size() ; i++){
			sb.append(",#{").append(buildName(fields.get(i).getName())).append("}");
		}
		return sb.toString();
	}
	
	/**
	 * 若父目录不存在则创建
	 * @param file
	 */
	static void createPath(File file){
		File parent = file.getParentFile();
		if(!parent.exists()){
			parent.mkdirs();
		}
	}
	
	/**
	 * 删除当前file的目录下所有的文件
	 * @param file
	 */
	static void delFiles(File file){
		File parent = file.getParentFile();
		File[] files = parent.listFiles();
		if(files != null && files.length >0){
			for(File f: files){
				f.delete();
			}
		}
	}
	
	/**
	 * 打印typeAlias串信息
	 * @param fileName
	 */
	public static void printTypeAlias(String fileName){
		if(printAlias){
			System.out.println("<typeAlias type=\"" + doMainPath + fileName + "\" alias=\"" + fileName +"\" />");
		}
	}
	
	static void writeDaoFile(String fileName) throws Exception {
		if(!tableToMapper) {
			return;
		}
		author = getAuthor();
		File file = new File(basePath + "dao/" + fileName + "Mapper" + java_file_type);
		//File file = new File("D:/workspace bd/maven.1409210698437/dg-driver/src/main/java/com/doogua/dg/driver/dao/" + fileName + "Mapper" +  java_file_type);
		createPath(file);
		FileOutputStream os = null;
		try {
			String daoName = firstUpper(fileName);
			os = new FileOutputStream(file);
			os.write(("package " + daoPath.substring(0, daoPath.length()-1) + ";").getBytes());
			os.write("\n\nimport org.springframework.stereotype.Repository;".getBytes());
			os.write(("\n\nimport " + baseDomainPath + daoName +";\n").getBytes());
			os.write(("\n/**\r\n * @" + author + " \n * " + now +  "\n*/").getBytes());
			os.write("\n@Repository".getBytes());
			os.write(("\npublic interface " + daoName + "Mapper extends BaseMapper<" + daoName + ">{").getBytes());
			os.write("\n\n}".getBytes());
		} finally {
			if(os != null) {
				os.close();
				os = null;
			}
		}
	}
	
	static void writeServiceFile(String fileName) throws Exception {
		author = getAuthor();
		File file = new File(basePath + "service/" + fileName + "Service" + java_file_type);
		//File file = new File("D:/workspace bd/maven.1409210698437/dg-service/src/main/java/com/doogua/dg/service/" + fileName + "Service" + java_file_type);
		createPath(file);
		FileOutputStream os = null;
		String lowerName = firstLower(fileName);
		try {
			os = new FileOutputStream(file);
			//header
			os.write(("package " + servicePath.substring(0, servicePath.length()-1) + ";").getBytes());
			os.write("\n\nimport java.util.List;".getBytes());
			os.write("\n\nimport org.springframework.beans.factory.annotation.Autowired;".getBytes());
			os.write("\nimport org.springframework.stereotype.Service;".getBytes());
			os.write("\nimport org.springframework.transaction.annotation.Transactional;".getBytes());
			os.write(("\n\nimport " + doMainPath + fileName +";").getBytes());
			os.write(("\nimport " + daoPath + fileName +"Mapper;\n").getBytes());
			os.write(("\n/**\r\n * @" + author + " \n * " + now +  "\n*/").getBytes());
			os.write("\n@Service".getBytes());
			os.write(("\npublic class " + fileName + "Service{\n").getBytes());
			
			//body
			os.write("\n\t@Autowired".getBytes());
			os.write(("\n\tprivate " + fileName + "Mapper " + lowerName + "Mapper;\n").getBytes());
			//create
			os.write("\n\t@Transactional".getBytes());
			os.write(("\n\tpublic int create(" + fileName + " " + lowerName +  ") {").getBytes());
			os.write(("\n\t\treturn " + lowerName + "Mapper.create(" + lowerName + ");\n\t}").getBytes());
			//update
			os.write("\n\n\t@Transactional".getBytes());
			os.write(("\n\tpublic int update(" + fileName + " " + lowerName +  ") {").getBytes());
			os.write(("\n\t\treturn " + lowerName + "Mapper.update(" + lowerName + ");\n\t}").getBytes());
			//delById
			os.write("\n\n\t@Transactional".getBytes());
			os.write(("\n\tpublic int delById(long id){").getBytes());
			os.write(("\n\t\treturn " + lowerName + "Mapper.delById(id);\n\t}").getBytes());
			//del
			os.write("\n\n\t@Transactional".getBytes());
			os.write(("\n\tpublic int del(" + fileName + " " + lowerName +  ") {").getBytes());
			os.write(("\n\t\treturn " + lowerName + "Mapper.del(" + lowerName + ");\n\t}").getBytes());
			//find
			os.write("\n\n\t@Transactional".getBytes());
			os.write(("\n\tpublic List<" + fileName + "> find(" + fileName + " " + lowerName +  ") {").getBytes());
			os.write(("\n\t\treturn " + lowerName + "Mapper.find(" + lowerName + ");\n\t}").getBytes());
			//findById
			os.write("\n\n\t@Transactional".getBytes());
			os.write(("\n\tpublic " + fileName + " findById(long id){").getBytes());
			os.write(("\n\t\treturn " + lowerName + "Mapper.findById(id);\n\t}").getBytes());
			//countAll
			os.write("\n\n\t@Transactional".getBytes());
			os.write(("\n\tpublic long countAll(){").getBytes());
			os.write(("\n\t\treturn " + lowerName + "Mapper.countAll();\n\t}").getBytes());
			//count
			os.write("\n\n\t@Transactional".getBytes());
			os.write(("\n\tpublic long count(" + fileName + " " + lowerName + "){").getBytes());
			os.write(("\n\t\treturn " + lowerName + "Mapper.count(" + lowerName + ");\n\t}").getBytes());
			
			os.write("\n}".getBytes());
		} finally {
			if(os != null) {
				os.close();
				os = null;
			}
		}
	}
	
	/**
	 * 删除之前生成的文件
	 */
	static void delFiles(){
		if(delFiles){
			List<String> fileNameList = new ArrayList<String>();
			fileNameList.add("D:/workspace bd/maven.1409210698437/dg-common/src/main/java/com/doogua/dg/common/domain/bean.java");
			fileNameList.add("D:/workspace bd/maven.1409210698437/dg-driver/src/main/resources/mappers/mapper.xml");
			fileNameList.add("D:/workspace bd/maven.1409210698437/dg-driver/src/main/java/com/doogua/dg/driver/dao/mapper.java");
			fileNameList.add("D:/workspace bd/maven.1409210698437/dg-service/src/main/java/com/doogua/dg/service/service.java");
			for(String fName : fileNameList){
				File f = new File(fName);
				delFiles(f);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		doTableToBean();
		//System.out.println("1sadf.asdfa.adf.".substring(0, "1sadf.asdfa.adf.".length()-1));
	}
}

@Data class Field {

	private String name;
	
	private String type;
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		
		if(name.startsWith("is_")){
			return "Boolean " + CodeUtils.buildName(name);
		}
		
		if(CodeUtils.fieldMap.get(type) == null || "".equals(CodeUtils.fieldMap.get(type))) {
			System.err.println(type + " can not be relfected！");
			return "String " + CodeUtils.buildName(name);
		}
		
		return CodeUtils.fieldMap.get(type) + " " + CodeUtils.buildName(name);
	}
}

class MysqlUtils {
	
	/*public static void main(String[] args) throws Exception {
		printAllTables();
		List<String> tables = getAllTables();
		for(String table : tables){
			System.out.println(table);
			System.out.print(getAllFields(table));
			System.out.println();
		}
		
	}*/
	
	/**
	 * 取table的全部filed，返回field的name
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getAllFields(String tabName) throws SQLException{
		List<String> fieldList = new ArrayList<String>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Class.forName(CodeUtils.jdbcDriver);
			connection = DriverManager.getConnection(CodeUtils.jdbcUrl, CodeUtils.userName, CodeUtils.password);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from " + tabName + " limit 0,1");
			ResultSetMetaData rsmd = resultSet.getMetaData(); 
			for(int i=1; i<=rsmd.getColumnCount(); i++){
				fieldList.add(rsmd.getColumnName(i).trim());
			}
			
			/*while(resultSet.next()){
				resultSet.getString(1);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) connection.close();
			if(statement != null) statement.close();
			if(resultSet != null) resultSet.close();
		}
		return fieldList;
	}
	
	/**
	 * 获取table的全部filed，返回field的name+type
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	public static List<Field> getFields(String tabName) throws SQLException{
		List<Field> fieldList = new ArrayList<Field>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Class.forName(CodeUtils.jdbcDriver);
			connection = DriverManager.getConnection(CodeUtils.jdbcUrl, CodeUtils.userName, CodeUtils.password);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from " + tabName + " limit 0,1");
			ResultSetMetaData rsmd = resultSet.getMetaData(); 
			//主键
			getPrimary(connection, tabName);
			Field field = null;
			for(int i=1; i<=rsmd.getColumnCount(); i++){
				field = new Field();
				field.setName(rsmd.getColumnName(i).trim());
				field.setType(rsmd.getColumnTypeName(i));
				fieldList.add(field);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) connection.close();
			if(statement != null) statement.close();
			if(resultSet != null) resultSet.close();
		}
		return fieldList;
	}
	
	public static String getPrimary(Connection connection, String tabName) throws SQLException{
		String primary = null;
		ResultSet rs = connection.getMetaData().getColumns(connection.getCatalog(), "%", tabName, "%ID");
		if(rs.next()){  
			primary =  rs.getString("COLUMN_NAME");
            CodeUtils.primaryMap.put(tabName, primary);
        }
		return primary;
	}
	
	/**
	 * 去库的全部table
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getAllTables() throws SQLException{
		List<String> tabList = new ArrayList<String>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			Class.forName(CodeUtils.jdbcDriver);
			connection = DriverManager.getConnection(CodeUtils.jdbcUrl, CodeUtils.userName, CodeUtils.password);
			statement = connection.createStatement();
			//resultSet = statement.executeQuery("SELECT * FROM information_schema.tables  t WHERE t.TABLE_SCHEMA LIKE 'dg_wx%'");
			resultSet = statement.executeQuery("SELECT * FROM information_schema.tables  t WHERE t.TABLE_SCHEMA = '" + CodeUtils.DB + "'");
			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
			    while (resultSet.next()) {
			        tabList.add(resultSet.getObject(3).toString().trim());
			    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) connection.close();
			if(statement != null) statement.close();
			if(resultSet != null) resultSet.close();
		}
		return tabList;
	}
	
	/**
	 * 打印库的全部table信息
	 * @throws SQLException
	 */
	static void printAllTables() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			Class.forName(CodeUtils.jdbcDriver);
			connection = DriverManager.getConnection(CodeUtils.jdbcUrl, CodeUtils.userName, CodeUtils.password);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM information_schema.tables  t WHERE t.TABLE_SCHEMA LIKE '" + CodeUtils.DB + "%'");
			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
			    //System.out.printf("%-12s\t", rsMetaData.getColumnName(i));
			    //System.out.println();
			    // Iterate through the result and print the student names
			    while (resultSet.next()) {
			      for (int j = 1; j <= rsMetaData.getColumnCount(); j++)
			        System.out.printf("%-12s\t", resultSet.getObject(j));
			      	System.out.println();
			    }
			    // Close the connection
			    connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) connection.close();
			if(statement != null) statement.close();
			if(resultSet != null) resultSet.close();
		}
	}
	
	/**
	 * 生成table name为主键，value为全部field name的Map
	 * @return
	 * @throws SQLException
	 */
	static Map<String, List<String>> getTabColMap() throws SQLException{
		Map<String, List<String>> tabColMap = new HashMap<String, List<String>>();
		List<String> list = getAllTables();
		for(String tab : list){
			tabColMap.put(tab, getAllFields(tab));
		}
		return tabColMap;
	}
}
