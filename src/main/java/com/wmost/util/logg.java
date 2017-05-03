package com.wmost.util;

import java.text.SimpleDateFormat;
import java.util.Date;


//@description	日志统一接口
//@author chenbintao
//@data		2016-09-02 20:29		初稿
//			2016-09-20 17:01		增加日志方法


public class logg {
	public static final String 	LOG_TIME_FORMAT	= "yyyy/MM/dd HH:mm:ss";

	public static void Println(String msg){
		StackTraceElement stack[] = (new Throwable()).getStackTrace(); 
		System.out.println(
					getTime() 
				+ 	" " 
				+ 	stack[1].getClassName()
				+ 	":" 
				+	stack[1].getLineNumber()
				+ 	"[" 
				+ 	msg
				+	"]"
			);
	}
	
	public static void Error(String err){
		StackTraceElement stack[] = (new Throwable()).getStackTrace(); 
		System.out.println(
					getTime() 
				+ 	" ERROR " 
				+ 	stack[1].getClassName()
				+ 	":" 
				+	stack[1].getLineNumber()
				+ 	"[" 
				+ 	err
				+	"]"
			);	
	}
	
	public static void getCaller()    
	{
		StackTraceElement stack[] = (new Throwable()).getStackTrace();    
		for (int i = 0; i < stack.length; i++)   
		{
			StackTraceElement s = stack[i];
			System.out.format("Class:%d\t%s\n", i, s.getClass());//类类型  
			System.out.format("ClassName:%d\t%s\n", i, s.getClassName());//类名  
			System.out.format("MethodName:%d\t%s\n", i, s.getMethodName());//方法名  
			System.out.format("FileName:%d\t%s\n", i, s.getFileName());//文件名  
			System.out.format("LineNumber:%d\t%s\n", i, s.getLineNumber());//行数  
			System.out.println("-------------------------------------------");//行数  
		}  
	}
	
	//获取当前时间
	public final static String getTime(){
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(LOG_TIME_FORMAT);
        return sdf.format(d);
	}
}
