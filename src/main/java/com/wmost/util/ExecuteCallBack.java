package com.wmost.util;

import java.sql.Connection;
import java.sql.ResultSet;

//@description	数据库通信类
//@author chenbintao
//@data		2016-9-1 10:15		初稿
//			2016-9-2 13:22		工程结构调整

public interface ExecuteCallBack {
	void resultCallBack(ResultSet result) throws Exception;
	void resultCallBack(ResultSet result,Connection conn) throws Exception;
}
