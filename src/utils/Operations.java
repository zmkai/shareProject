package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snsoft.dbSource.BasicDBSource;
import com.snsoft.dbSource.DbSource;

/**
 * 操作集合包括获得连接，查找一条数据，插入，更新，删除，查询多条数据，批删除
 * @author Administrator
 *
 */
public class Operations {
	private DbSource ds;
	private Connection connection;
	private Statement st;
	private PreparedStatement p;
	private ResultSet rs ;
	public Operations(){
		try {
			ds=new BasicDBSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得连接
	 * @return Connection
	 */
	public  Connection getConnect(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查找一条数据
	 * @param sql sql语句
	 * @param params list参数列表
	 * @return  象征记录的map集合
	 * @throws SQLException 异常
	 * 
	 */
	public Map<String, Object> findOne(String sql,List<Object> params){
		try {
			Map<String, Object> map= new HashMap<String, Object>();
			connection = getConnect();
			p= connection.prepareStatement(sql);
			if(params!=null&&!params.isEmpty()){
				for(int i = 0;i<params.size();i++){
					p.setObject(i+1, params.get(i));
				}
			}
			rs = p.executeQuery();//执行数据库查询操作，返回结果集
			ResultSetMetaData  rm = rs.getMetaData();
			int count = rm.getColumnCount();
			while(rs.next()){
				for(int i = 0;i<count;i++){
					String columnName = rm.getColumnName(i+1);
					String value = rs.getString(columnName);
					if(value==null){
						value = "";
					}
					map.put(columnName, value);
				}
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			realseConnect();
		}
		return null;
		
	}
	
	/**
	 * 
	 * 查询多条数据
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMore(String sql,List<Object> params){
		try {
			connection = getConnect();
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			p = connection.prepareStatement(sql);
			if(params!=null&&!params.isEmpty()){
				for(int i = 0;i<params.size();i++){
					p.setObject(i+1, params.get(i));
				}
			}
			rs = p.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i =0;i<count;i++){
					String col_name = metaData.getColumnName(i+1);
					String col_value = rs.getString(col_name);
					if(col_value==null){
						col_value ="";
					}
					map.put(col_name, col_value);
				}
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			realseConnect();
		}
		return null;
	}
	
	
	/**
	 * 
	 * 插入，更新，删除
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean indelUpdate(String sql,List<Object> params){
		try {
			connection = getConnect();
			p = connection.prepareStatement(sql);
			if(params!=null&&!params.isEmpty()){
				for (int i = 0; i <params.size(); i++) {
					System.out.println(params.get(i));
					p.setObject(i+1, params.get(i));
				}
				p.execute();
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			realseConnect();
		}
		return false;
	}
	


	/*
	 * 批删除
	 */
	public boolean delBattch(String[] sql){
		try {
			boolean flag = false;
			int[] result;//接收批处理后的结果
			if(sql!=null){
				st = connection.createStatement();
				for(int i =0;i<sql.length;i++){
					st.addBatch(sql[i]);
				}
				result = st.executeBatch();
				if(result!=null){
					flag = true;
				}
			}
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			realseConnect();
		}
		return false;
		
	}
	
	
	/**
	 * 释放连接
	 * @param pst
	 * @param rs
	 * @param conn
	 */
	public void realseConnect() {
        if(rs!=null){  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if(p!=null){  
            try {  
                p.close(); 
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if(st!=null){  
            try {  
                st.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
  
        if(connection!=null){  
            try {  
                ds.closeConnect(connection); 
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println("释放成功");
	}
}
