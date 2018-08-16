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

import utils.GetSqlbyId;
import utils.ProrocalUtils;

import com.snsoft.dao.GetFilesDao;

/**
 * Servlet implementation class SearchProjects
 */
@WebServlet("/SearchProjectsServlet")
public class SearchProjectsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchProjectsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET");
		String result="";
		GetFilesDao dao = new GetFilesDao();
		//解析request请求，获取参数数据
		Map<String, Object> requestMap = ProrocalUtils.getRequestParams(request);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<Object> params = new ArrayList<Object>();
		String account = (String) requestMap.get("account");
		String currentPage = (String) requestMap.get("currentPage");
		if(currentPage==null||"0".equals(currentPage)){
			result=ProrocalUtils.serializeResult(1, "缺少当前页面参数",null);
		}else {
			if (account != null) {
				params.add(account);
				params.add((Integer.parseInt(currentPage) - 1) * 10);
				String sql = GetSqlbyId.findSqlById("queryAFileInfo");
				list = dao.getOnePageInfo(sql, params);
			} else {
				params.add((Integer.parseInt(currentPage) - 1) * 10);
				String sql = GetSqlbyId.findSqlById("getLast");
				list = dao.getOnePageInfo(sql, params);
			}
			if (list == null || list.isEmpty()) {
				result = ProrocalUtils.serializeResult(1, "查找失败", null);
			} else {
				result = ProrocalUtils.serializeResult(0, "查找成功", list);
			}
			
		}

		

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
//				params.add((Integer.parseInt(currentPage)-1)*10);
//				params.add(10);
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
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");;
		PrintWriter out = response.getWriter();
		out.write(result);
		System.out.println(result.toString());
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
