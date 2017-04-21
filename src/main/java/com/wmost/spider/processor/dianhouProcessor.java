/**
@description  dianhou网爬取器
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
		2017-04-21	21:01:00	完善代码架构,按照设计稿进行了模块的划分,日志输出正常
		2017-04-21	21:31:00	整理规范调试信息打印
		2017-04-21	21:41:00	修正增加爬取链接逻辑
		2017-04-21	22:21:00	升级获取外网IP方法
		
		
**/


package com.wmost.spider.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wmost.cfig.LOG;
import com.wmost.cfig.LOG_TYPE;
import com.wmost.util.deviceIfo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class dianhouProcessor implements PageProcessor {
	private static boolean IS_DEBUG = false;
    public static final String URL_TARGET = "http://www\\.dianhou\\.com/company\\?p=company\\&page=[1-9]{1,3}";
    public static final String URL_MORE = "/p=company\\&page=[1-9]{1,3}\\.html";
    public static final String server_ip = deviceIfo.getV4IP();
    
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site page = Site.me().setRetryTimes(3).setSleepTime(1000);
	public Site getSite() {
		
		return page;
	}

	public void process(Page page) {
		long startTime, endTime;
        startTime =System.currentTimeMillis();
        
        // 部分二：定义如何抽取页面信息，并保存下来
        if(page.getUrl().regex(URL_TARGET).match()){
			String name = page.getHtml().xpath("//div[1]/div[5]//div[2]//ul/li/a/@title").toString();
			String location = page.getHtml().xpath("//div[1]/div[5]//div[2]//ul/li/a/@src").toString();
			endTime = System.currentTimeMillis();
			page.putField(LOG.log_type, LOG_TYPE.LOG_TYPE_COMPANY+"");
			page.putField(LOG.time_ms, (endTime-startTime)+"");
			page.putField(LOG.error_code, page.getStatusCode()+"");
			page.putField(LOG.company.name, name);
			page.putField(LOG.company.location, location);
			page.putField(LOG.company.identity, location);
			page.putField(LOG.server_ip, server_ip);
        
            if (IS_DEBUG) {
            	System.out.println(page.getRequest().getUrl()+"抽取的页面内容:");
            	for (Map.Entry<String, Object> entry : page.getResultItems().getAll().entrySet()) {
    	            System.out.println(entry.getKey() + ":\t" + entry.getValue());
            	}
            }
		}
        
        // 部分三：从页面发现后续的url地址来抓取
		List<String> urls = new ArrayList<String>();
		urls.addAll(page.getHtml().links().regex(URL_TARGET).all());
		urls.addAll(page.getHtml().links().regex(URL_MORE).all());
		if (urls.size()<=0) {
			return;
		}
        page.addTargetRequests(urls);
        
        if (IS_DEBUG) {
        	System.out.println("增加爬取url:"+urls.toString());
        }
	}
}
