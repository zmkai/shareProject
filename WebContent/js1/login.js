$(function() {
	$("#login").click(function () {
		debugger;
		var account = $("#account").val();
		var password = $("#password").val();
		alert("account="+account);
		alert("password="+password);
		if(account==null||account==""||password==null||password==""){
			alert("密码或账号不能为空");
			return ;
		}
		$.ajax({

			   type: "POST",

			   url: "LoginServlet",

			   data: {
				   account:account,
				   password:password
			   },
			   dataType:"json",
			   success: function(data){
				   if(data.code==0){
					   alert("登录成功");
					   alert(data.msg);
					   alert("即将跳转");
					   location.href="submit.html";
				   }else {
					alert(data.msg);
				   }
			   },
			   Error:function(a,b,c){
				   alert(a,b,c);
			   }
			});
	})
})