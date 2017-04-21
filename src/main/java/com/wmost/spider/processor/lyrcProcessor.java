/**
@description  lyrc网爬取器
@author hanse/irene
@data	2017-04-08	00:00:00	初稿
		2017-04-21	00:00:00	整理代码
		2017-04-21	21:01:00	完善代码架构,按照设计稿进行了模块的划分,日志输出正常
		2017-04-21	21:31:00	整理规范调试信息打印
		2017-04-21	22:21:00	升级获取外网IP方法
		
		
**/


package com.wmost.spider.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wmost.app.*;
import com.wmost.cfig.LOG;
import com.wmost.cfig.LOG_TYPE;
import com.wmost.spider.model.user;
import com.wmost.util.deviceIfo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class lyrcProcessor implements PageProcessor{
	private static boolean IS_DEBUG = false;
    public static final String URL_TARGET ="/Person_Lookzl/id-[0-9]{4}\\.html";
    public static final String URL_MORE="http://rc\\.lyrc\\.net/Companyzp\\.aspx\\?Page=[1-9]{1,3}";
    public static final String server_ip = deviceIfo.getV4IP();
    
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000); 
	public Site getSite() {
		
		return site;
	}

	public void process(Page page) {
		long startTime, endTime;
        startTime =System.currentTimeMillis();

        // 部分二：定义如何抽取页面信息，并保存下来
        if(page.getUrl().regex(URL_TARGET).match()){
            String name =page.getHtml().xpath("//*[@width='61%']/table/tbody/tr[1]/td[2]/text()").get();//用户名
            String sex = page.getHtml().xpath("//*[@width='61%']/table/tbody/tr[1]/td[4]/text()").get();//性别
            String ethnic = page.getHtml().xpath("//*[@width='61%']/table/tbody/tr[2]/td[4]/text()").get().toString();//民族
            String location = page.getHtml().xpath("//*[@width='61%']/table/tbody/tr[3]/td[4]/text()").get();//所在地
            String identity =page.getHtml().xpath("//*td[@width='283']/text()").get();//身份学历
            String school =page.getHtml().xpath("//*td[@width='201']/text()").get();//学校
            String major =page.getHtml().xpath("//*[@width='90%']/tbody/tr[2]/td[4]/text()").get();//专业
            String work_experience = page.getHtml().xpath("//td[@width='773']/table/tbody/tr/td/table[6]/tbody/tr[2]/td[2]/text()").get();//工作经验
            String hope_position = page.getHtml().xpath("//td[@width='773']/table/tbody/tr/td/table[8]/tbody/tr[5]/td[2]/text()").get();//希望求职岗位
            String hope_palce = page.getHtml().xpath("//td[@width='773']/table/tbody/tr/td/table[8]/tbody/tr[4]/td[2]/text()").get();//希望工作地点
            String hope_salary = page.getHtml().xpath("//td[@width='773']/table/tbody/tr/td/table[8]/tbody/tr[2]/td[2]/text()").get();//希望待遇
            String work_type = page.getHtml().xpath("//td[@width='773']/table/tbody/tr/td/table[8]/tbody/tr[1]/td[2]/text()").get();
            
            endTime = System.currentTimeMillis();
            page.putField(LOG.log_type, LOG_TYPE.LOG_TYPE_USER+"");
            page.putField(LOG.time_ms, (endTime-startTime)+"");
            page.putField(LOG.error_code, page.getStatusCode()+"");
            page.putField(LOG.user.name, name);
            page.putField(LOG.user.sex, sex);
            page.putField(LOG.user.ethnic, ethnic);
            page.putField(LOG.user.location, location);
            page.putField(LOG.user.identity, identity);
            page.putField(LOG.user.school, school);
            page.putField(LOG.user.major, major);
            page.putField(LOG.user.work_experience, work_experience);
            page.putField(LOG.user.hope_position, hope_position);
            page.putField(LOG.user.hope_palce, hope_palce);
            page.putField(LOG.user.hope_salary, hope_salary);
            page.putField(LOG.user.hope_salary, hope_salary);
            page.putField(LOG.user.work_type, work_type);
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
