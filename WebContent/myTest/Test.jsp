<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试界面</title>
</head>
<body>
<% request.getSession().setAttribute("account", "195140040"); %>
<form action="${pageContext.request.contextPath }/SubmitServlet"  enctype="multipart/form-data" method="post">
	上传用户：<input type="text" name="username"><br/>
	上传文件：<input type="file" name="file"/><br/>
	<input type="submit" value="提交"/>
</form>

</body>
</html>