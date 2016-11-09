<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/alljoinperson?id='
									+ ${activitId },
							method : 'post',
							fit : true,
							title : '参与用户列表',
							fitColumns : true,
							singleSelect : true,
							pagination : true,
							rownumbers : false,
							pageSize : 10,
							pageList : [ 10, 20, 30 ],
							remoteSort : false,
							idField : 'id',
							loadMsg : '数据装载中......',
							columns : [ [
									{
										field : 'id',
										title : '用户编号',
										width : 30,
										align : 'center'
									},
									{
										field : 'nickname',
										title : '呢称',
										width : 30,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables')
													.datagrid("getRows");
											var usersid = dataValue[index].id;
											return '<a href="#" onclick="stuUserShow('
													+ usersid
													+ ')">'
													+ value
													+ '</a>';
										}
									},
									{
										field : 'name',
										title : '姓名',
										width : 30,
										align : 'center'
									},
									{
										field : 'sex',
										title : '性别',
										width : 30,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == "0") {
												return "男";
											} else {
												return "女";
											}
										}
									}, {
										field : 'startYear',
										title : '入学年份',
										width : 30,
										align : 'center'
									}, {
										field : 'school',
										title : '学校',
										width : 30,
										align : 'center'
									}, {
										field : 'college',
										title : '学院',
										width : 30,
										align : 'center'
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

	function stuUserShow(index) {
		window.location.href = global_ctxPath + '/stuuserShow1?id=' + index;
	}
	
	//跳转到列表页面
	function backlist() {
		window.location.href = global_ctxPath + '/activitieslist';
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 4px;padding-top: 0px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table id="tables" class="easyui-datagrid"
				style="width:inherit;height:auto; text-align: center;"
				pagination="true">

			</table>
		</div>
	</div>
	<div id="toolbar_t" class="info">
		<table cellspacing="0" cellpadding="0" border="0" style="width:100%">
			<tr>
				<td align="left"><a href="#" onclick="backlist()" data-options="iconCls:'icon-back'" margin-left: -10px;"
					class="easyui-linkbutton" plain="false">返回</a></td>
			</tr>
		</table>
	</div>
</body>