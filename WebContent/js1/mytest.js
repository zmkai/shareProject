$(function(){
	$("#s").click(function() {
		debugger;
		var filename = document.getElementById("fileName").value;
		alert(filename);
		var file = document.getElementById("file").files[0];
		alert(file);
		$.ajax({
			url:"submitServlet",
			type:"POST",
			data:{
				filename:filename,
				file:file
			},
			success:function(data){
				alert(data);
				alert("上传成功");
			},
			error:function(data){
				alert("data");
				alert("失败");
			}
		});
		
		
	});
	
});

