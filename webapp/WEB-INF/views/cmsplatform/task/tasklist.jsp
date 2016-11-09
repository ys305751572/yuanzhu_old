<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/getTaskPage',
							method : 'post',
							fit : true,
							title : '',
							fitColumns : true,
							singleSelect : false,
							pagination : true,
							rownumbers : false,
							pageSize : 15,
							pageList : [ 15, 20, 25, 30 ],
							remoteSort : false,
							idField : 'id',
							loadMsg : '数据装载中......',
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									{
										field : 'id',
										title : '任务编号',
										width : 40,
										align : 'center',
									},
									{
										field : 'task_title',
										title : '任务标题',
										width : 90,
										align : 'left'
									},
									{
										field : 'nickname',
										title : '发布人',
										width : 50,
										align : 'center'
									},
									{
										field : 'task_createTime',
										title : '发布时间',
										width :40,
										align : 'center'
									},
									{
										field : 'typeName',
										title : '任务类型',
										width : 20,
										align : 'center'
									},
									{
										field : 'money',
										title : '任务奖励',
										width : 20,
										align : 'center'
									},
									{
										field : 'task_status',
										title : '审核状态',
										width : 20,
										align : 'center',
										formatter : function(value ,row ,index) {
											if(value == '0'){
												return '未审核';
											}
											else if(value == '1') {
												return '已通过';
											}
											else {
												return '已拒绝';
											}
										}
									},
									{
										field : 'accept_id',
										title : '接单状态',
										width : 20,
										align : 'center',
										formatter : function(value ,row ,index) {
											if(value == '0'){
												return '未接单';
											}
											else{
												return '已接单';
											}
										}
									},
									{
										field : 'acceptname',
										title : '接单人',
										width : 20,
										align : 'center',
									},
									{
										field : 'operate',
										title : '操作',
										width : 50,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables').datagrid("getRows");
											var taskid = dataValue[index].id;
											var detail = '<img src="images/view-flag.png" title="查看" width="20px" style="cursor:pointer;" height="20px" onclick="toShow(' + taskid + ')">'
											var g_status;
											if(value == '0') {
												console.log("value==0");
												g_status = '<img src="images/ok-flag.png" title="审核通过" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + taskid + ',1)">';
												g_status = g_status + '<img src="images/no-flag.png" title="审核拒绝" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + taskid + ',2)">';
											}
											else if(value == '1') {
												g_status = '<img src="images/no-flag.png" title="审核拒绝" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + taskid + ',2)">';
											}
											else if(value == '2') {
												g_status = '<img src="images/ok-flag.png" title="审核通过" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + taskid + ',1)">';
											}
											return detail + g_status;
										}
									},
									{
										field : 'status',
										title : '任务状态',
										width : 50,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables').datagrid("getRows");
											var taskid = dataValue[index].id;
											var g_status;
											if(value == '0') {
												g_status = '待接单';
											}
											else if(value == '1') {
												g_status = "已接单";
											}
											else if(value == '2') {
												g_status = "待评价";
											}
											else if(value == '3') {
												g_status = "已完成";
											}
											else if(value == '4') {
												g_status = "已过期";
											}
											else if(value == '5') {
												g_status = "已取消";
											}
											return g_status;
										}
									},
									{
										field : 'reword',
										title : '奖励状态',
										width : 50,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables').datagrid("getRows");
											var taskid = dataValue[index].id;
											var g_status;
											if(value == '1') {
												g_status = '已奖励';
											}
											else {
												g_status = '<img src="images/ok-flag.png" title="确认奖励" width="20px" style="cursor:pointer;" height="20px" onclick="reword(' + taskid + ')">';
											}
											return g_status;
										}
									} 
									] ],
							toolbar : '#toolbar_t'
						});
		//分页参数配置
		$('#tables').datagrid('getPager').pagination({
			displayMsg : '当前显示从{from}到{to}共{total}记录',
			beforePageText : '第',
			afterPageText : '页 共 {pages} 页',
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
		
		// 加载任务类型列表
		$('#task_type_id').combobox({
			url : global_ctxPath + '/taskTypeList',
			valueField : "id",
			textField : "type",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#task_type_id').combobox('getData');
				if (data.length > 0) {
					$('#task_type_id').combobox("select", data[0].id);
				}
			}
		});
	});

	function reword(taskId) {
		$.ajax({
			type : 'post',
			url : global_ctxPath + '/reword' ,
			dataType : 'json',
			data : {
				'taskId' : taskId
			},
			success : function(data){
				if(data.status == "1") {
					divfloat(data.msg, 150, 40);
				}
				else{
					divfloat('操作成功', 150, 40);
					$('#tables').datagrid('reload');
				}
			}
		});
	}
	
	// 跳转详情页面
	function toShow(index) {
		window.location.href = global_ctxPath + '/toTaskShow?id=' + index;
	}

	// 跳转编辑页面
	function toEdit(index) {
		console.log(index);
		window.location.href = global_ctxPath + '/toEdit?id=' + index;
	}
	
	function toAdd() {
		window.location.href = global_ctxPath + '/toAdd';
	}
	
	// 上/下架
	function exchange(index,status) {
		$.ajax({
			type : 'post',
			url : global_ctxPath + '/exchangeTask' ,
			dataType : 'json',
			data : {
				'id' : index,
				't_status' : status
			},
			success : function(data){
				if(data == "-1") {
					divfloat('操作失败', 150, 40);
				}
				else{
					divfloat('操作成功', 150, 40);
					$('#tables').datagrid('reload');
				}
			}
		});
	}
	
	function download2() {
		window.location.href = global_ctxPath + "/commons/downloadTaskExcel";
	}

	function doSearch() {
		$('#tables').datagrid("load", {
			task_type_id       :  $("#task_type_id").combobox('getValue'),
			task_status        :  $("#task_status").combobox('getValue'),
			task_accapt_status :  $("#task_accapt_status").combobox('getValue'),
			start_time         :  $("#start_time").datebox('getValue'),
			end_time           :  $("#end_time").datebox('getValue'),
			create_user        :  $("#create_user").val()
		});
	}

	function resetValue() {
		$("#content").val('');
	}

	//获取多选的ID
	function getRowsIdfield() {
		var str = "";
		var rows = $('#tables').datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			//if (rows[i].status == '0') {
				
			//}
			str += rows[i].id + ",";
		}
		$('#tables').datagrid("clearSelections");
		return str;
	}

	//批量处理
	function updatestoyes() {
		var ary = getRowsIdfield();
		if (ary.length > 0) {
			$.messager.confirm('提示', '你确定将所有选中项审核为通过？', function(r) {
				if (r) {
					activitiesUpdates(ary, 1);
				}
			})
		} else {
			divfloat('请选择至少一项记录', 250, 40);
		}
	}

	//批量处理
	function updatestono() {
		var ary = getRowsIdfield();
		if (ary.length > 0) {
			$.messager.confirm('提示', '你确定将所有选中项审核为未通过？', function(r) {
				if (r) {
					activitiesUpdates(ary, 2);
				}
			})
		} else {
			divfloat('请选择至少一项未审核的记录', 250, 40);
		}
	}

	//批量处理方法
	function activitiesUpdates(ary, flag) {
		$.ajax({
			type : 'post',//传输方式
			cache : false,
			url : global_ctxPath + '/updateActivitiess',//路径
			dataType : 'json',
			data : {
				"ary" : ary,
				"status" : flag
			},
			success : function(data) {
				if (data > 0) {
					divfloat('操作成功', 160, 40);
					$('#tables').datagrid('reload');
				} else {
					divfloat('操作失败', 160, 40);
				}
			},
			error : function() {
				divfloat('操作失败', 160, 40);
			}
		});
	}

	//格式化时间
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	};

	function myparser(s) {
		if (!s)
			return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	};
	
	function toBindGroupPage() {
		window.location.href =  global_ctxPath + "/toBindGroupPage";
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height:60px;padding:10px 14px 0px 14px;background:#ffffff;overflow:hidden;">
			 <span>任务类型：</span>
			 <select id="task_type_id" name="task_type_id" class="easyui-combobox" style="width:116px;" data-options="editable:false">
			 </select> 
			 <span>审核状态：</span>
			 <select id="task_status" class="easyui-combobox" style="width:116px;" data-options="editable:false">
			 	<option value="">所有状态</option>
			 	<option value="0">未审核</option>
			 	<option value="1">通过</option>
			 	<option value="2">未通过</option>
			 </select>
			 <span>接单状态：</span>
			 <select id="task_accapt_status" class="easyui-combobox" style="width:116px;" data-options="editable:false">
			 	<option value="-1">所有状态</option>
			 	<option value="1">已接单</option>
			 	<option value="2">未接单</option>
			 </select>
			 
			 <span>开始时间：</span>
			 <input id="start_time" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"
				    name="startDate" editable="fasle" style="line-height:16px;border:1px solid #ccc; width:116px; height:20px;" />
			 <span>截止日期:</span> 
			 <input id="end_time" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" name="endDate"
					editable="fasle" style="line-height:16px;border:1px solid #ccc; width:116px; height:20px;" />
			 <br />
			 <span>发起人  &nbsp;&nbsp;：</span> 
			 <input id="create_user" class="textbox" style="line-height:16px;border:1px solid #ccc; width:116px; height:20px;" />
			
							
			<a href="javascript:void(0)" style="width: 60px;"
				data-options="iconCls:'icon-search'" class="easyui-linkbutton"
				plain="false" onclick="doSearch()">查询</a> <a href="#"
				style="width: 60px;" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload'" plain="false"
				onclick="resetValue()">重置</a>
		</div>
		<div data-options="region:'center',border:false"
			style="padding-left: 4px;padding-top: 0px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table id="tables" style="width:inherit;height:auto; text-align: center;" pagination="true">

			</table>
		</div>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width:100%">
				<tr>
					<td align="left"><a href="#" onclick="download2()"
						data-options="iconCls:'icon-ok'" class="easyui-linkbutton"
						plain="false">下载文档</a>
						<td<a href="#" onclick="toBindGroupPage()"
						data-options="iconCls:'icon-ok'" class="easyui-linkbutton"
						plain="false">任务类型绑定群</a>
				</tr>
			</table>
		</div>
	</div>
</body>