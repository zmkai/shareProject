package com.snsoft.dbSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class BasicDBSource implements DbSource {
	private Properties pro;
	private String url;
	private String user;
	private String password;
	private int maxCount;//最大连接数
	private List<Connection> connections;//集合类存放被关闭的连接
	
	public BasicDBSource() throws FileNotFoundException, ClassNotFoundException, IOException {
		//调用带参构造函数
		this(Thread.currentThread().getContextClassLoader().getResourceAsStream("myJdbc.properties"));
	}
	public BasicDBSource(InputStream configFile) throws FileNotFoundException, IOException, ClassNotFoundException{
		pro = new Properties();
		pro.load(configFile);
		url = pro.getProperty("url");
		user = pro.getProperty("user");
		password = pro.getProperty("password");
		maxCount = Integer.parseInt(pro.getProperty("poolMax"));
		Class.forName(pro.getProperty("driver"));
		connections = new ArrayList<Connection>();
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		//要获得连接时，先判断集合中是否存在连接，如果没有则像以前一样创建，如果有则弹出一个连接对象
		if(connections.size()==0){
			return DriverManager.getConnection(url, user, password);
		}else {
			int last = connections.size()-1;
			return connections.remove(last);
		}
	}

	@Override
	public synchronized void  closeConnect(Connection connection) throws SQLException {
		if (connections.size()>=5) {
			connection.close();
		}else {
			connections.add(connection);
		}
	}

}
