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

import org.jsoup.helper.StringUtil;
import com.snsoft.dao.loginDao;

import utils.GetSqlbyId;
import utils.ProrocalUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ni");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "";
		Map<String, Object> map = ProrocalUtils.getRequestParams(request);//解析request中的数据，即获得参数
		String account = (String) map.get("account");
		String password = (String) map.get("password");
		
		
		if(StringUtil.isBlank(account)){
			result = ProrocalUtils.serializeResult(1, "账户不能为空", null);
		}else if(StringUtil.isBlank(password)){
			result = ProrocalUtils.serializeResult(1, "密码不能为空", null);
		}else{
			List<Object> params = new ArrayList<Object>();
			//构造参数集合
			params.add(account);
			params.add(password);
			//System.out.println("account="+account+" "+"password="+password);
			//获得sql语句
			String sql = GetSqlbyId.findSqlById("queryUser");
			//System.out.println(sql);
			boolean b = new loginDao().login(sql, params);
			//System.out.println(b);
			if(b){
				request.getSession().setAttribute("account", account);
				result = ProrocalUtils.serializeResult(0, "登录成功", null);
				//System.out.println(ProrocalUtils.serializeResult(0, "登录成功", null));
			}else {
				result = ProrocalUtils.serializeResult(1, "账户或密码错误", null);
			}
			System.out.println("result="+result);
			response.setContentType("json/application");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out =  response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		}
	}
}
