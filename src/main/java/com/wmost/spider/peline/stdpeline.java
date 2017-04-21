/**
@description  抽取数据调试显示模块
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
		2017-04-21	21:01:00	完善代码架构,按照设计稿进行了模块的划分,日志输出正常
**/


package com.wmost.spider.peline;

import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wmost.spider.model.company;
import com.wmost.spider.model.job;
import com.wmost.spider.model.user;
import com.wmost.cfig.LOG;
import com.wmost.cfig.LOG_TYPE;
import com.wmost.cfig.UNICODE;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class stdpeline implements Pipeline {
	@Override
	public void process(ResultItems resultItems, Task task) {
		//Map转JSON
		Map<String, Object> map = resultItems.getAll();
		JSONObject json = JSONObject.fromObject(map); 
		
		//JSON转类
		Object o=null;
		if (null==map.get(LOG.log_type)){
			System.out.println(resultItems.toString()+" log_type is null!");
			return;
		}
		String log_type = map.get(LOG.log_type).toString();
		switch (log_type){
			case LOG_TYPE.LOG_TYPE_USER+"":
				o=(user)JSONObject.toBean(json,user.class);
				break;
			case LOG_TYPE.LOG_TYPE_COMPANY+"":
				o=(company)JSONObject.toBean(json,company.class);
				break;
			case LOG_TYPE.LOG_TYPE_JOB+"":
				o=(job)JSONObject.toBean(json,job.class);
				break;
			default:
				o=new String("unrecognition log_type!");
				break;
		}
		
		//日志输出
		String s = logPrintln(resultItems,o);
		if (null != s) {
			System.out.println(s);
		}
	}
	
	//格式化生成日志(非法时返回null)
	public String logPrintln(ResultItems resultItems,Object o){
		if (null == resultItems || null == o) {
			return null;
		}
		
		//time_stamp(日志器生成)
		
		//log_type
		String log_type = resultItems.get(LOG.log_type);
		//search_key
		String search_key = UUID.randomUUID().toString();
		//time_ms
		String time_ms = resultItems.get(LOG.time_ms);
		//error_code
		String error_code = resultItems.get(LOG.error_code);
		//日志body
		String body = o.toString();
		//server_ip
		String server_ip = resultItems.get(LOG.server_ip);
		
		return String.join(
				UNICODE.JOIN, 
				log_type,
				search_key,
				time_ms,
				error_code,
				body,
				server_ip
				);		
	}
}
