package utils;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.helper.StringUtil;


public class ProrocalUtils {
	
	/**
	 * 解析request，将其中的参数都放入到map集合中
	 * @param request
	 * @return 
	 */
	public static Map<String, Object> getRequestParams(HttpServletRequest request){
		if (request == null) {
			return null;
		}
		String acceptjson = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if ("GET".equals(request.getMethod())) {
			acceptjson = request.getQueryString();
		}
		if ("POST".equals(request.getMethod())) {
			try {
				request.setCharacterEncoding("UTF-8");
				BufferedReader reader = request.getReader();
				StringBuilder sb = new StringBuilder();
				String temp;
				while ((temp = reader.readLine()) != null) {
					sb.append(temp);
				}
				reader.close();

				//System.out.println("请求中的数据为:" + sb);
				acceptjson = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("请求中的数据为:"+acceptjson);
		//解析数据放到map集合里
		//判断某字符串是否为空或长度为0或由空白符(whitespace)构成,对于制表符、换行符、换页符和回车符StringUtils.isBlank()均识为空白符
		if (!StringUtil.isBlank(acceptjson)) {
			if (acceptjson.contains("=")) {
				String[] strings = acceptjson.split("&");
				for (String string : strings) {
					map.put(string.split("=")[0], string.split("=")[1]);
				}
			}
		} else {
			JSONObject jo = JSONObject.fromObject(acceptjson);
			@SuppressWarnings("unchecked")
			Iterator<Object> it = jo.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = jo.getString(key);
				map.put(key, value);
			}
		}
		return map;
	}
	
	
	/**
	 * 封装结果，返回json格式的数据
	 * @param code
	 * @param msg
	 * @param object
	 * @return
	 */
	public static String serializeResult(int code,String msg,Object object){
		JSONObject jsonObject = new JSONObject();
		jsonObject.element("code", code);
		jsonObject.element("msg", msg);
		if(object!=null){
			if(object instanceof Collection<?>){
				JSONArray array = new JSONArray();
				array.addAll((Collection<?>) object);
				jsonObject.element("data", array);
			}else if(object instanceof Map<?, ?>){
					jsonObject.element("data", object);
			}else {
				jsonObject.element("data", object);
			}
		}
		return jsonObject.toString();
	}
}
