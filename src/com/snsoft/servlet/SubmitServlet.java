package com.snsoft.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import utils.DeCompressUtil;
import utils.GetSqlbyId;
import utils.ProrocalUtils;

import com.snsoft.dao.PanDingDao;
import com.snsoft.dao.SubmitDao;
import com.snsoft.domain.FileElement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/SubmitServlet")
public class SubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		FileElement fe = new FileElement();
		fe = fileUpload(request, fe);
		String result = "";
		
		if (fe.getAccount() != null) {
			List<Object> list = new ArrayList<Object>();
			list.add(fe.getAccount());
			list.add(fe.getFileName());
			String sqlString = GetSqlbyId.findSqlById("getInfoByfileName");
			boolean bl = new PanDingDao().searchFile(sqlString, list);
			//System.out.println(bl);
			if(bl){
				result=ProrocalUtils.serializeResult(1, "�ļ����ظ�", null);
			}else {
				try {
					if ("rar".equals(fe.getFileType())) {
						DeCompressUtil.unrar(fe.getAbsulatePath(), fe.getDestPath());
					} else if ("zip".equals(fe.getFileType())) {
						DeCompressUtil.unzip(fe.getAbsulatePath(), fe.getDestPath());
					} else {

						System.out.println("ֻ֧��zip��rar");
						return;
					}
					//ƴ�ճ���ѹ����õ��ļ�·��
					String destPath = this.getServletContext().getRealPath(File.separator+"dist"+File.separator+fe.getAccount()+File.separator+fe.getFileName());
					//����ļ���Ŀ¼·��
					String href = destPath.substring(destPath.indexOf("shareProject"));
					String string = href.replaceAll("\\\\", "/");
					string="http://"+request.getServerName()+":"+request.getServerPort()+"/"+string;
					fe.setHref(string);
					

					params.put("id", fe.getId().substring(0, 8));
					params.put("account", fe.getAccount());
					params.put("uploadDate", fe.getUploadDate());
					params.put("fileName", fe.getFileName());
					params.put("href", fe.getHref());
					//���ϴ��ļ���Ϣ�������ݿ�
					String sql = GetSqlbyId.findSqlById("insertFileInfo");
					boolean b = new SubmitDao().submit(sql, params);
					if (b) {
						result = ProrocalUtils.serializeResult(0, "�ϴ��ɹ�", null);
					} else {
						result = ProrocalUtils.serializeResult(1, "�ϴ�ʧ��", null);
					}
					
					File dest = new File(destPath);
					if (!dest.exists()) {
						dest.mkdirs();
					}
					// �����ļ����µ����ݵ���һ���ļ���
					directory(
							(new File(fe.getDestPath()).listFiles()[0])
									.getAbsolutePath(),
							destPath);
					// ɾ���ļ���
					deleteFile(new File(fe.getDestPath()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
		} else {
			result = ProrocalUtils.serializeResult(2, "�û�δ��¼", null);
		}
		response.setContentType("json/application");
		response.setCharacterEncoding("utf-8");
		System.out.println(result);
		PrintWriter out = response.getWriter();
		out.write(result);
		out.flush();
		out.close();
		
		
	}
	
	/**
	 * @Method:fileUpload
	 * @Description:�ļ��ϴ�
	 * @param request �������
	 * @return �ϴ��ļ��ľ���·��
	 */
	public FileElement fileUpload(HttpServletRequest request,FileElement fe) {
		String account = "";
		account = (String) request.getSession().getAttribute("account");
		if (account==null) {
			System.out.println("�û�δ��¼�����ȵ�¼");
			return null;
		}
		fe.setAccount(account);
		Date date = new Date();
		fe.setUploadDate(new Timestamp(date.getTime()));
		List types = Arrays.asList("zip","rar");
		// �õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WebRoot/WEB-INF/uploadĿ¼��
		String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		// �ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
		String tempPath = this.getServletContext().getRealPath("/temp");
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			// ������ʱĿ¼
			tmpFile.mkdir();
		}
		try {
			// ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
			// 1������һ��DiskFileItemFactory����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�Сʱ���ͻ�����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼���С�
			factory.setSizeThreshold(1024 * 1024);// ���û������Ĵ�СΪ100KB�������ָ������ô�������Ĵ�СĬ����10KB
			// �����ϴ�ʱ���ɵ���ʱ�ļ��ı���Ŀ¼
			factory.setRepository(tmpFile);
			// 2������һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			// �����ļ��ϴ�����
//			upload.setProgressListener(new ProgressListener() {
//
//				@Override
//				public void update(long pBytesRead, long pContentLength,
//						int arg2) {
//					System.out.println("�ļ���СΪ��" + pContentLength + ",��ǰ�Ѵ���"
//							+ pBytesRead);
//					/**
//					 * �ļ���СΪ��14608,��ǰ�Ѵ���4096 �ļ���СΪ��14608,��ǰ�Ѵ���7367
//					 * �ļ���СΪ��14608,��ǰ�Ѵ���11419 �ļ���СΪ��14608,��ǰ�Ѵ���14608
//					 */
//
//				}
//			});

			// ����ϴ��ļ�������������
			upload.setHeaderEncoding("UTF-8");
			// 3���ж��ύ�����������Ƿ����ϴ���������
			if (!ServletFileUpload.isMultipartContent(request)) {
				// ���մ�ͳ��ʽ��ȡ����
				return null;
			}

			// �����ϴ������ļ��Ĵ�С�����ֵ��Ŀǰ������Ϊ1024*1024�ֽڣ�Ҳ����5MB
			upload.setFileSizeMax(5*1024 * 1024);
			// �����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ10MB
			upload.setSizeMax(1024 * 1024 * 10);
			// 4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// ���fileitem�з�װ������ͨ�����������
				if (item.isFormField()) {
					String name = item.getFieldName();
					// �����ͨ����������ݵ�������������
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
					fe.setFileName(value);
				} else {// ���fileitem�з�װ�����ϴ��ļ�
						// �õ��ϴ����ļ����ƣ�
					String filename = item.getName();
					//System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺
					// c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
					// �����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename = filename
							.substring(filename.lastIndexOf("\\") + 1);
					// �õ��ϴ��ļ�����չ��
					String fileExtName = filename.substring(filename
							.lastIndexOf(".") + 1);
					// �����Ҫ�����ϴ����ļ����ͣ���ô����ͨ���ļ�����չ�����ж��ϴ����ļ������Ƿ�Ϸ�
					System.out.println("�ϴ����ļ�����չ���ǣ�" + fileExtName);
					if(!types.contains(fileExtName)){
						System.out.println("���ļ����Ͳ�֧��");
						return null;
					}
					fe.setFileType(fileExtName);
					// ��ȡitem�е��ϴ��ļ���������
					InputStream in = item.getInputStream();
					// �õ��ļ����������
					String saveFilename = makeFileName(filename);
					fe.setId(UUID.randomUUID().toString());
					// �õ��ļ��ı���Ŀ¼
					String realSavePath = makePath(saveFilename, savePath);
					// ����һ���ļ������
//					FileOutputStream out = new FileOutputStream(realSavePath
//							+ "\\" + saveFilename);
					FileOutputStream out = new FileOutputStream(realSavePath
							+ File.separator + saveFilename);
					// ����һ��������
					byte buffer[] = new byte[1024];
					// �ж��������е������Ƿ��Ѿ�����ı�ʶ
					int len = 0;
					// ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					while ((len = in.read(buffer)) > 0) {
						// ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\"
						// + filename)����
						out.write(buffer, 0, len);
					}
					// �ر�������
					in.close();
					// �ر������
					out.close();
					// ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
					item.delete();
					//fe.setAbsulatePath(realSavePath + "\\" + saveFilename);
					fe.setAbsulatePath(realSavePath + File.separatorChar + saveFilename);
					String tempFilePath = this.getServletContext().getRealPath(File.separatorChar+fe.getFileType());
					File tempFile = new File(tempFilePath);
					if(!tempFile.exists()){
						//������ʱĿ¼
						tempFile.mkdirs();
					}
					fe.setDestPath(tempFilePath);
					return fe;
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			System.out.println("�����ļ��������ֵ������");
			return null;
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
			System.out.println("�ϴ��ļ����ܵĴ�С�������Ƶ����ֵ������");
			return null;
		} catch (Exception e) {
			System.out.println("�ļ��ϴ�ʧ�ܣ�");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Method: makeFileName
	 * @Description: �����ϴ��ļ����ļ������ļ����ԣ�uuid+"_"+�ļ���ԭʼ����
	 * @param filename
	 *            �ļ���ԭʼ����
	 * @return uuid+"_"+�ļ���ԭʼ����
	 */
	private String makeFileName(String filename) { // 2.jpg
		// Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		return UUID.randomUUID().toString() + "_" + filename;
	}

	/**
	 * Ϊ��ֹһ��Ŀ¼�������̫���ļ���Ҫʹ��hash�㷨��ɢ�洢
	 * 
	 * 
	 *
	 * @param filename
	 *            �ļ�����Ҫ�����ļ������ɴ洢Ŀ¼
	 * @param savePath
	 *            �ļ��洢·��
	 * @return �µĴ洢Ŀ¼
	 */
	private String makePath(String filename, String savePath) {
		// �õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ����������ڴ��еĵ�ַ
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf; // 0--15
		int dir2 = (hashcode & 0xf0) >> 4; // 0-15
		// �����µı���Ŀ¼
		String dir = savePath + File.separator + dir1 + File.separator + dir2; // upload\2\3
		// File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		// ���Ŀ¼������
		if (!file.exists()) {
			// ����Ŀ¼
			file.mkdirs();
		}
		return dir;
	}
	
	 /**
	       * ���Ƶ����ļ�
	       * @param oldPath Ҫ���Ƶ��ļ���
	       * @param newPath Ŀ���ļ���
	       */
	      public static void copyfile(String oldPath, String newPath) {
	          int hasRead = 0;
	          File oldFile = new File(oldPath);
	          if (oldFile.exists()) {
	              try {
	                  FileInputStream fis = new FileInputStream(oldFile);//����ԭ�ļ�
	                  FileOutputStream fos = new FileOutputStream(newPath);
	                  byte[] buffer = new byte[1024];
	                  while ((hasRead = fis.read(buffer)) != -1) {//���ļ�û�ж�����β
	                      fos.write(buffer, 0, hasRead);//д�ļ�
	                  }
	                  fos.flush();
	                  fos.close();
	                  fis.close();
	              } catch (Exception e) {
	                  System.out.println("���Ƶ����ļ���������");
	                  e.printStackTrace();
	              }
	          }
	      }
	  
	      /**
	       *
	       * @param oldPath Ҫ���Ƶ��ļ���·��
	       * @param newPath Ŀ���ļ���·��
	       */
	      public static void directory(String oldPath, String newPath) {
	         File f1 = new File(oldPath);
	         if(f1.isDirectory()){
	        	 File[] files = f1.listFiles();//listFiles�ܹ���ȡ��ǰ�ļ����µ������ļ����ļ���
	        	 for (int i = 0; i < files.length; i++) {
	        		 if (files[i].isDirectory()) {
	        			 File dirNew = new File(newPath + File.separator + files[i].getName());
	        			 dirNew.mkdir();//��Ŀ���ļ����д����ļ���
	        			 //�ݹ�
	        			 directory(oldPath + File.separator + files[i].getName(), newPath + File.separator + files[i].getName());
	        		 } else {
	        			 String filePath = newPath + File.separator + files[i].getName();
	        			 copyfile(files[i].getAbsolutePath(), filePath);
	        		 }
	        		 
	        	 }
	        	 
	         }else {
	        	 System.out.println("oldpath="+oldPath+"  "+"newPath="+newPath);
				copyfile(f1.getAbsolutePath(), newPath+File.separator+f1.getName());
			}
	      }
	      
	    //�ݹ�ɾ���ļ���  
	      public void deleteFile(File file) {  
	       if (file.exists()) {//�ж��ļ��Ƿ����  
	        if (file.isFile()) {//�ж��Ƿ����ļ�  
	         file.delete();//ɾ���ļ�   
	        } else if (file.isDirectory()) {//�����������һ��Ŀ¼  
	         File[] files = file.listFiles();//����Ŀ¼�����е��ļ� files[];  
	         for (int i = 0;i < files.length;i ++) {//����Ŀ¼�����е��ļ�  
	          this.deleteFile(files[i]);//��ÿ���ļ�������������е���  
	         }  
	         file.delete();//ɾ���ļ���  
	        }  
	       } else {  
	        System.out.println("��ɾ�����ļ�������");  
	       }  
	      }
	
}


