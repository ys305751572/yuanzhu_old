<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/common/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
.commonTab{
     line-height: 23px;
     padding: 5px;
}
.commonTab th{
     font-size:12px;
     font-weight: bold;
     border: 0px solid gray;
}
.commonTab td{
    font-size:14px;
    border: 0px solid gray;
}
.commonTab input,select{
	border: 1px solid #ddd;
	
}
label{ 
	display: block; 
	width: autoau; 
	text-align: center; 
	text-justify: distribute-all-lines; 
}
</style>
</head>
<body>
	<div style="margin:2px;border: 1px solid #DDD;"  >
		<table id="menuConfigTab"></table>
		<div id="queryDiv" >
			<table id="queryTab" class="commonTab">
				<tr>
					<th>菜单名称:</td>
					<td><input type="text"  id="query_menuName" /></td>
					<th>菜单类型:</td>
					<td><select id="query_menuType">
						<option value="00">全部</option>
						<option value="01">外部链接</option>
						<option value="02">友情链接</option>
					</select></td>
					<th>创建时间:</td>
					<td><input class="easyui-datebox" type="text" id="createTimeBefore"/>-<input class="easyui-datebox" type="text" id="createTimeEnd"/></td>
					<td><a href="##" class="easyui-linkbutton" id="queryBtn">查询</a></td>
				</tr>
			</table>
		</div>
		<div id="other_configWin" style="margin-top: 10px;">
			<form action="" id="configForm">
				<table class="commonTab" id="configTab">
					<tr>
						<th>菜单名称:</th>
						<td><input type="text" id="cofName" placeholder="菜单名称"/></td>
						<th>菜单链接:</th>
						<td><input type="text" id="cofUrl" style="width: 200px;" placeholder="菜单链接"/></td>
						<th>菜单类型:</th>
						<td>
							<select id="cofType">
								<option></option>
								<option value="01">外部链接</option>
								<option value="02">友情链接</option>
							</select>
						</td>
						<th>菜单排序:</th>
						<td><input id="cofSort"  style="width:110px;" ></td>
					</tr>
					<tr >
						<td colspan="8" align="left"><a href="###" id="addSubConfigBtn" class="easyui-linkbutton" >添加子链接</a></td>
						<td><input type="hidden" id="id"/></td>
					</tr>
					<tr >
						<td colspan="8">
							<table id="subConfigs" >
								
							</table>
						</td>
					</tr>
					<tr align="center" >
						<td colspan="8"><a href="##" class="easyui-linkbutton" id="saveBtn">提交</a>&nbsp;&nbsp;<a href="##" id="clearBtn" class="easyui-linkbutton">清空</a></td>
					</tr>
				</table>
			</form>
		</div>
    </div> 
</body>
<script type="text/javascript">
var grid;
var win;
var msgTitle='提示信息';
var msgType='info';
$(function(){
	win= $('#other_configWin').window({
		title:"增加其他菜单配置",
		algin:'center', 
		closed: true, 
		width:800,
		heeight:400,
		top:100,
		modal: true,  
		cache: false,  
		minimizable:false,  
		maximizable:false,  
		collapsible:false,  
		shadow: true  
  });
$("#menuConfigTab").datagrid({
     title : '其他菜单设置',
	 method : 'post',
	 url:'findWithpage',
     fit : false,
     fitColumns : true,
     singleSelect : true,
     pagination : true,
     rownumbers : true,
     pageSize : 10,
     pageList : [ 10, 20, 30 ],
     remoteSort : false,
     idField : 'id',
     loadMsg : '数据装载中......',
	frozenColumns : [ [ {
		field : 'ck',
		checkbox : true
	} ] ],
	columns : [ [
				{
					field:'cofName',
					title:'菜单名称',
					width:120,
					height : 'auto',
					align : 'center',
					rowspan : 2
				},{
					field : 'cofType',
					title : '菜单类型',
					width : 200,
					height : 'auto',
					align : 'center',
					rowspan : 2,
					formatter:function(value,rowData,index){
						var val;
						if(value=='01'){
							val='外部链接';
						}else if(value=='02'){
							val='友情链接';
						}
						return val;
					}
				},{
					field : 'creationTime',
					title : '创建时间',
					align : 'center',
					width : 130,
					height : 50
				},{
					field:'id',
					title:'操作',
					align : 'center',
					width:180,
					formatter:function(value, row, index){
	                    return "<a href=\"###\" onclick=\"modify_config('" + value + "')\">修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"###\" onclick=\"delete_config('" + value + "')\">删除</a>";
					}

				}] ],
				pagination : true,
				rownumbers : true,
				singleSelect : false,
				toolbar : [
						{
							text : '新增菜单项',
							iconCls : 'icon-add',
							handler : function() {
								$("form").each(function() {   
									   this.reset();   
								});
								$("#subConfigs").empty();
								win.window('open');
							}
		 				}
				]
		}
);
$('#queryTab').prependTo('.datagrid-toolbar');
//设置分页控件  
var p = $('#menuConfigTab').datagrid('getPager');
$(p).pagination({
	pageSize : 10,//每页显示的记录条数，默认为10  
	pageList : [ 5, 10, 15 ],//可以设置每页记录条数的列表  
	beforePageText : '第',//页数文本框前显示的汉字  
	afterPageText : '页    共 {pages} 页',
	displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	queryParams : {
	}
});

$("#clearBtn").click(function(){
	$("form").each(function() {   
		   this.reset();   
	});
});
$("#saveBtn").click(function(){
	var config=new Object();
	var configName=$("#cofName").val();
	if(configName!="" && configName!=null && configName!=undefined){
		config.cofName=configName;
	}else{
		$("#cofName").attr('placeholder','请输入菜单配置名称');
		$("#cofName").focus();
		return;
	}
	var configUri=$("#cofUrl").val();
	if(configUri!="" && configUri!=null && configUri!=undefined){
		if(checkUrl(configUri)){
			config.cofUrl=configUri;
		}else{
			$("#cofUrl").val('');
			$("#cofUrl").attr('placeholder','您输入的地址符合法,请重新输入!');
			$("#cofUrl").focus();
			return;
		}
	}else{
		   $("#cofUrl").attr('placeholder','请输入菜单链接地址!');
		   $("#cofUrl").focus();
		   return ;
	}
	var configType=$("#cofType").val();
	if(configType!="" && configType!=null && configType!=undefined){
		config.cofType=configType;
	}else{
		$("#cofType").focus();
		$("#cofType").append('<option selected="selected">请选择类型</option>');
		return;
	}
	var configSort=$("#cofSort").val();
	if(configSort!="" && configSort!=null &&configSort!=undefined){
		if(checkNumber(configSort)){
			config.cofSort=configSort;
		}else{
			$("#cofSort").val('');
			$("#cofSort").attr('placeholder','请输入1-100正整数');
			return;
		}
	}else{
		$("#cofSort").focus();
		$("#cofSort").val('');
		$("#cofSort").attr('placeholder','请输入1-100正整数');
		return;
	}
	var id=$("#id").val();
	if(id!="" && id!=null && id!=undefined){
		config.id=id;
	}
	var subConfigs=[];
	var isOk=true;
	$(".config").each(
		function(){
			 var subConfig={};
			 
			 var subSort=$(this).find("[name='subConfigSort']").val();
			 if(subSort!="" && subSort!=null && subSort!=undefined){
				 if(checkNumber(subSort)){
					 subConfig['cofsonSort']=subSort;
				 }else{
					 $($(this).find("[name='subConfigSort']")).focus();
					 $($(this).find("[name='subConfigSort']")).val('');
					 $($(this).find("[name='subConfigSort']")).attr('placeholder','请输入1-100正整数');
					 isOk=false;
				 }
			 }else{
				 $($(this).find("[name='subConfigSort']")).focus();
				 $($(this).find("[name='subConfigSort']")).attr('placeholder','请输入菜单出现次序');
				 isOk=false;
			 }
			 var subUrl=$(this).find("[name='subConfigUrl']").val();
			 if(subUrl!="" && subUrl!=null && subUrl!=undefined){
				 if(checkUrl(subUrl)){
					    subConfig['cofsonAddr']=subUrl;
				 }else{
					 	$($(this).find("[name='subConfigUrl']")).focus();
					    $($(this).find("[name='subConfigUrl']")).val('');
					    $($(this).find("[name='subConfigUrl']")).attr('placeholder','您输入的地址符合法,请重新输入!');
					    isOk=false;
				 }
			 }else{
				 $($(this).find("[name='subConfigUrl']")).focus();
				 $($(this).find("[name='subConfigUrl']")).attr('placeholder','请输入菜单链接地址');
				 isOk=false;
			 }
			 var subName=$(this).find("[name='subConfigName']").val();
			 if(subName!="" && subName!=null && subName!=undefined){
				 subConfig['cofsonName']=subName;
			 }else{
				 $($(this).find("[name='subConfigName']")).focus();
				 $($(this).find("[name='subConfigName']")).attr('placeholder','请输入菜单配置名称');
				 isOk=false;
			 }
			 subConfigs.push(subConfig);
		}		
	);
	config.configures=subConfigs;
	if(isOk){
		$.ajax(
				{
					type : "post",
					url : "cms_otherMenuSaveOrUpdate",
					data:{
						'configureStr':$.toJSON(config)
					},
					dataType : "json",
					success : function(data) {
						if(data.opRs){
							$.messager.alert(msgTitle,data.opRs, msgType);
						}else{
							$.messager.alert(msgTitle,'系统错误,请联系管理员', msgType);
						}
						win.window('close');
						$('#menuConfigTab').datagrid('reload');
					}	
					
				}		
			);
	}
});
$("#addSubConfigBtn").click(function(){
	var $table = $("#configTab tr");  
    var len = $table.length;
    var idStr='sub'+(len+1);
	var str='<tr id='+idStr+' class="config">'
		+'<th>菜单名称:</th>'
		+'<td><input type="text" name="subConfigName" placeholder="菜单名称"/></td>'
		+'<th>菜单链接:</th>'
		+'<td><input type="text" name="subConfigUrl" style="width: 200px;" placeholder="菜单链接"/></td>'
		+'<th>菜单排序:</th>'
		+'<td><input type="text" name="subConfigSort" style="width:200px;"  placeholder="菜单链接"/></td>'
		+'<td align="center" colspan="2"><a href="#" onclick="deleteConfig('+idStr+')" class="easyui-linkbutton">删除</a></td>'
		+'</tr>';
	$("#subConfigs").append(str);
});
$('#createTimeBefore,#createTimeEnd').datebox({  
	formatter: function(date){ return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate(); }
});
$("#queryBtn").click(function (){
	$('#menuConfigTab').datagrid(
			'load',
			{
				cofName : $("#query_menuName").val(),
				cofType:$("#query_menuType").val(),
				createTimeBefore:$('#createTimeBefore').datebox('getValue'),
				createTimeEnd:$('#createTimeEnd').datebox('getValue')
			});
	
});
});
function deleteConfig(obj){
	$(obj).remove();
}
function checkUrl(val){
	//下面的代码中应用了转义字符"\"输出一个字符"/"
	var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
	var objExp=new RegExp(Expression);
	if(objExp.test(val)==true){
	   return true;
	}else{
	   return false;
	}
}
function modify_config(value){
	$.ajax({
			type : "post",
			url : "cms_otherMenuUpdateinit",
			data:{
				'key':value
			},
			dataType : "json",
			success : function(data) {
				for(var i in data){
					$("#"+i).val(data[i]);
				}
				$("#subConfigs").empty();
				var $table = $("#configTab tr");  
			    var len = $table.length;
			    var idStr='sub'+(len+1);
			    for(var i=0;i<data.configures.length;i++){
			    	var config=data.configures[i];
			    	var str='<tr id='+idStr+' class="config">'
					+'<th>菜单名称:</th>'
					+'<td><input type="text" name="subConfigName" placeholder="菜单名称" value="'+config['cofsonName']+'"/></td>'
					+'<th>菜单链接:</th>'
					+'<td><input type="text" name="subConfigUrl" style="width: 200px;" placeholder="菜单链接" value="'+config['cofsonAddr']+'"/></td>'
					+'<th>菜单排序</th>'
					+'<td><input type="text" name="subConfigSort" style="width:200px;"  value="'+config['cofsonSort']+'" /></td>'
					+'<td align="center" colspan="2"><a href="#" onclick="deleteConfig('+idStr+')" class="easyui-linkbutton">删除</a></td>'
					+'</tr>';
					$("#subConfigs").append(str);
			    }
				win.window('open');
			}	
	});
	win.window('open');
}
function delete_config(value){
	$.messager.confirm(msgTitle,'您确定要删除?',
			function(button_rmv) {
				if (button_rmv) {
					$.ajax({
						type : "post",
						url : "cms_otherMenuDelete",
						data : {
							"key" : value
						},
						dataType : "json",
						success : function(data) {
							msgType = 'info';
							$.messager.alert(msgTitle,data.opRs,msgType);
							$('#menuConfigTab').datagrid('reload');
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							$.messager.alert(msgTitle, '操作异常出错,请检查网络是否正常!');
						}
				});
			}
		});
}
function checkNumber(val){
	var reg=/[^(\d)]/g;
	if(!reg.test(val)){
		return true;
	}else{
		return false;
	}
}
</script>
</html>