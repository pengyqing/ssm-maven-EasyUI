<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加宿舍信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form action="<%=request.getContextPath() %>/addInfo1.do" method="post">

<h2 style="margin-left:850px;">添加宿舍信息</h2><br>

    	<span style="margin-left:800px;">院系：</span>
    	<input type="text" name="department" style="margin-left:20px;"><br><br>
		
		<span style="margin-left:800px;">楼栋：</span>
		<input type="text" name="build" style="margin-left:20px;"><br><br>
		
		<span style="margin-left:800px;">编号：</span>
		<input type="text" name="dormNum" style="margin-left:20px;"><br><br>	
		
    <input type="submit" value="添加" style="margin-left:900px;">
<% String name = ""; try{
	 name = request.getAttribute("msg").toString();
}catch(Exception e){
	
}finally{
	
} %>
<h2 style="margin-left:850px; color:red"><%=name%></h2>
      </form>
  </body>
</html>
