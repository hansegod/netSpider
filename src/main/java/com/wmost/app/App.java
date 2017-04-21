/**
@description  应用中心
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
		2017-04-21	21:01:00	完善代码架构,按照设计稿进行了模块的划分,日志输出正常
		2017-04-21	21:51:00	对JSON与对象互转方法进行了升级,采用util中方法,并增强了调试日志输出
		2017-04-21	22:21:00	升级获取外网IP方法
		
**/


package com.wmost.app;

import com.wmost.cfig.cfig;
import com.wmost.reducer.reducer;
import com.wmost.spider.spider;
import com.wmost.weber.weber;


public class App {
	private static String VERSION = "0.1.2";
	
	public static void main(String[] args) {
		long startTime, endTime;
        startTime =System.currentTimeMillis();
        
        System.out.println("======================"+"netSpider"+"========================");
        System.out.println("========================"+VERSION+"==========================");
        
        cfig.submit(args);
        weber.submit(args);
        reducer.submit(args);
        spider.submit(args);
		
        endTime = System.currentTimeMillis();
        System.out.println("【应用中心】启动完成,耗时(ms):" + (endTime - startTime));
	
        while (true) {
        	try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
}
