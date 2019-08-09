package com.steam.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 * @author ASUS
 *
 */
public class Utils {
	
	public static String DateConvert(String source) throws ParseException{
		if(source.contains("/")){
			//需要转换
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date templeDate = sdf1.parse(source);
			source = sdf2.format(templeDate);
		}
		return source;
	}
	
}
