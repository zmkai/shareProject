package com.snsoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snsoft.dao.GetLastDao;

import utils.GetSqlbyId;
import utils.ProrocalUtils;


/**
 * Servlet implementation class getLastServlet
 */
@WebServlet("/getLastServlet")
public class GetLastServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLastServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("查找最新");
		String result = "";
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		String sql = GetSqlbyId.findSqlById("getLast");
		System.out.println(sql);
		results = new GetLastDao().getLast(sql);
		//System.out.println(results);
		if(results==null){
			System.out.println("查询失败");
			result = ProrocalUtils.serializeResult(1, "查询失败", null);
		}else if(results.isEmpty()){
			result=ProrocalUtils.serializeResult(0, "数据库为空", null);
		}else {
			result = ProrocalUtils.serializeResult(0, "查询成功", results);
		}
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		System.out.println("result="+result);
		PrintWriter out = response.getWriter();
		out.write(result);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
