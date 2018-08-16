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

import com.snsoft.dao.searchDao;

import utils.GetSqlbyId;
import utils.ProrocalUtils;

/**
 * Servlet implementation class search
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result="";
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		String sql = GetSqlbyId.findSqlById("queryUsers");
		results = new searchDao().getUsers(sql);
		System.out.println("*******************");
		for (Map<String, Object> map : results) {
			System.out.println(map.get("account")+"  "+map.get("username")+"  "+map.get("password"));
		}
		
		System.out.println("*******************");
		result = ProrocalUtils.serializeResult(0, "查询成功", results);
		System.out.println("result="+result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("json/application");
		PrintWriter out =response.getWriter();
		out.write(result);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("post");
//		String result="";
//		GetFilesDao dao = new GetFilesDao();
//		Map<String, Object> requestMap = ProrocalUtils.getRequestParams(request);
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		List<Object> params = new ArrayList<Object>();
//		String account = (String) requestMap.get("account");
//		String currentPage = (String) requestMap.get("currentPage");
//		String pageCount = (String) requestMap.get("pageCount");
//		if(pageCount!=null&&!"0".equals(pageCount)){
//			if(account!=null||currentPage!=null){
//				params.add(account);
//				params.add((Integer.parseInt(currentPage)-1)*(Integer.parseInt(pageCount)));
//				params.add(Integer.parseInt(pageCount));
//				String sql = GetSqlbyId.findSqlById("queryAFileInfo");
//				list = dao.getOnePageInfo(sql, params);
//				if(list==null||list.isEmpty()){
//					result = ProrocalUtils.serializeResult(1, "查找失败", null);
//				}else {
//					result = ProrocalUtils.serializeResult(0, "查找成功", list);
//				}
//			}else {
//				result=ProrocalUtils.serializeResult(1, "账号或者当前页参数不完整", null);
//			}
//		}else {
//			if(account!=null||currentPage!=null){
//				params.add(account);
//				params.add((Integer.parseInt(currentPage)-1)*5);
//				params.add(5);
//				String sql = GetSqlbyId.findSqlById("queryAFileInfo");
//				list = dao.getOnePageInfo(sql, params);
//				if(list==null||list.isEmpty()){
//					result = ProrocalUtils.serializeResult(1, "查找失败", null);
//				}else {
//					result = ProrocalUtils.serializeResult(0, "查找成功", list);
//				}
//			}else {
//				result=ProrocalUtils.serializeResult(1, "账号或者当前页参数不完整", null);
//			}
//		}
//		response.setContentType("json/application");
//		response.setCharacterEncoding("UTF-8");;
//		PrintWriter out = response.getWriter();
//		out.write(result);
//		System.out.println(result.toString());
//		out.flush();
//		out.close();
		this.doGet(request, response);
	}

}
