/**
@description  抽取数据调试显示模块(开发调试模式时,单机直接输出信息至控制台)
@author hanse/irene
@data	2017-04-08	00:00	初稿
		2017-04-21	00:00	整理代码
		2017-04-21	21:01	完善代码架构,按照设计稿进行了模块的划分,日志输出正常


**/


package com.wmost.spider.peline;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.wmost.spider.model.company;
import com.wmost.spider.model.position;
import com.wmost.spider.model.candidate;
import com.wmost.util.Map2Obj;
import com.wmost.util.SafeString;
import com.wmost.util.timeUtil;
import com.wmost.cfig.LOG;
import com.wmost.cfig.UNICODE;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class stdiopeline implements Pipeline {
	private static boolean IS_DEBUG = false;
	private static Logger logger  =  Logger.getLogger(logpeline.class);

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		if (null == resultItems.get(LOG.log_type)){
			logger.warn(resultItems.toString()+" log_type is null!");
			return;
		}
		
		if (IS_DEBUG) {
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
	            System.out.println(entry.getKey() + ":\t" + entry.getValue());
			}
		}
		
		//Map转JSON
		Map<String, Object> map = resultItems.getAll();
		JSONObject json = JSONObject.fromObject(map); 
		if (IS_DEBUG) {
			System.out.println("收到json为:"+json.toString());
		}
		
		//JSON转类
		Object o = null;
		String log_type = map.get(LOG.log_type).toString();
		switch (log_type){
			case LOG.LOG_TYPE.LOG_TYPE_CANDIDATE+"":
				o=(candidate)Map2Obj.mapToObject(json,candidate.class);
				break;
			case LOG.LOG_TYPE.LOG_TYPE_COMPANY+"":
				o=(company)Map2Obj.mapToObject(map, company.class);
				break;
			case LOG.LOG_TYPE.LOG_TYPE_POSITION+"":
				o=(position)Map2Obj.mapToObject(json,position.class);
				break;
			default:
				o=new String("unrecognition log_type!");
				break;
		}
		if (IS_DEBUG) {
			System.out.println("序列化对象后:"+o.toString());
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
		
		//time_stamp
		String time = timeUtil.getTime();
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
				UNICODE.JOIN			, 
				time					,
				log_type				,
				search_key				,
				time_ms					,
				error_code,
				SafeString.to(body)		,
				server_ip
				);		
	}
}
