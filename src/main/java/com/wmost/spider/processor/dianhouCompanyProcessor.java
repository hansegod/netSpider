/**
@description  dianhou网爬取器
@author hanse/irene
@data	2017-04-08	00:00	初稿
		2017-04-21	00:00	整理代码
		2017-04-21	21:01	完善代码架构,按照设计稿进行了模块的划分,日志输出正常
		2017-04-21	21:31	整理规范调试信息打印
		2017-04-21	21:41	修正增加爬取链接逻辑
		2017-04-21	22:21	升级获取外网IP方法
		2017-04-23	21:00	修改爬取信息组织形式,采用json封装对象数组至body,支持同时爬取多个对象
	
		
**/


package com.wmost.spider.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.wmost.cfig.LOG;
import com.wmost.cfig.SRC_CODE;
import com.wmost.spider.model.company;
import com.wmost.util.DeviceIfo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class dianhouCompanyProcessor implements PageProcessor {
	private static boolean IS_DEBUG = false;
	
	//"http://www\\.dianhou\\.com/company\\?p=company\\&page=[1-9]{1,3}";
    public static final String URL_TARGET = "http://www\\.dianhou\\.com/company/detail/[0-9]{1,10}";
    public static final String URL_MORE = ".*p=company\\&page=[0-9]{1,10}\\.html";
    public static final String server_ip = DeviceIfo.getV4IP();
    
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site page = Site.me().setRetryTimes(3).setSleepTime(1000);
	public Site getSite() {
		
		return page;
	}

	public void process(Page page) {
		long startTime = System.currentTimeMillis();
        
        // 部分二：定义如何抽取页面信息，并保存下来
        if(page.getUrl().regex(URL_TARGET).match()){
			page.putField(LOG.log_type, LOG.LOG_TYPE.LOG_TYPE_COMPANY+"");
			page.putField(LOG.time_ms, (System.currentTimeMillis()-startTime)+"");
			page.putField(LOG.error_code, page.getStatusCode()+"");
			{
				company[] c = new company[24];
				{
					c[0]			= 	new company();
					c[0].src		=	SRC_CODE.WEB_CODE_DIANHOU+"";
					c[0].name		=	page.getHtml().xpath("html/body/div[3]/div/div[1]/div/div[1]/div[2]/h3/span[1]/text()").toString();
					c[0].industry	=	page.getHtml().xpath("html/body/div[3]/div/div[1]/div/div[1]/div[2]/h3/span[1]/text()").toString();
					c[0].scale		=	page.getHtml().xpath("html/body/div[3]/div/div[2]/div[1]/div/p[3]/text()").toString();
//					c[0].nature		=	page.getHtml().xpath("").toString();
//					c[0].position	=	page.getHtml().xpath("").toString();
//					c[0].salary		=	page.getHtml().xpath("").toString();
					c[0].location	=	page.getHtml().xpath("html/body/div[3]/div/div[1]/div/div[3]/div[2]/a/span/span/text()").toString();
					c[0].website	=	page.getHtml().xpath("html/body/div[3]/div/div[2]/div[1]/div/p[4]/a/text()").toString();
//					c.tag			=	page.getHtml().xpath("").toString();
				}
				String body = new Gson().toJson(c);
				page.putField(LOG.body, body);
				if (IS_DEBUG) {
					System.out.println("抽取后整理的body:"+body);
				}
			}
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
