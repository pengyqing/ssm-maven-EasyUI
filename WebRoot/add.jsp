<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加学生基本信息</title>
    
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
    <form action="<%=request.getContextPath() %>/addInfo.do" method="post">

		<h2 style="margin-left:850px;">添加学生信息</h2><br>

    	<span style="margin-left:800px;">学      号：</span>
    	<input type="text" name="stuNumber" style="margin-left:20px;"><br><br>
		
		<span style="margin-left:800px;">姓      名：</span>
		<input type="text" name="name" style="margin-left:20px;"><br><br>
		
		<span style="margin-left:800px;">性      别：</span>
			<select name="sex" style="margin-left:20px; width:165px;height:25px;" >
      			<option value="男">男</option>
      			<option value="女">女</option>
      			<option value="gay">gay</option>
      			<option value="未知">未知</option>
  			</select><br><br>
		
		<span style="margin-left:800px;">院       系：</span>
		<input type="text" name="department" style="margin-left:20px;"><br><br>
		
		<span style="margin-left:800px;">年       级：</span>
		<input type="text" name="grade" style="margin-left:20px;"><br><br>
		
		<span style="margin-left:800px;">班级号：</span>
		<input type="text" name="classesCard" style="margin-left:20px;"><br><br>
		
		<span style="margin-left:800px;">宿舍楼栋：</span>
		<input type="text" name="build" style="margin-left:5px;"><br><br>
		
		<span style="margin-left:800px;">宿舍号：</span>
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
