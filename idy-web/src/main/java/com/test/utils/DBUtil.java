package com.test.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.test.exception.resolver.StackTrace;

public class DBUtil {
	
	static String driver = "com.mysql.jdbc.Driver";
	
	static String url = "jdbc:mysql://10.255.209.155:8306/dg_xiaoshuo";
	
	static String userName = "root";
	
	static String password = "root";
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DBUtil.class);

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}
	
	public static Connection getConnection(String url, String userName, String password) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}
	
	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if(conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				logger.error(StackTrace.getExceptionTrace(e));;
			}
		}
		if(ps != null) {
			try {
				ps.close();
				ps = null;
			} catch (SQLException e) {
				logger.error(StackTrace.getExceptionTrace(e));;
			}
		}
		if(rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				logger.error(StackTrace.getExceptionTrace(e));;
			}
		}
	}
	
	public static void close(PreparedStatement ps, ResultSet rs) {
		if(ps != null) {
			try {
				ps.close();
				ps = null;
			} catch (SQLException e) {
				logger.error(StackTrace.getExceptionTrace(e));;
			}
		}
		if(rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				logger.error(StackTrace.getExceptionTrace(e));;
			}
		}
	}
}
