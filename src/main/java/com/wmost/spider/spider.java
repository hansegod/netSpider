/**
@description  爬虫引擎
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
		2017-04-21	21:51:00	对JSON与对象互转方法进行了升级,采用util中方法,并增强了调试日志输出

**/


package com.wmost.spider;

import us.codecraft.webmagic.Spider;

import com.wmost.spider.peline.logpeline;
import com.wmost.spider.processor.dianhouProcessor;
import com.wmost.spider.processor.lyrcProcessor;

public class spider {
	private static boolean IS_DEBUG = false;
	
	public static void submit(String[] args) {
		long startTime, endTime;
        startTime =System.currentTimeMillis();
        
        /*********************************************/
        //System.out.println("【www.dianhou.com-爬取演示】");
		Spider
		.create(new dianhouProcessor())
		.addUrl("http://www.dianhou.com/company?p=company&page=1")
		.addPipeline(new logpeline())
		.thread(5)
		.start();
		
		/*********************************************/
		//System.out.println("【rc.lyrc.net-爬取演示】");
		Spider
		.create(new lyrcProcessor())
		.addUrl("http://rc.lyrc.net/Companyzp.aspx?Page=1")
		.addPipeline(new logpeline())
		.thread(5)
		.start();
		
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫引擎】启动成功,耗时(ms):" + (endTime - startTime));
	}
}
