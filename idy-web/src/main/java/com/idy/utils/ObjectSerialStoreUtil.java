package com.idy.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import com.mysql.jdbc.Blob;

import lombok.Data;

/**
 * 保存序列化的对象的数据库
 *@Description: TODO
 *@author penggao15@creditease.cn
 *@date 2016年1月29日 下午5:45:02 
 *@version V1.0
 */
public class ObjectSerialStoreUtil {

	public static void saveObjectToDB(){
		try{
			SerObj e1 = new SerObj();
			e1.setC01("1abv");
			e1.setC02(2222);
			e1.setC03(new Date());
			e1.setC04(BigDecimal.valueOf(10.22));
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/idy", "root", "243221");
			System.out.println("AutoCommit=" + conn.getAutoCommit());
			conn.setAutoCommit(false);
			System.out.println("Now AutoCommit=" + conn.getAutoCommit());
			PreparedStatement ps = conn.prepareStatement("insert into ser_obj(ser_value, blob_val, byte_val) values(?, ?, ?)");
			//ps.setObject(1, "aaa");
			//ps.setBytes(1, serializeObject(e1));
			//ps.setObject(1, new String(serializeObject(e1)));
			ps.setString(1, new String(serializeObject(e1)));
			ps.setBytes(2, serializeObject(e1));
			ps.setObject(3, serializeObject(e1));
			int res = ps.executeUpdate();
			System.out.println("update res = " + res);
			conn.commit();
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from ser_obj");
			while(rs.next()){
				//System.out.println(rs.getString("ser_value"));
				Blob blob = (Blob) rs.getBlob("blob_val");
				if(blob == null) continue;
				BufferedInputStream bis = new BufferedInputStream(blob.getBinaryStream());
				byte[] buff = new byte[(int) blob.length()];
				while(-1 != (bis.read(buff, 0, buff.length))){
					ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buff));
					SerObj obj = (SerObj) in.readObject();
					System.out.println(obj.getC01() + "-------" + obj.getC04());
				}
				//System.out.println(blob.getBinaryStream());
				//System.out.println();
				byte[] bs = rs.getBytes("byte_val");
				if(bs == null || bs.length < 1) continue;
				ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bs));
				SerObj obj = (SerObj) in.readObject();
				System.out.println(obj.getC02() + "****************" + obj.getC03());
			}
			rs.close();
			stat.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static byte[] serializeObject(Object object) throws IOException {
		ByteArrayOutputStream saos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(saos);
		oos.writeObject(object);
		oos.flush();
		return saos.toByteArray();
	}

	
	public static void main(String[] args) {
		saveObjectToDB();
	}
}

@Data class SerObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String c01;
	
	private Integer c02;
	
	private Date c03;
	
	private BigDecimal c04;
}
