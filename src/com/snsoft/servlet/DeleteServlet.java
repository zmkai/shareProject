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
import com.snsoft.dao.DeleteDao;
import utils.GetSqlbyId;
import utils.ProrocalUtils;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get");
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DeleteDao del = new DeleteDao();
		String result = "";
		Map<String, Object> reqparams = ProrocalUtils.getRequestParams(request);
		String id = (String) reqparams.get("id");
		String account = (String) reqparams.get("account");
		if(id!=null&&account!=null){
			List<Object> params = new ArrayList<Object>();
			params.add(id);
			params.add(account);
			String sql = GetSqlbyId.findSqlById("deleteFile");
			if(del.deleteFile(sql, params)){
				result = ProrocalUtils.serializeResult(0, "success", null);
			}else {
				result = ProrocalUtils.serializeResult(1, "删除失败", null);
			}
		}else {
			result = ProrocalUtils.serializeResult(1, "参数id或account不完整", null);
		}
		System.out.println("result="+result);
		PrintWriter out = response.getWriter();
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		out.write(result);
		out.flush();
		out.close();
	}

}
