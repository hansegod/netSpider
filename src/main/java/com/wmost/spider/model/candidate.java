/**
@description  个人信息保存结构体
@author hanse/irene
@data	2017-04-08	00:00	初稿
		2017-04-21	00:00	整理代码
		2017-04-21	22:01	按照日志格式要求精简了日志输出toString
		
		
**/


package com.wmost.spider.model;

import com.wmost.cfig.UNICODE;

public class candidate {
	public String key					;//标识
	public String src					;//来源
	public String name					;//名称
	public String gender				;//性别
	public String ethnic				;//民族
	public String major					;//学历
	public String school				;//学校
	public String capacity				;//能力
	public String experience			;//经验
	public String industry				;//行业
	public String scale					;//规模
	public String nature				;//性质
	public String position				;//职位
	public String type					;//类型
	public String salary				;//薪资
	public String location				;//地址
	public String expect_industry		;//行业*
	public String expect_scale			;//规模*
	public String expect_nature			;//性质*
	public String expect_position		;//职位*
	public String expect_type			;//类型*
	public String expect_salary			;//薪资*
	public String expect_location		;//地址*
	public String tag					;//标签
	
	public candidate(){}
	
	@Override
	public String toString() {
		return String.join(
				UNICODE.JOIN		,
				getKey()			,
				src					,
				name				,
				gender				,
				ethnic				,
				major				,
				school				,
				capacity			,
				experience			,
				industry			,
				scale				,
				nature				,
				position			,
				type				,
				salary				,
				location			,
				expect_industry		,
				expect_scale		,
				expect_nature		,
				expect_position		,
				expect_type			,
				expect_salary		,
				expect_location		,
				tag					
    			);
    }
    
    public String getKey(){
    	//生成标识应聘者的唯一标识,后续可采用主键字段组合MD5
    	key = this.hashCode()+"";
    	
    	return key;
    }
}