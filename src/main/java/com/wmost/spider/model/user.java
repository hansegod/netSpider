/**
@description  个人信息保存结构体
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
**/


package com.wmost.spider.model;

import net.sf.json.JSONObject;
import us.codecraft.webmagic.ResultItems;

public class user {
    private String key;	
    private String name;	
    private String sex;	
    private String ethnic;	
    private String location;	
    private String identity;	
    private String school;	
    private String major;	
    private String work_experience;	
    private String hope_position;	
    private String hope_palce;	
    private String hope_salary;	
    private String work_type;
    
    @Override
    public String toString() {
        return "["	
				+	"name=" 				+ name
				+ 	", sex=" 				+ sex 
				+ 	", minzu=" 				+ ethnic 
				+ 	", location="			+ location
				+ 	", identity=" 			+ identity 
				+ 	", school=" 			+ school 
				+ 	", major=" 				+ major 
				+ 	", work_experience=" 	+ work_experience
				+ 	", hope_position=" 		+ hope_position  
				+ 	", hope_palce=" 		+ hope_palce 
				+ 	", hope_salary=" 		+ hope_salary
				+ 	", work_type=" 			+ work_type 
				+ "]";
    }
    
    
    
    
    
    
    
    
    
    /*该部分后续不支持*/
    public user(){}
    public String getMinzu() {
        return ethnic;
    }
    public void setMinzu(String ethnic) {
        this.ethnic = ethnic;
    }
    public String getWork_experience() {
        return work_experience;
    }
    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }
    public String getHope_position() {
        return hope_position;
    }
    public void setHope_position(String hope_position) {
        this.hope_position = hope_position;
    }
    public String getHope_palce() {
        return hope_palce;
    }
    public void setHope_palce(String hope_palce) {
        this.hope_palce = hope_palce;
    }
    public String getHope_salary() {
        return hope_salary;
    }
    public void setHope_salary(String hope_salary) {
        this.hope_salary = hope_salary;
    }
    public String getWork_type() {
        return work_type;
    }
    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }
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
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
}