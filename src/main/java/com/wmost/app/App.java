/**
@description  应用中心
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
		2017-04-21	21:01:00	完善代码架构,按照设计稿进行了模块的划分,日志输出正常
		2017-04-21	21:51:00	对JSON与对象互转方法进行了升级,采用util中方法,并增强了调试日志输出

**/


package com.wmost.app;

import com.wmost.spider.spider;


public class App {
	public static void main(String[] args) {
		long startTime, endTime;
        startTime =System.currentTimeMillis();
        
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
