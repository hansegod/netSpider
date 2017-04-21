/**
@description  服务引擎
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
**/


package com.wmost.weber;


public class weber {
	public static void submit(String[] args) {
		long startTime, endTime;
        startTime =System.currentTimeMillis();
        
        
		
        endTime = System.currentTimeMillis();
        System.out.println("【服务引擎】启动完成,耗时(ms):" + (endTime - startTime));
	}
}
