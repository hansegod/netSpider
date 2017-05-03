package com.wmost.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

//@description	数据库通信类
//@author hanse/irene
//@data	2016-09-01 10:15		初稿
//		2016-09-02 13:22		工程结构调整
//		2016-09-14 16:42		增加查询,插入连续操作方法
//		2016-09-18 11:42		增加初始化时的遗留数据清理,增加查询/批处理方法调用时的参数合法性检测
//		2016-09-20 17:01		增加日志方法,增加数据库连接关闭
//		2017-05-03 17:01		清除日志输出



public class JDBCWrapper {
	@SuppressWarnings("unused")
	private static String 	server 			= null;
	@SuppressWarnings("unused")
	private static String 	db 				= null;
	@SuppressWarnings("unused")
	private static String 	name 			= null;
	@SuppressWarnings("unused")
	private static String 	password 		= null;
	@SuppressWarnings("unused")
	private static int 		pool 			= 0;
	private static JDBCWrapper jdbcInstance = null;
	private static LinkedBlockingQueue<Connection> dbConnectionPool = new LinkedBlockingQueue<Connection> ();
   
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//logg.Println("Class JDBCWrapper Load JDBC dirver is ok..."); 
		} catch (ClassNotFoundException e) {
			//logg.Error("Class JDBCWrapper Load JDBC dirver is failed!"); 
			e.printStackTrace();
		}
	}
	private JDBCWrapper(){
		
	}
	//采用单例模式(初始化后再传递参数将无效)
	public static JDBCWrapper getJDBCInstance(){
		
		return jdbcInstance;
	}
	public static JDBCWrapper getJDBCInstance(String server,String db,String name,String password,int pool){
		JDBCWrapper.server 	= server;
		JDBCWrapper.db		= db;
		JDBCWrapper.name	= name;
		JDBCWrapper.password= password;
		JDBCWrapper.pool	= pool;
		
		if (jdbcInstance == null){ 
			synchronized(JDBCWrapper.class){
				//logg.Println("Class JDBCWrapper Build JDBC instance first time...");
				if (jdbcInstance == null){
					jdbcInstance = new JDBCWrapper(server,db,name,password,pool);
					//logg.Println("Class JDBCWrapper Build JDBC instance is ok......................");
				}           
			}   
		}else{
			//logg.Println("Class JDBCWrapper Build JDBC instance has been builded return ...");	
		}
		
		return jdbcInstance;
	}
	//建立连接池
	private JDBCWrapper(String server,String db,String name,String password,int pool){
		if (null == server || server.equals("") || null == db || db.equals("") || null == name || name.equals("") || null == password || password.equals("")){
			return;
		}
		pool = (pool > 0) ? pool : 1;
		//logg.Println("Class JDBCWrapper Build JDBC link pool is building...");
		//1.清理链表
		while (0 < dbConnectionPool.size()){
			try {
				dbConnectionPool.poll().close();
			} catch (SQLException e) {
				//logg.Error("Class JDBCWrapper JDBCWrapper() SQLException err...");
				e.printStackTrace();
			}
		}
		//2.生成连接池
		String dburl = "jdbc:mysql://"+ server + "/" + db;
		//logg.Println("Class JDBCWrapper connect to:" + dburl + "/use:" + name + "/" + password);
		for (int i = 0; i < pool; i++){
			try {
				Connection conn = DriverManager.getConnection(dburl,name,password);
				dbConnectionPool.put(conn);
			} catch (Exception e) {
				//logg.Error("Class JDBCWrapper Build JDBC link pool is failed!");
				e.printStackTrace();
			}
		} 
		//logg.Println("Class JDBCWrapper Build JDBC link pool is ok....");
		
	}
	//连接池
	public synchronized Connection getConnection(){
		while (0 == dbConnectionPool.size()){
			try {
				Thread.sleep(1000);
				//logg.Println("Class JDBCWrapper Get JDBC link pool is waiting getConnection...");
			} catch (InterruptedException e) {
				//logg.Error("Class JDBCWrapper Get JDBC link pool is failed!");
				e.printStackTrace();
			}
		}
		//logg.Println("Class JDBCWrapper Get JDBC link pool getConnection ok...");
		
		return dbConnectionPool.poll();
	}
	//执行批处理
	public int[] doBatch(String sqlText, List<Object[]> paramsList) {
		if (sqlText.equals("") || paramsList == null ){
			//logg.Println("Class JDBCWrapper JDBC doQuery() parameter err!");
			return new int[]{};
		}
		Connection conn = getConnection();
		PreparedStatement preparedStatement = null;
		int[] result = null;
		try {
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(sqlText);
			for (Object[] parameters : paramsList){
				for(int i = 0; i < parameters.length; i++){
					preparedStatement.setObject(i+1, parameters[i]);
				}
				preparedStatement.addBatch();
			}
			result = preparedStatement.executeBatch();
			conn.commit();
		} catch (Exception e) {
			//logg.Error("Class JDBCWrapper JDBC doBatch is failed!");
			e.printStackTrace();
		} finally {
			if (preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				try {
					dbConnectionPool.put(conn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
      
		return result;
	}
	//查询
	public void doQuery(String sqlText, Object[] paramsList, ExecuteCallBack callBack) {
		if (sqlText.equals("") || paramsList == null || paramsList.length == 0 || callBack == null){
			//logg.Println("Class JDBCWrapper JDBC doQuery() parameter err!");
			return;
		}
		Connection conn = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			preparedStatement = conn.prepareStatement(sqlText);
			for(int i = 0; i < paramsList.length; i++){
				preparedStatement.setObject(i+1, paramsList[i]);
			}
			result = preparedStatement.executeQuery();
			callBack.resultCallBack(result);
		} catch (Exception e) {
			//logg.Error("Class JDBCWrapper JDBC doQuery is failed!");
			e.printStackTrace();
		} finally {
			if (preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				try {
					dbConnectionPool.put(conn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} 
	}
	//执行批处理
	public int[] doBatchUnCommit(String sqlText, List<Object[]> paramsList,Connection conn) {
		if (sqlText.equals("") || paramsList == null || conn == null){
			//logg.Error("Class JDBCWrapper JDBC doBatchUnCommit() parameter err!");
			return new int[]{};
		}
		PreparedStatement preparedStatement = null;
		int[] result = null;
		try {
			preparedStatement = conn.prepareStatement(sqlText);
			for (Object[] parameters : paramsList){
				for(int i = 0; i < parameters.length; i++){
					preparedStatement.setObject(i+1, parameters[i]);
				}
				preparedStatement.addBatch();
			}
			result = preparedStatement.executeBatch();
		} catch (Exception e) {
			//logg.Error("Class JDBCWrapper JDBC doBatchUnCommit is failed! "+sqlText);
			e.printStackTrace();
		} finally {
			if (preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				try {
					dbConnectionPool.put(conn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
      
		return result;
	}
	//查询
	public void doQueryUnCommit(String sqlText, Object[] paramsList, ExecuteCallBack callBack,Connection conn) {
		if (sqlText.equals("") || paramsList == null || paramsList.length == 0 || callBack == null || conn == null){
			//logg.Error("Class JDBCWrapper JDBC doQueryUnCommit() parameter err!");
			return;
		}
		try {
			conn.isClosed();
		} catch (SQLException e1) {
			//logg.Error("Class JDBCWrapper JDBC doQueryUnCommit() conn isClosed() err!");
			e1.printStackTrace();
			return;
		}
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			preparedStatement = conn.prepareStatement(sqlText);
			for(int i = 0; i < paramsList.length; i++){
				preparedStatement.setObject(i+1, paramsList[i]);
			}
			result = preparedStatement.executeQuery(); 
			callBack.resultCallBack(result,conn);
		} catch (Exception e) {
			//logg.Error("Class JDBCWrapper JDBC doQueryUnCommit is failed!");
			e.printStackTrace();
		} finally {
			if (preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				try {
					dbConnectionPool.put(conn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} 
	}
	//释放连接
	public void Close(){
		synchronized(JDBCWrapper.class){
			while( dbConnectionPool.size() > 0 ){
				 try {
					dbConnectionPool.poll().close();
				} catch (SQLException e) {
					//logg.Error("Class JDBCWrapper JDBC Close() err!");
					e.printStackTrace();
				}
			}
			dbConnectionPool.clear();
			jdbcInstance=null;
			//logg.Println("Class JDBCWrapper JDBC Close() ok...");
		}
	}
}
