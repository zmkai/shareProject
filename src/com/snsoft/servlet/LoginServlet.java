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
		Map<String, Object> map = ProrocalUtils.getRequestParams(request);//����request�е����ݣ�����ò���
		String account = (String) map.get("account");
		String password = (String) map.get("password");
		
		
		if(StringUtil.isBlank(account)){
			result = ProrocalUtils.serializeResult(1, "�˻�����Ϊ��", null);
		}else if(StringUtil.isBlank(password)){
			result = ProrocalUtils.serializeResult(1, "���벻��Ϊ��", null);
		}else{
			List<Object> params = new ArrayList<Object>();
			//�����������
			params.add(account);
			params.add(password);
			//System.out.println("account="+account+" "+"password="+password);
			//���sql���
			String sql = GetSqlbyId.findSqlById("queryUser");
			//System.out.println(sql);
			boolean b = new loginDao().login(sql, params);
			//System.out.println(b);
			if(b){
				request.getSession().setAttribute("account", account);
				result = ProrocalUtils.serializeResult(0, "��¼�ɹ�", null);
				//System.out.println(ProrocalUtils.serializeResult(0, "��¼�ɹ�", null));
			}else {
				result = ProrocalUtils.serializeResult(1, "�˻����������", null);
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
