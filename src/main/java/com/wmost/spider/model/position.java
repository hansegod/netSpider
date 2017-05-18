/**
@description  工作信息保存结构体
@author hanse/irene
@data	2017-04-08	00:00	初稿
		2017-04-21	00:00	整理代码
		2017-04-21	22:01	按照日志格式要求精简了日志输出toString
		
		
**/


package com.wmost.spider.model;

import com.wmost.cfig.UNICODE;

public class position {
	public String key				;//标识
	public String src				;//来源
	public String name				;//职位
	public String company			;//公司
	public String industry			;//行业
	public String scale				;//规模
	public String nature			;//性质
	public String website			;//网址
	public String count				;//数量
	public String type				;//类型
	public String pubtime			;//发布
	public String offtime			;//截止
	public String salary			;//薪资
	public String location			;//地址
	public String major				;//学历
	public String school			;//学校
	public String experience		;//经验
	public String tag				;//标签
	public String duty				;//职责
	
	
	
	public position(){}
	
	@Override
	public String toString() {
		return String.join(
				UNICODE.JOIN		, 
				getKey()			,
				src					,
				name				,
				company				,
				industry			,
				scale				,
				nature				,
				website				,
				count				,
				type				,
				pubtime				,
				offtime				,
				salary				,
				location			,
				major				,
				school				,
				experience			,
				tag					,
				duty				
				);
	}
	
	public String getKey(){
		//生成标识职位的唯一标识,后续可采用主键字段组合MD5
		key = this.hashCode()+"";
		
		return key;
	}
}
