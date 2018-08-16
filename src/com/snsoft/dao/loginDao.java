package com.snsoft.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Operations;

/**
 * 登录接口的DAO实现
 * @author Administrator
 *
 */
public class loginDao {
	private Operations op;
	public loginDao() {
		op = new Operations();
	}
	public boolean login(String sql,List<Object> params){
		Map<String, Object> map = new HashMap<String, Object>();
		map = op.findOne(sql, params);
		System.out.println(map.size());
		if (map != null && !map.isEmpty()) {
			return true;
		}
		return false;
	}
}
