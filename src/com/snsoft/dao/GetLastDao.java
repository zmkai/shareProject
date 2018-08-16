package com.snsoft.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Operations;


public class GetLastDao {
	private Operations cp;
	public GetLastDao() {
		cp = new Operations();
	}
	/**
	 * ������������
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getLast(String sql){
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
			results = cp.findMore(sql, null);
			return results;
	}

}
