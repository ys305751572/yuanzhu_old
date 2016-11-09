<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资讯管理</title>
<script type="text/javascript">
	$(function(){
		//加载数据到表格
		initGrid();
		//表格分页
		gridpage();
		//初始化新增div层
		initAddDiv();
		//初始化修改div层
		initEditDiv();
		//资讯下拉框
		add_combo();
		edit_combo();
		});

		function initGrid(){
			$('#info_table').datagrid({
				url : 'pagelist',
				method : 'post',
				fit : true,
				title : '资讯管理',
				fitColumns : true,
				singleSelect : true,
				pagination : true,
				rownumbers : true,
				pageSize : 10,
				idField : 'id',
				pageList : [ 5 ,10, 20 ,30],
				autoRowHeight : false,
				loadMsg : '数据正在装载......',
				sortOrder:'asc',
				remoteSort: false,
				columns : [ [
								{
									field : 'infoClassid',
									title : '分类',
									width : 50,
									sortable:true,       
									formatter:function(value,row,index){
										if(row.infoClassid==1){
											return "公司动态";
										}
										return "业内新闻";
									}   
								},
								{
									field : 'infoTitle',
									title : '资讯标题',
									width : 50,
									sortable:true
								},
								{
									field : 'infoContent',
									title : '资讯内容',
									width : 100,
									sortable:true
								},
								/* ,
								 {
									title:'资讯图片',
									field:'infoImgs',
									//width:200,
									align:'center',        
									formatter:function(value,row,index){
										var img="<img src='"+row.infoImgs+"' />";
										return img;
									}   
								} */
								{
									field : 'infoPublishtime',
									title : '发布时间',
									width : 50,
									sortable:true
									/* ,
									formatter:function(value,row,index){
										var date=new Date(parseInt(row.infoPublishtime));
									    var setDate=date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日";										
										return setDate;
									} */
								},{
									field : 'id',
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
										
					                    return "<a href=\"###\" onclick=\"info_edit('" + index + "')\">修改</a>&nbsp;&nbsp;<a href=\"###\" onclick=\"info_remove('" + value + "')\">删除</a>";
					                }
								}]],
								toolbar : '#info_toolbar'
								
	})}
    //资讯下拉框
	function edit_combo(){
		
        $("#edit_classid").combobox({
            valueField : 'value',
            textField : 'label',
            data : [{
                label : '公司动态',
                value : '1'
            },{
                label : '业内新闻',
                value : '2'
            }],
            panelHeight : 'auto',
            required : false,
            multiple : false
        });
	}
	
	function add_combo(){
		
        $("#classid").combobox({
            valueField : 'value',
            textField : 'label',
            data : [{
                label : '公司动态',
                value : '1'
            },{
                label : '业内新闻',
                value : '2'
            }],
            panelHeight : 'auto',
            required : false,
            multiple : false
        });
	}
	//按标题搜索
	function loadSearch(){
		var title=$('#infoTitle').val();
	    $('#info_table').datagrid('load',{
	    	"infoTitle" : title
	    });	
	}
	//清空查询条件
	function clearSearch(){
		$('#infoTitle').val("");
	}
	//分页设置
	function gridpage(){
		$('#info_table').datagrid('getPager').pagination({
			displayMsg : '当前显示从{from}到{to}共{total}记录',
			beforePageText : '第',
			afterPageText : '页 共 {pages} 页',
			onBeforeRefresh : function(pageNumber, pageSize) {
				
            	$('#info_table').datagrid('load');
			}
		});
	}
	//删除资讯数据
	function info_remove(id){
		
		 //var data = $('#info_table').datagrid("getSelections");
		// if(data[0]!=null){
			 if(window.confirm('是否删除？')){
			 $.ajax({
					type : "post",
					dataType : "json",
					url : "del?id=" + id,
					success : function(msg){
						if (msg.succ) {
							$('#info_table').datagrid('reload');
							$.messager.alert('提示', msg.message);
						}else{
							$.messager.alert('提示', msg.message);
						}
					}
				});
			 }
			 //else{
			//	 $.messager.alert('提示','请稍后再试!');
			 //}
		// }else{
		//	 $.messager.alert('提示','请选择要删除的数据');
		// }
		
	}
	function info_add_open(){
		$('#add_div').dialog('open');
	}
	//添加资讯数据
	function info_add(){
		var id=$('#id').val();
		var classid = $("#classid").combobox('getValue');
		var title=$('#title').val();
		var content=$('#content').val();
		var infoImgs=$("#image").val();
		//var createtime=$('#createtime').val();
		$.ajax({
			type : "post",
			secureuri : false,
			url : 'add',
			dataType : 'json',
			data : {
				//"id" : id,
				"infoClassid" : classid,
				"infoTitle" : title,
				"infoContent" : content,
				"infoImgs" : infoImgs
				//,"infoPublishtime" : createtime
			},
			success : function(data,status){
				if(data.succ){
					$.messager.alert('提示',msg.message);
					$('#add_div').dialog('close');
					//resetAddData();
					$('#info_table').datagrid('reload');
				}else{
					$.messager.alert('提示',data.message);
				}
			}
		});
	}
	//上传资讯图片
	function uploadImgs(){
		$.ajaxFileUpload({
			type : "post",
			secureuri : false,
			frameId : 'temp_upload_frame',
			fileElementId : [ 'image'],
			url : 'uploadImg',
			dataType : 'json',
			success : function(data){
				alert(data.newFileName);
				if(data){
					$("#image").val(data.newFileName);
					$.messager.alert('提示',"上传成功");
				}else{
					$.messager.alert('提示',"上传失败");
				}
			}
		});
		
	}
	function info_edit(index){
			//var data = $('#info_table').datagrid("getSelections");
			//if(data[0]!=null){
				var dataRow = $('#info_table').datagrid("getRows");
			    var row = dataRow[index];
			    //alert(row.id);
				$('#edit_div').dialog('open');
				
				$('#edit_id').val(row.id);
				//$('#edit_classid').val(row.infoClassid);
				$('#edit_title').val(row.infoTitle);
				$('#edit_image').val(row.infoImgs);
				$('#edit_content').val(row.infoContent);
				$('#edit_createtime').val(row.infoPublishtime);
			//}else{
			//	$.messager.alert('提示','请选则要修改的数据！');
			//}
			
	}
	//修改资讯数据
	function info_update(){
		var id=$('#edit_id').val();
		var classid = $("#edit_classid").combobox('getValue');
		var title=$('#edit_title').val();
		var content=$('#edit_content').val();
		var infoImg=$('#edit_image').val();
		var createtime=$('#edit_createtime').val();
		
		$.ajax({
			type : "post",
			url : 'update',
			dataType : 'json',
			data : {
				"id" : id,
				"infoClassid" : classid,
				"infoTitle" : title,
				"infoImgs" : infoImg,
				"infoContent" : content,
				"infoPublishtime" : createtime
			},
			success : function(msg){
				if(msg.succ){
					$.messager.alert('提示',msg.message);
					exitEditData();
					$('#info_table').datagrid('reload');
				}else{
					$.messager.alert('提示',msg.message);
				}
			}
		});
		
		
	}
	
	function resetAddData(){
		$('#id').val("");
		$('#classid').val("");
		$('#title').val("");
		$('#image').val("");
		$('#content').val("");
		$('#createtime').val("");
	}
	function exitEditData(){
		$('#edit_id').val("");
		$('#edit_classid').val("");
		$('#edit_title').val("");
		$('#edit_image').val("");
		$('#edit_content').val("");
		$('#edit_createtime').val("");
		
		$('#edit_div').dialog('close');
	}
	 
	function initAddDiv(){
		$('#add_div').dialog({
		    title: '添加资讯',
		    width: 300,
		    height: 280,
		    closed: true,
		    cache: false,
		    modal: true,
		    closable:true,
		    resizable:false
		});
	}
	function initEditDiv(){
		$('#edit_div').dialog({
		    title: '修改资讯',
		    width: 300,
		    height: 280,
		    closed: true,
		    cache: false,
		    modal: true,
		    closable:true,
		    resizable:false
		});
	}
</script>
</head>
<body>
	<table id="info_table" ></table>
	<div id="info_toolbar">
		<table cellspacing="0" cellpadding="0" border="0" style="width:100%">
                <tr>
                    <td align="left">
                        <label style="font: 20px;">资讯标题：</label>
                        <input id="infoTitle" type="text" style="width:160px;height:25px; border:solid 1px #dfdfdf">
                        <input style="border:solid 1px #dfdfdf;width: 50;" type="button" value="查询" id="titleSearch" onclick="loadSearch()" />
                        <input style="border:solid 1px #dfdfdf;width: 50;" type="button" value="清空" id="clearSearch" onclick="clearSearch()" />
                    	<input style="border:solid 1px #dfdfdf;width: 50;" id="btnAdd" type="button" value="添加" onclick="info_add_open()" />
                    </td>
                </tr>
            </table>
	</div>
<div id="add_div" style="width: 280px;"  align="center">
	<table id="addTable" width="280" align="center">
		<!-- <tr>
			<td width="100">ID</td>
			<td><input type="text" id="id" /></td>
		</tr> -->
		<tr>
			<td>分类</td>
			<td><input type="text" id="classid" /></td>
		</tr>
		<tr>
			<td>标题</td>
			<td><input type="text" id="title" /></td>
		</tr>
		<tr>
			<td>图片</td>
			<td>
			<input width="130" type="file" name="image" id="image" />
			<input type="button" onclick="uploadImgs()" value="上传">
			</td>
		</tr>
		<tr>
			<td>内容</td>
			<td><input type="text" id="content" /></td>
		</tr>
		<!-- <tr>
			<td>创建时间</td>
			<td><input type="text" id="createtime" onfocus="createTime()" /></td>
		</tr> -->
		<tr >
			<td><input style="border:solid 1px #dfdfdf;width: 50;" type="button" value="确定" onclick="info_add()" /></td>
			<td><input style="border:solid 1px #dfdfdf;width: 50;" type="button" value="重置" onclick="resetAddData()" /></td>
		</tr>
	</table>
</div>

<iframe src="" name="temp_upload_frame" id="temp_upload_frame"
		style="display: none;"></iframe>
</body>
</html>