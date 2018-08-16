package com.snsoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import com.snsoft.dao.GetRecordsDao;

import utils.GetSqlbyId;
import utils.ProrocalUtils;

/**
 * Servlet implementation class GetCountsServlet
 */
@WebServlet("/GetRecordsServlet")
public class GetRecordsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRecordsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> params = ProrocalUtils.getRequestParams(request);
		int pageCounts =0;
		String sql =null;
		String result = "";
		Map<String, Object> results = new HashMap<String, Object>();
		if(params.get("sign")==null){
			System.out.println("缺少Sign参数");
			return ;
		}
		String pageCount = (String) params.get("pageCount");
		int size = 0;
		if(pageCount==null&&!"0".equals(pageCount)){
			size = 10;
		}else {
			size = Integer.parseInt(pageCount);
		}
		String sign = (String) params.get("sign");
		if("0".equals(sign)){//表示查询所有纪录数
			System.out.println("sign="+sign);
			sql = GetSqlbyId.findSqlById("getAllRecord");
			results = new GetRecordsDao().getCounts(sql, null);
			
			
		}
		if("1".equals(sign)){//标识查询某一用户的上传记录
			System.out.println("sign="+sign);
			List<Object> list = new ArrayList<Object>();
			list.add(params.get("account"));
			sql = GetSqlbyId.findSqlById("getUserRecord");
			results = new GetRecordsDao().getCounts(sql, list);
		}
//		for(String s :results.keySet()){
//			System.out.println(s);
//		}
		//记录总数目
		String recordCount = (String) results.get("recordCount");
		//计算页面总数目
		int yushu = Integer.parseInt(recordCount)%size;
		if(yushu!=0){
			pageCounts = Integer.parseInt(recordCount)/size+1;
		}else {
			pageCounts = Integer.parseInt(recordCount)/size;
		}
		results.put("pageCount",pageCounts);
		if(results!=null){
			result = ProrocalUtils.serializeResult(0, "查找成功", results);
		}else {
			result = ProrocalUtils.serializeResult(1, "查找失败", null);
		}
		System.out.println(result);
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.flush();
		out.close();
	}

}
