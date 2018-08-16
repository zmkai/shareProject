package com.snsoft.dao;

import java.util.ArrayList;
import java.util.List;

import utils.GetSqlbyId;
import utils.Operations;

public class DeleteDao {
	private Operations cp;
	public DeleteDao() {
		cp = new Operations();
	}
	public static void main(String[] args) {
		DeleteDao dao = new DeleteDao();
		List<Object> params = new ArrayList<Object>();
		params.add("20123213");
		params.add("195140040");
		System.out.println(dao.deleteFile(GetSqlbyId.findSqlById("deleteFile"), params));
	}
	
	/**
	 * 删除上传文件
	 * @param sql
	 * @param params
	 * @return boolean
	 */
	public boolean deleteFile(String sql,List<Object> params){
		boolean b = false;
		try {
			if(params!=null&&!params.isEmpty()){
				b=cp.indelUpdate(sql, params);
				if(b){
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cp.realseConnect();
		}
		return false;
	}

}
