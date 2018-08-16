package com.snsoft.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;






import utils.GetSqlbyId;
import utils.Operations;

public class GetFilesDao {
	private Operations cp;
	public GetFilesDao() {
		cp = new Operations();
	}
	
	public static void main(String[] args) {
		String sql = GetSqlbyId.findSqlById("getRecords");
		List<Object> params = new ArrayList<Object>();
		params.add("195140040");
		GetFilesDao gaf = new GetFilesDao();
		System.out.println(gaf.getPage(sql, "195140040"));
		//Map<String, Object> resultMap = gaf.getPage(sql, "195140040");
//		if (resultMap!=null&&!resultMap.isEmpty()) {
//			System.out.println(resultMap.get("pages"));
//		}else {
//			System.out.println("����");
//		}
		
		List<Object> params1 = new ArrayList<Object>();
		params1.add("195140040");
		params1.add(0);
		params1.add(5);
		String sqlString = GetSqlbyId.findSqlById("queryAFileInfo");
		List<Map<String, Object>> resulstList  = gaf.getOnePageInfo(sqlString, params1);
		if(resulstList!=null&&!resulstList.isEmpty()){
			for (Map<String, Object> map : resulstList) {
				for(Entry<String, Object> set:map.entrySet()){
					System.out.println(set.getKey()+"="+set.getValue());
					
				}
			}
			System.out.println(resulstList.size());
		}else {
			System.out.println("Ϊ��");
		}
		
	}
	
	/**
	 * ����ҳ��
	 * @param sql
	 * @param params
	 * @return
	 */
	public int getPage(String sql,String account){
		List<Object> params = new ArrayList<Object>();
		params.add(account);
		if (params != null && !params.isEmpty()) {
			Map<String, Object> result = cp.findOne(sql, params);
			if (result != null && !result.isEmpty()) {
				return Integer.parseInt((String) result.get("pages"));
			}
		}
		return -1;
	}
	
	
	/**
	 * ����һҳ��������
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getOnePageInfo(String sql,List<Object> params){
		try {
			if(params!=null&&!params.isEmpty()){
				return cp.findMore(sql, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cp.realseConnect();
		}
		return null;
	}

}
