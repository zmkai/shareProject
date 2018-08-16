$(function() {
	$("#but").click(function () {
		var file = document.getElementById("file").files[0];
		var fn = document.getElementById("fN");
		alert("file="+file);
		alert("fn="+fn);
		if(file==null||fn==null||fn==""){
			alert("数据不完整");
		}
		
		$.ajax( {
			url: submitServlet,
		    type: "POST",  
		    async: false,  
		    cache: false, 
		    processData: false,// 告诉jQuery不要去处理发送的数据
		    contentType: false,// 告诉jQuery不要去设置Content-Type请求头
		    data: {
		    	ff:file,
		    	fn:fn
		    },
		    success: function(data){
		    	alert("success")
		        console.log(data);
		    },
		    error: function(err) {
		        alert(err);
		    }
			
		})
	})
})