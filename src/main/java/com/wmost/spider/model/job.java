/**
@description  工作信息保存结构体
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
**/


package com.wmost.spider.model;

public class job {
    private String key;	
    private String name;		
    private String location;		
    private String school;	
    private String major;	
    private String work_experience;		
    private String palce;	
    private String salary;	
    private String work_type;
    
    @Override
    public String toString() {
        return "["	
				+	"name=" 				+ name
				+ 	", location="			+ location
				+ 	", school=" 			+ school 
				+ 	", major=" 				+ major 
				+ 	", work_experience=" 	+ work_experience
				+ 	", hope_palce=" 		+ palce 
				+ 	", hope_salary=" 		+ salary
				+ 	", work_type=" 			+ work_type 
				+ "]";
    }
    
    
    
    
    
    
    /*该部分后续不支持*/
    public String getWork_experience() {
        return work_experience;
    }
    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }
    public String getPalce() {
        return palce;
    }
    public void setPalce(String palce) {
        this.palce = palce;
    }
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
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
