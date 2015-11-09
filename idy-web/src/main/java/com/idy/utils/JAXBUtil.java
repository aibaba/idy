package com.idy.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;

/**
 * xml转换的工具类
 * @author gaopengbd
 *
 */
public class JAXBUtil {

	/**
	 * 转换对象为xml
	 * @param obj
	 * @return
	 * @throws JAXBException
	 */
	public static <T> String objToXml(T obj) throws JAXBException {
		if(obj == null) return null;
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
        StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString().replace("standalone=\"yes\"?>", "?>");
	}
	
	public static <T> String objToXml(T obj, String encoding, String header) throws JAXBException {
		if(obj == null) return null;
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, header);//编码格式 
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
        StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}
	
	public static <T> String objToXml(T obj, String encoding, String header, boolean format) throws JAXBException {
		if(obj == null) return null;
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, header);//编码格式 
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
        StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}
	
	public static <T> String objToXml(T obj, String encoding, String header, boolean format, boolean fragment) throws JAXBException {
		if(obj == null) return null;
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, header);//编码格式 
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragment);// 是否省略xm头声明信息
        StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}
	
	public static <T> T xmlToObj(String xml, Class<T> clazz) throws JAXBException {
		if(StringUtils.isEmpty(xml)){
			return null;
		}
		JAXBContext context = JAXBContext.newInstance(clazz);  
        Unmarshaller unmarshaller = context.createUnmarshaller();  
        @SuppressWarnings("unchecked")
		T obj = (T)unmarshaller.unmarshal(new StringReader(xml));
		
		return obj;
	}
}
