<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/xiu/findAllXiu',
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
										title : '秀吧编号',
										width : 20,
										align : 'center',
									},
									{
										field : 'title',
										title : '秀吧标题',
										width : 60,
										align : 'center'
									},
									{
										field : 'shortName',
										title : '秀吧简称',
										width : 30,
										align : 'center'
									},
									{
										field : 'index',
										title : '排序',
										width : 30,
										align : 'center'
									},
									{
										field : 'createDate',
										title : '创建时间',
										width : 30,
										align : 'center'
									},
									{
										field : 'isList',
										title : '操作',
										width : 20,
										align : 'center',
										formatter : function(value, row, index) {
											
											var detail;
											var allot;
											var isList;
											
											var dataValue = $('#tables')
													.datagrid("getRows");
											var postBarId = dataValue[index].id;
											detail = '<img src="'+ global_ctxPath +'/images/view-flag.png" title="查看" width="20px" style="cursor:pointer;" height="20px" onclick="postBarShow('
											+ postBarId + ')">';	
											
											edit = '<img src="'+ global_ctxPath +'/images/edt.gif" title="编辑" width="20px" style="cursor:pointer;" height="20px" onclick="postBarEdit('
											+ postBarId + ')">';
											
											allot = '<img src="'+ global_ctxPath +'/images/role.png" title="赋权" width="20px" style="cursor:pointer;" height="20px" onclick="allotFn('
											+ postBarId + ')">';
											
											if(value == '1') {
												isList = '<img src="'+ global_ctxPath +'/images/no-flag.png" title="下架" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + postBarId + ',-1)">';
											}
											else if(value == '-1') {
												isList = '<img src="'+ global_ctxPath +'/images/ok-flag.png" title="上架" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + postBarId + ',1)">';
											}
											return detail + edit +allot + isList;
										}
									} ] ],
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
	});

	// 上/下架
	var exchange = function(postBarId,status) {
		$.ajax({
			type : 'post',
			url : global_ctxPath + '/xiu/exchange' ,
			dataType : 'json',
			data : {
				'id' : postBarId,
				'isList' : status
			},
			success : function(data){
				if(data.status == "1") {
					divfloat('操作失败', 150, 40);
				}
				else{
					divfloat('操作成功', 150, 40);
					$('#tables').datagrid('reload');
				}
			}
		});
	}
	
	var allotFn = function(postBarId) {
		window.location.href = global_ctxPath + "/xiu/userListPage?postBarId=" + postBarId;
	}
	

	// 查看活动详细信息
	var postBarShow = function(postBarId) {
		window.location.href = global_ctxPath + '/xiu/detail?postBarId=' + postBarId;
	}

	var postBarEdit = function(postBarId) {
		window.location.href = global_ctxPath + '/xiu/edit?postBarId=' + postBarId;
	}
	
	
	// 审核拒绝
	function activitiesUpdateToNo(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('提示', '确定将其审核为未通过？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/updateActivities',
					data : {
						"id" : id,
						"status" : "2"
					},
					dataType : "json",
					success : function(result) {
						if (result == "1") {
							divfloat('处理成功', 160, 40);
						} else {
							divfloat('处理失败', 160, 40);
						}
						$('#tables').datagrid('reload');
					}
				});
			}
		});
	}

	function doSearch() {
		$('#tables').datagrid("load", {
			provinceId : $("#province").combobox("getValue"),
			cityId : $("#city").combobox("getValue"),
			content : $("#content").val(),
			createTime : $("#createTime").datebox("getValue"),
			stuUser : $("#stuUser").val()
		});
	}

	function resetValue() {
		$("#province").combobox('reload',
				global_ctxPath + '/findProvinceByParamsList');
		$("#content").val('');
		$("#createTime").datebox('setValue', '');
		$("#stuUser").val('');
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

	var addPostBar = function() {
		window.location.href = global_ctxPath + "/xiu/add";
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
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height:45px;padding:10px 14px 0px 14px;background:#ffffff;overflow:hidden;">
			<span>活动内容：</span> <input id="content" class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" />
			<span
				style="margin-left: 6px;">发起人：</span> <input id="stuUser"
				class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:118px; height:20px;" />
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
					<td align="left"><a href="#" onclick="addPostBar()"
						data-options="iconCls:'icon-ok'" class="easyui-linkbutton"
						plain="false">新增秀吧</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>