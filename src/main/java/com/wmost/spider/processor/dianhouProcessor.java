/**
@description  dianhou网爬取器
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
		2017-04-21	21:01:00	完善代码架构,按照设计稿进行了模块的划分,日志输出正常
**/


package com.wmost.spider.processor;

import java.util.List;

import com.wmost.cfig.LOG;
import com.wmost.cfig.LOG_TYPE;
import com.wmost.util.deviceIfo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class dianhouProcessor implements PageProcessor {
	private static boolean IS_DEBUG = false;
    public static final String URL_LIST ="http://www\\.dianhou\\.com/company\\?p=company\\&page=[1-9]{1,3}";
    public static final String URL_POST="/p=company\\&page=[1-9]{1,3}\\.html";

	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site page = Site.me().setRetryTimes(3).setSleepTime(1000);
	public Site getSite() {
		
		return page;
	}

	public void process(Page page) {
		// 部分二：定义如何抽取页面信息，并保存下来
		long startTime, endTime;
        startTime =System.currentTimeMillis();
        
        List<String> urls = page.getHtml().links().regex("http://www.dianhou.com/company?p=company&page=\\d+").all();
		if(!page.getUrl().regex(URL_LIST).match()){
			// 部分二：定义如何抽取页面信息，并保存下来
			String name = page.getHtml().xpath("//div[1]/div[5]//div[2]//ul/li/a/@title").toString();
			String location = page.getHtml().xpath("//div[1]/div[5]//div[2]//ul/li/a/@src").toString();
			endTime = System.currentTimeMillis();
			page.putField(LOG.log_type, LOG_TYPE.LOG_TYPE_COMPANY+"");
			page.putField(LOG.time_ms, (endTime-startTime)+"");
			page.putField(LOG.error_code, page.getStatusCode()+"");
			page.putField(LOG.company.name, name);
			page.putField(LOG.company.location, location);
			page.putField(LOG.server_ip, deviceIfo.getLocalIP());
        }
        else{
        	// 部分三：从页面发现后续的url地址来抓取
            page.addTargetRequests(page.getHtml().links().regex(URL_POST).all());
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            page.addTargetRequests(urls);
        }
	}
}
