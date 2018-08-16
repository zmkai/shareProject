package com.snsoft.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import utils.Operations;

public class searchDao {
	private static Operations op;
	public searchDao() {
		op=new Operations();
	}
	public List<Map<String,Object>> getUsers(String sql){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = op.findMore(sql, null);
		return result;
	}

}
