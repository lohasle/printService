打印服务接受符合xml规范的HTML文件或者远程请求


使用方法：

	请求地址   ~/printService/doPrint
	
	参数	
			printurl 当前页面请求地址
			distagid 需要排除的元素ID 指定后 打印将不显示
			ns		 是否自动排除脚本
			pageSize 纸张大小  (预留)
			callbackparam  服务器接收回调参数
	
	返回   msg={"msg",~};
	
	
	
	
例如： jquery中
$(document).ready(function() {
	/*
		打印请求
		
		地址printServiceUrl 配置在项目中取得
		
		
		参数
		printurl 当前页面请求地址
		distagid 需要排除的元素ID 指定后 打印将不显示
		ns		 是否自动排除脚本
		pageSize 纸张大小  (预留)
	
		callbackparam  服务器接收回调参数
	*/
	$("#doprintBtn").click(function(){
		$("#doprintBtn").html("正在打印...");
		$("#doprintBtn").attr("disabled", true);
		$.ajax({
			dataType: "jsonp",
			url:<%=printServiceUrl%>,
			data:{"printurl":<%=reqQry%>,"distagid":"doprint","ns":"y"},
			jsonp: "callbackparam",
			success:function(msg){
				if(msg){
					if(msg.msg=='success'){
						alert("打印成功");
						$("#doprintBtn").html("点此打印");
					}else{
						alert(msg);
					}
				}
				$("#doprintBtn").html("点此打印...");
				$("#doprintBtn").attr("disabled", false);
			}
		});
	});
});


-----------------------------------------------wkhtmltoPDf 方式

请求地址   ~/printService/doPrint
	
	参数	
			参数
			printurl 当前页面请求地址
			islandscape  是否横版   y 为横版
			quality		质量  h 高 l 低	
			pageSize 纸张大小  
			callbackparam  服务器接收回调参数
	
	返回   msg={"msg",~};
	
	
/*
			打印请求
			
			地址printServiceUrl 配置在项目中取得
			
			参数
			printurl 当前页面请求地址
			islandscape  是否横版   y 为横版
			quality		质量  h 高 l 低	
			pageSize 纸张大小  
			callbackparam  服务器接收回调参数
		*/
	
			$.ajax({
				dataType: "jsonp",
				url:"[打印服务器地址（test 192.168.123.23:8080/printService/doPrint/）]",
				data:{"printurl":"www.baidu.com","pagesize":"A4","islandscape":"y","quality":"h"},
				jsonp: "callbackparam",
				success:function(msg){
					if(msg){
						if(msg.msg=='success'){
							alert("打印成功");
							$("#doprintBtn").html("点此打印");
						}else{
							alert(msg.msg);
						}
					}
				
				}
			});
	