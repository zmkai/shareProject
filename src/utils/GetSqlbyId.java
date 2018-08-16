package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class GetSqlbyId {
	public static void main(String[] args) {
		System.out.println(findSqlById("queryUser"));
	}
	/**
	 * 从xml文件中解析出sql语句
	 * @param id 语句的id值
	 * @return  sql语句
	 */
	public static String findSqlById(String id){ 
		BufferedReader reader = null;
		String path = GetSqlbyId.class.getClassLoader().getResource("").getPath()+"sql.xml";
		File file = new File(path);
		try 
		{
			String sql = null;
			if(!file.exists()){
				System.out.println("文件不存在");
				return null;
			}
			
			StringBuilder sb = new StringBuilder();
			String line = null;
			reader = new BufferedReader(new FileReader(file));
			while((line = reader.readLine())!=null){
				sb.append(line);
			}
			Document d = Jsoup.parse(sb.toString());
			sql = d.getElementById(id).html();
			return sql;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
