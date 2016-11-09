<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	var updateId = 0;
	$(function() {
		$("#filtrationName").focus().keydown(function(event) {
			if (event.which == 13) {
				filtrationAddorUpdate();
			}
		});

		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/allfiltration',
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
										title : '关键词编号',
										width : 30,
										align : 'center',
									},
									{
										field : 'content',
										title : '关键词内容',
										width : 100,
										align : 'center'
									},
									{
										field : 'time',
										title : '发布时间',
										width : 30,
										align : 'center'
									},
									{
										field : 'userName',
										title : '编辑人',
										width : 30,
										align : 'center'
									},
									{
										field : 'edit',
										title : '操作',
										width : 40,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables')
													.datagrid("getRows");
											var id = dataValue[index].id;
											var content = dataValue[index].content;
											/* return '<img src="images/view-flag.png" title="查看" width="20px" style="cursor:pointer;" height="20px" onclick="showfiltration('
													+ id
													+ ')">&nbsp;&nbsp;<img src="images/edt.gif" title="编辑" width="20px" style="cursor:pointer;" height="20px" onclick="updatefiltration('
													+ id + ')">'; */
											var edit = "edit";
											return '<img src="images/edt.gif" title="编辑" width="20px" style="cursor:pointer;" height="20px" onclick="addOrupdateFiltration('
													+ "'"
													+ edit
													+ "'"
													+ ','
													+ id
													+ ','
													+ "'"
													+ content
													+ "'" + ')">';
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

		//对话框初始化
		$('#addOrupdateFiltration').dialog({
			title : 'sdfsd',
			top : 140,
			width : 370,
			height : 150,
			cache : false,
			modal : true,
			closed : true,
			inline : false,
			buttons : [ {
				text : '确定',
				handler : filtrationAddorUpdate
			}, {
				text : '取消',
				handler : function() {
					$('#addOrupdateFiltration').dialog('close');
				}
			} ],
			onClose : function() {
				$("#filtrationName").val('');
			}
		});
	});

	function addOrupdateFiltration(type, id, name) {
		if (type == 'add') {
			$('#addOrupdateFiltration').dialog('setTitle', '新增');
			$("#filtrationId").html('请输入关键词名称：');
		} else {
			updateId = id;
			$('#addOrupdateFiltration').dialog('setTitle', '修改--' + name);
			$("#filtrationId").html('请输入新的关键词名称：');
		}
		$('#addOrupdateFiltration').dialog('open');
		$("#filtrationName").focus();
	}

	function filtrationAddorUpdate() {
		var flag = "sdfsdfsdf";
		
		if (updateId == "0") {
			flag = '新增';
		} else {
			flag = '修改';
		}

		var name = $("#filtrationName").val();

		if (name.replace(/\s/gi, '').length < 1) {
			divfloat(flag + '关键词名不能为空', 250, 40);
			$('#filtrationName').focus();
			return false;
		}
		var lenname = name.replace(/[^\x00-\xff]/g, "**").length;
		if (lenname > 100) {
			divfloat('关键词名过长', 250, 40);
			$('#filtrationName').focus();
			return;
		}
		
		$.ajax({
			type : "post",
			async : false,
			url : global_ctxPath + '/addOrupdateFiltration',
			data : {
				"id" : updateId,
				"content" : name
			},
			dataType : "json",
			success : function(result) {
				if (result == "1") {
					$('#addOrupdateFiltration').dialog('close');
					$("#filtrationName").val('');
					divfloat(flag + '成功', 160, 40);
					updateId = 0;
					$('#tables').datagrid('reload');
				} else {
					divfloat(flag + '失败', 160, 40);
				}
			}
		});
	}

	//获取多选的ID
	function getRowsIdfield() {
		var str = "";
		var rows = $('#tables').datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			str += rows[i].id + ",";
		}
		$('#tables').datagrid("clearSelections")
		return str;
	}

	//批量删除
	function deletes() {
		var ary = getRowsIdfield();
		if (ary.length > 0) {
			$.messager.confirm('删除提示框', '你确定删除这些关键词吗?删除后数据将不能恢复', function(r) {
				if (r) {
					deletefiltrationss(ary);
				}
			})
		} else {
			divfloat('没有选择任何一项', 160, 40);
		}
	}

	//批量删除方法
	function deletefiltrationss(ary) {
		$.ajax({
			type : 'post',//传输方式
			cache : false,
			url : global_ctxPath + '/deleteFiltrations',//路径
			dataType : 'json',
			data : {
				"ary" : ary
			},
			success : function(data) {
				if (data > 0) {
					divfloat('删除成功', 160, 40);
					$('#tables').datagrid('reload');
				} else {
					divfloat('删除失败', 160, 40);
				}
			},
			error : function() {
				divfloat('删除失败', 160, 40);
			}
		});
	}

	function doSearch() {
		$('#tables').datagrid("load", {
			createTime : $('#createTime').datebox('getValue'),
			content : $('#content').val(),
			stuUser : $('#stuUser').val()
		});
	}

	function resetValue() {
		$('#createTime').datebox('setValue', '');
		$('#content').val('');
		$('#stuUser').val('');
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
			<span>编辑人：</span> <input id="stuUser" class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" />
			<span>内容：</span> <input id="content" class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" />
			<span>发布时间：</span> <input id="createTime" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser,editable:false"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" />
			<a href="javascript:void(0)" style="width: 60px;"
				data-options="iconCls:'icon-search'" class="easyui-linkbutton"
				plain="false" onclick="doSearch()">查询</a> <a href="#"
				style="width: 60px;" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload'" plain="false"
				onclick="resetValue()">重置</a>
		</div>
		<div data-options="region:'center',border:false"
			style="padding-left: 4px;padding-top: 0px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table id="tables" class="easyui-datagrid"
				style="width:inherit;height:auto; text-align: center;"
				pagination="true">

			</table>
		</div>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width:100%">
				<tr>
					<td align="left"><a href="#"
						onclick="addOrupdateFiltration('add','0','0')"
						data-options="iconCls:'icon-add'" class="easyui-linkbutton"
						plain="false">添加关键词</a> <a href="#"
						data-options="iconCls:'icon-remove'" onclick="deletes()"
						class="easyui-linkbutton" plain="false">删除</a></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="addOrupdateFiltration">
		<table style="margin-left: 15px; margin-top: 30px;">
			<tr>
				<td><span id="filtrationId">请输入名称：</span></td>
				<td><input id="filtrationName" type="textbox" /></td>
			</tr>
		</table>
	</div>
</body>