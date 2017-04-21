/**
@description  日志格式及字段编码(爬虫引擎 ----> 数据引擎)
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
**/


package com.wmost.cfig;

public class LOG {
	//该部分与com.wmost.spider.peline.readme.md需保持一致
	public final static String version		= "0.0.1";
	public final static String log_type 	= "log_type";
	public final static String search_key 	= "search_key";
	public final static String time_ms 		= "time_ms";
	public final static String error_code 	= "error_code";
	/*******************日志的BODY部分**********************/
	public class user {
		//该部分与com.wmost.spider.model.user需保持一致
		public final static String key				="key";	
		public final static String name				="name";	
		public final static String sex				="sex";	
		public final static String ethnic			="ethnic";	
		public final static String location			="location";	
		public final static String identity			="identity";	
		public final static String school			="school";	
		public final static String major			="major";	
		public final static String work_experience	="work_experience";	
		public final static String hope_position	="hope_position";	
		public final static String hope_palce		="hope_palce";	
		public final static String hope_salary		="hope_salary";	
	    public final static String work_type		="work_type";		
	}
	public class job {
		//该部分与com.wmost.spider.model.job需保持一致
		public final static String key				="key";	
		public final static String name				="name";		
		public final static String location			="location";		
		public final static String school			="school";	
		public final static String major			="major";	
		public final static String work_experience	="work_experience";		
		public final static String palce			="palce";	
		public final static String salary			="salary";	
		public final static String work_type		="work_type";
	}
	public class company {
		//该部分与com.wmost.spider.model.company需保持一致
		public final static String key				="key";	
		public final static String name				="name";		
		public final static String location			="location";	
		public final static String identity			="identity";
	}
	public final static String server_ip 	= "server_ip";
}
