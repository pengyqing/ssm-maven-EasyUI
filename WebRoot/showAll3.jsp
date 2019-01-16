
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
    <title>学生成绩信息管理</title>
	
	<link href="<%=basePath%>/js/jquery-easyui-1.2.6/themes/default/easyui.css" rel="stylesheet" type="text/css" />  
    <link href="<%=basePath%>/js/jquery-easyui-1.2.6/themes/icon.css" rel="stylesheet" type="text/css" />

    <script src="<%=basePath%>/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/jquery-easyui-1.2.6/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>




<body >
<!--查询条件-->
<div region="north" border="false" style="height:100px; overflow-y: hidden; background-color:#F9F6F3;" >
	
	<h2>
	<a href="showAll.jsp" style="margin-left:800px;">学生基本信息管理</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="showAll2.jsp">学生宿舍信息管理</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="showAll3.jsp">学生成绩信息管理</a></h2>
	
	<form id="queryForm" method="post">
	  <table border="0" width="99%" align="center" cellpadding="5" cellspacing="0" style="font-size:12px; margin-top:5px;">
        <tr>
          <td width="6%" align="right">
          <%
	String today=new SimpleDateFormat("yyyy年-MM月-dd日").format(new Date());
%>
<%= today %>
</td>
		  <td width="6%" align="right">科目：</td>
          <td width="17%" align="left"><input name="subject" type="text" style="width:200px;" /></td>
		   <td width="6%" align="right">学号：</td>
          <td width="17%" align="left"><input name="stuNumber" type="text" style="width:150px;" /></td>
          <td width="8%"><a href="javascript:void(0);searchData();">
		     查询</a></td>
          <td width="10%"><a href="javascript:void(0);clearForm();">
			清除
			</a></td>
			
			<td width="4%" style="width:20px;"><a href="javascript:void(0);updateScore();">
		     添加或修改成绩信息</a></td>
         
			
        </tr>
      </table>
	</form>
</div>
<!--查询条件end-->

<!--表格-->
<div region="center" border="false"  >
	<div id="grid" ></div>
</div> 
<!--表格end-->



<!--导入excel-->
<div id="dlg_upload" class="easyui-dialog"  style="width:400px;
		padding:10px 20px;background: #fafafa;" closed="true" buttons="#dlg-buttons">
			<form id="uploadForm"  method='post' enctype="multipart/form-data">  
					文件(xls)：&nbsp;<input type="file" name="importFileName" id="importFileName"/>  
			</form>
			<div style="color:#FF0000">提示：请先下载excel模板文件，然后再导入</div> 
			
</div>
<div id="dlg-buttons">  
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="upload();">确定</a>  
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" 
		onclick="javascript:$('#dlg_upload').dialog('close')">关闭</a>  
</div>  
<!--导入excel end-->




<script type="text/javascript">  

var grid;

$(function () {    
       //var strInfo;
     // alert('<%=basePath%>getAll.do');
        grid = $('#grid').datagrid({		
        title: '成绩信息',
        iconCls: 'icon-save',
        methord: 'post',
        url: '<%=basePath%>getAll2.do',
       // url:'http://localhost:8080/ssm/getAll.do',
		 //When request remote data, sending additional parameters also
        pageSize: 10,
		pageList: [10,20,30,40],        
        columns: [[
					{ field: 'id', title: '编号' ,width: 100,align:'left',sortable:true},
					{ field: 'stuNumber', title: '学号' ,width: 110,align:'left',sortable:true},
					{ field: 'name', title: '姓名' ,width: 100,align:'left',sortable:true},
					{ field: 'department', title: '院系' ,width: 110,align:'left',sortable:true},
					{ field: 'grade', title: '年级' ,width: 100,align:'left',sortable:true},
					{ field: 'classesCard', title: '班级' ,width: 100,align:'left',sortable:true}	,
					{ field: 'subject', title: '科目' ,width: 100,align:'left',sortable:true},
					{ field: 'mark', title: '分数' ,width: 100,align:'left',sortable:true}	,
					{ field: 'createTime', title: '创建时间' ,width: 150,align:'left',sortable:true},
					{ field: 'scid', title: '成绩id' ,width: 150,align:'left',hidden:'true',sortable:true}
					
				]],
        fit:true,
		striped:true, //奇偶行不同颜色
        pagination: true,
        rownumbers: true,
     //   fitColumns: true,
        singleSelect: false,
		sortName:"id" ,
		sortOrder:"asc" ,
		
		toolbar: [
			{
	            text: '导入',
			    iconCls: 'icon-excel',
			    handler: importFromExcel
			 },'-',{
	            text: '导出',
			    iconCls: 'icon-excel',
			    handler: exportToExcel
			 },'-',{
	            text: '文件上传',
			    iconCls: 'icon-upload',
			    handler: upload
			 },'-',{
	            text: '删除',
			    iconCls: 'icon-remove',
			    handler: deleterow
			}],
		
		onLoadSuccess:function(){
				grid.datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			},
			
		/*Fires when user sort a column, the parameters contains:
			sort: the sort column field name
			order: the sort column order
		*/
		onSortColumn:function(sort,order){
			var queryParams = $('#grid').datagrid('options').queryParams;  
    		queryParams.sort = sort;  
    		queryParams.order = order;  
    		$("#grid").datagrid('reload');
		},
		onLoadSuccess:function(data){    
        }    
		

    });
    $('body').layout();
});

var url;


//删除
function deleterow(){
	var rows = grid.datagrid('getSelections');
    var num = rows.length;
    if (num == 0) {
        Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
        return;
    }
	$.messager.confirm('提示','确定要删除吗?',function(result){
		if (result){
			var ps = "";
			$.each(rows,function(i,n){
				if(i==0) 
					ps += "?id="+n.scid;
				else
					ps += "&id="+n.scid;
				if(n.scid == null){
					$.messager.alert('提示',"此同学暂无成绩，无法删除",'info');
					}
			});
			
			
			$.post('<%=basePath%>/del2.do'+ps,function(data){
				grid.datagrid('reload'); 
				$.messager.alert('提示',data.msg,'info');
			});
		}
	});
}

 //上传excel，打开对话框，设置表单提交地址
function importFromExcel() {
	$.messager.alert('提示',"开发中",'info');
}


//导出到excel   
function exportToExcel()
{
	$.messager.alert('提示',"开发中",'info');
}


function updateScore(){
	var rows = grid.datagrid('getSelections');
    var num = rows.length;
    if (num == 0) {
        Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
        return;
    }

    var stuNumber="";
    var name="";
    var subject="";
    var mark="";
    var scid="";
    $.each(rows,function(i,n){
        
    	if(n.scid != null){
    		scid = n.scid;
        }
        if(n.stuNumber != null){
        	stuNumber = n.stuNumber;
        }
        if(n.name != null){
        	name = n.name;
        }
        if(n.subject != null){
        	subject = n.subject;
        }
        if(n.mark != null){
        	mark = n.mark;
        }
        
		if(i==0) {
			window.location.href="add3.jsp?stuId="+n.id+"&scid="+scid+"&stuNumber="+stuNumber+"&name="+name
			+"&subject="+subject+"&mark="+mark;
			}
		
	});
    
}


//表格查询
function searchData(){
	var params = grid.datagrid('options').queryParams; //先取得 datagrid 的查询参数
	var fields =$('#queryForm').serializeArray(); //自动序列化表单元素为JSON对象
	$.each( fields, function(i, field){
		params[field.name] = field.value; //设置查询参数
	}); 
	grid.datagrid('reload'); //设置好查询参数 reload 一下就可以了
}

//清空查询条件
function clearForm(){
	$('#queryForm').form('clear');
	searchData();
}






function Msgshow(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        showType: 'show'
    });
}
function Msgslide(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        timeout: 3000,
        showType: 'slide'
    });
}
function Msgfade(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        timeout: 3000,
        showType: 'fade'
    });
}








//excel文件上传操作
function upload() {
	$.messager.alert('提示',"开发中",'info');
}

//文件后缀名检查，必须是xls文件
function checkFile(){   
	
	var importFileName = document.getElementById("importFileName").value;
	if(importFileName==""){
		window.alert("文件名不能为空");
		return false;
	}
	if(importFileName.substring(importFileName.length-3,importFileName.length)!='xls'){
		window.alert("文件必须是xls格式");
		return false;
	}

	return true;
}



</script>  



</body>
</html>






 