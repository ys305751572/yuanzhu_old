<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/alluser',
							method : 'post',
							fit : true,
							title : '',
							fitColumns : true,
							singleSelect : true,
							pagination : true,
							rownumbers : false,
							pageSize : 15,
							pageList : [ 15, 20, 25, 30 ],
							remoteSort : false,
							idField : 'id',
							loadMsg : '数据装载中......',
							columns : [ [
									{
										field : 'id',
										title : '用户编号',
										width : 30,
										align : 'center',
									},
									{
										field : 'userName',
										title : '登录名',
										width : 30,
										align : 'center'
									},
									{
										field : 'realName',
										title : '用户姓名',
										width : 30,
										align : 'center'
									},
									{
										field : 'mobile',
										title : '手机号码',
										width : 30,
										align : 'center'
									},
									{
										field : 'userType',
										title : '用户类型',
										width : 30,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == "1") {
												return "普通管理员";
											} else if (value == "2") {
												return "系统管理员";
											} else if (value == "3") {
												return "任务管理员";
											} else if (value == "4") {
												return "咨询管理员";
											} else if (value == "5") {
												return "活动管理员";
											}
										}
									},
									{
										field : 'fstatus',
										title : '操作',
										width : 40,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables')
													.datagrid("getRows");
											var usersid = dataValue[index].id;
											return '<img src="images/edt.gif" title="编辑" width="20px" style="cursor:pointer;" height="20px" onclick="update_user('
													+ usersid
													+ ')">&nbsp;&nbsp;<img src="images/del.gif" title="删除" width="20px" style="cursor:pointer;" height="20px" onclick="delete_user('
													+ index + ')">';
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

	//跳转到修改页面
	function update_user(index) {
		window.location.href = global_ctxPath + '/userUpdate?id=' + index;
	}

	//跳转到新增页面
	function add_user() {
		window.location.href = global_ctxPath + '/userAdd';
	}

	//删除操作
	function delete_user(index) {
		var dataValue = $('#tables').datagrid("getRows");
		var usersid = dataValue[index].id;
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('删除用户', '是否确定删除此用户？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/delete/user',
					data : {
						"id" : usersid
					},
					dataType : "json",
					success : function(result) {
						if (result == "1") {
							divfloat('删除成功', 160, 40);
						} else {
							divfloat('删除失败', 160, 40);
						}
						$('#tables').datagrid('reload');
					}
				});
			}
		});
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 4px;padding-top: 4px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table id="tables" class="easyui-datagrid"
				style="width:inherit;height:auto; text-align: center;"
				pagination="true">

			</table>
		</div>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width:100%">
				<tr>
					<td align="left"><a href="#" onclick="add_user()"
						data-options="iconCls:'icon-add'" class="easyui-linkbutton"
						plain="false">添加用户</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>