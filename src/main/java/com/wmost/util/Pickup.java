package com.wmost.util;

import java.util.regex.Pattern;

public class Pickup {
	public static String getTime(String msg){
		if(null!=msg){
			Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}[:][0-9]{1,2}"); 
			if(pattern.matcher(msg) != null){
				try{
					if(pattern.matcher(msg).find()){
						return pattern.matcher(msg).group();
					}else{
						return "";
					}
				}catch(IllegalStateException e){
					//e.printStackTrace();
					return "";
				}
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
}
