package com.snsoft.dao;

import java.util.List;
import java.util.Map;
import utils.Operations;

public class GetRecordsDao {
	private Operations cp;
	public GetRecordsDao() {
		cp = new Operations();
	}
	
	public Map<String, Object> getCounts(String sql,List<Object> params){
		return cp.findOne(sql, params);
	}

}
