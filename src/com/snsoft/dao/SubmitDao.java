package com.snsoft.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Operations;


public class SubmitDao {
	private Operations cpu ;
	public SubmitDao(){
		cpu = new Operations();
	}
	
	public Map<String, Object> panDing(String sql , Map<String, Object> params){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> paramList = new ArrayList<Object>();
		String account = (String) params.get("account");
		String fileName = (String) params.get("filename");
		String filechangeName = (String) params.get("filechangename");
		if (account == null || fileName == null) {
			System.out.println("上传失败，因为参数不足");
			return null;
		}
		paramList.add(account);
		paramList.add(fileName);
		paramList.add(filechangeName);
		System.out.println("执行查找函数");
		System.out.println("account=" + account + "filename=" + fileName);
		for (Object object : paramList) {
			System.out.println(object.toString());
		}
		map = cpu.findOne(sql, paramList);
		System.out.println(map.isEmpty());
		return map;
	}
	
	public boolean submit(String sql,Map<String, Object> params){
		List<Object> list = new ArrayList<Object>();
		if (params != null) {
			list.add(params.get("id"));
			list.add(params.get("account"));
			list.add(params.get("uploadDate"));
			list.add(params.get("fileName"));
			list.add(params.get("href"));
			if (cpu.indelUpdate(sql, list)) {
				return true;
			}
			return false;
		}
		return false;
	}

}
