/**
@description  公司信息保存结构体
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
**/


package com.wmost.spider.model;


public class company {
    private String key;	
    private String name;		
    private String location;	
    private String identity;
    
    @Override
    public String toString() {
        return "["	
				+	"name=" 				+ name
				+ 	", location="			+ location
				+ 	", identity=" 			+ identity 
				+ 	", school=" 			+ identity 
				+ "]";
    }  
    
    
    
    
    
    
    
    
    /*该部分后续不支持*/
    public company(){}
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
