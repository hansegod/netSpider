/**
@description  工作信息保存结构体
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
		2017-04-21	22:01:00	按照日志格式要求精简了日志输出toString
**/


package com.wmost.spider.model;

import com.wmost.cfig.UNICODE;

public class job {
	public String key;
	public String name;
	public String location;
	public String school;
	public String major;
	public String work_experience;
	public String palce;
	public String salary;
	public String work_type;
    
    public job(){}
    
    @Override
    public String toString() {
    	return String.join(
    			UNICODE.JOIN, 
    			getKey(),
    			name,
    			location,
    			school,
    			major,
    			work_experience,
    			palce,
    			salary,
    			work_type
    			);
    }
    
    public String getKey(){
    	key = this.hashCode()+"";
    	
    	return key;
    }
}
