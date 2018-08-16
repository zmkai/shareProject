package com.snsoft.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Operations;

public class PanDingDao {
	private Operations op;
	public PanDingDao(){
		op=new Operations();
	}
	
	public  boolean searchFile(String sql,List<Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = op.findOne(sql, params);
		if(map!=null&&map.size()!=0){
			return true;
		}else {
			return false;
		}
		
	}

}
