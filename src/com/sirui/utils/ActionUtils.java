package com.sirui.utils;

import java.util.HashMap;
import java.util.Map;




public class ActionUtils {
	private static Map<String,String> map = new HashMap<String, String>();
	static {
		map.put("NUMBER", "Long ");
		map.put("RAW", "byte[] ");
		map.put("BLOB", "byte[] ");
		map.put("CHAR", "String ");
		map.put("DATE", "Date ");
		map.put("FLOAT", "BigDecimal ");
		map.put("VARCHAR2", "String ");
		map.put("NUMBER", "Integer ");
		map.put("BLOB RAW", "byte[] ");
		map.put("CLOB RAW", "String ");
		map.put("", "String ");
		map.put(null, "String ");
		
	}
	
	/**
	 * 表结构转换java bean
	 * @param str
	 * @return
	 */
	public static String tableBean(String str){
		String [] lines = str.split("\n");
		String priv = "private ";
		StringBuffer sb = new StringBuffer();
		for(String line : lines){
			String [] fileds = line.split("\t");
			String name = fileds[0].toLowerCase();
			if(fileds.length == 1){
				sb.append(priv);
				sb.append("String ");
				sb.append(getName(name));
			} else {
				String type = fileds[1];
				int typeLength = type.indexOf("(");
				String typeName = type.substring(0,typeLength == -1 ? type.length() : typeLength);
				StringBuffer s = getName(name);
				type = map.get(typeName.toUpperCase());
				sb.append(priv);
				sb.append(type == null ? "String ":type );
				sb.append(s.toString());
			}
			sb.append(";");
			sb.append("\n");
		}
		return sb.toString();
	}

	private static StringBuffer getName(String name) {
		
		char [] cs = name.toCharArray();
		StringBuffer s = new StringBuffer();
		
		for(int i=0; i<cs.length;i++){
			if(cs[i] == '_'){
				s.append((cs[++i]+"").toUpperCase());
			} else {
				s.append(cs[i]);
			}
		}
		
		return s;
	}
	
	/**
	 * java bean 转换Grid返回串
	 * @param str
	 * @return
	 */
	public static String beanGrid(String str){
		String [] lines = str.split("\n");
		StringBuffer sb = new StringBuffer();
		for(String line : lines){
			int index = line.lastIndexOf(" ");
			if(index == -1)
				continue;
			line = line.substring(index);
			line = line.replace(';', ',');
			sb.append(line);
		}
		str = sb.substring(0, sb.length() -1);
		return str;
	}
	
	/**
	 * java bean 转换Ext Model对象
	 * @param str
	 * @return
	 */
	public static String beanExtModel(String str){
		String [] lines = str.split("\n");
		StringBuffer sb = new StringBuffer();
		String st = "{name: '";
		for(String line : lines){
			int index = line.lastIndexOf(" ");
			int last = line.indexOf(";");
			if(index == -1 || last == -1)
				continue;
			line = line.substring(index,last);
			sb.append(st);
			sb.append(line);
			sb.append("'},");
			sb.append("\n");
		}
		str=  sb.substring(0,sb.length() -1);
		return str;
	}
	
	/**
	 * 表结构转换Ext Model 对象
	 * @param str
	 * @return
	 */
	public static String tableExtModel(String str){
		String [] lines = str.split("\n");
		StringBuffer sb = new StringBuffer();
		String st = "{name: '";
		for(String line : lines){
			sb.append(st);
			sb.append(getName(line.toLowerCase()));
			sb.append("'},");
			sb.append("\n");
		}
		str=  sb.substring(0,sb.length() -1);
		return str;
	}
	
	/**
	 * 表结构 转换 Gird 串
	 * @param str
	 * @return
	 */
	public static String tableGrid(String str){
		String [] lines = str.split("\n");
		StringBuffer sb = new StringBuffer();
		for(String line : lines){
			sb.append(getName(line.toLowerCase()));
			sb.append(",");
		}
		str=  sb.substring(0,sb.length() -1);
		return str;
	}
	
	/**
	 * 自定义sql转换grid
	 * @param str
	 * @return
	 */
	public static String sqlGrid(String str){
		String [] lines = str.split(",");
		StringBuffer sb = new StringBuffer();
		for(String line : lines){
			sb.append(getName(line.toLowerCase()));
			sb.append(",");
		}
		str=  sb.substring(0,sb.length() -1);
		return str;
	}
}
