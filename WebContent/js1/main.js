$(function () {
	$("#submit").click(function (){
		alert("触发");
		var file = $("#file").val();
		alert("为file赋值");
		alert(file);
		if(file==null){
			alert("上传文件不能为空");
			return ;
		}else {
			alert("ajax");
			$.ajax({
				
				  type: "POST",
				  url: "mainServlet",
				  data: {
					 file:file
				   },
				   dataType:"json",
				  success: function(data){
					  	if(data.code==0){
					  		alert("上传成功");
					  	}else {
							alert("上传失败");
						}
					  }
			})
		}
		
	})
	
})