<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加或修改成绩信息</title>
    
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
    <form action="<%=request.getContextPath() %>/addInfo2.do" method="post">

<h2 style="margin-left:850px;">添加&修改成绩信息</h2><br>

		<!-- <span style="margin-left:800px;">id：</span> -->
    	<input type="hidden" name="stuId" value="<%=request.getParameter("stuId") %>" style="margin-left:20px;">
		<input type="hidden" name="id" value="<%=request.getParameter("scid") %>" style="margin-left:20px;">

    	<span style="margin-left:800px;">学号：</span>
    	<input type="text" name="stuNumber" value="<%=request.getParameter("stuNumber") %>" readonly= "true" style="margin-left:20px;"> *不可修改<br><br>
		
		<span style="margin-left:800px;">姓名：</span>
		<input type="text" name="name" value="<%=request.getParameter("name") %>" readonly= "true" style="margin-left:20px;"> *不可修改<br><br>
		
		<span style="margin-left:800px;">科目：</span>
		<input type="text" name="subject" value="<%=request.getParameter("subject") %>" style="margin-left:20px;"><br><br>	
		
		<span style="margin-left:800px;">分数：</span>
		<input type="text" name="mark" value="<%=request.getParameter("mark") %>" style="margin-left:20px;"><br><br>	
		
    <input type="submit" value="提交" style="margin-left:900px;">
<% String name = ""; try{
	 name = request.getAttribute("msg").toString();
}catch(Exception e){
	
}finally{
	
} %>
<h2 style="margin-left:850px; color:red"><%=name%></h2>
      </form>
  </body>
</html>
