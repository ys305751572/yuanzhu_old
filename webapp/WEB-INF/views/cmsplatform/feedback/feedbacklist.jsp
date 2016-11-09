<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/allback',
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
										title : '反馈编号',
										width : 30,
										align : 'center',
									},
									{
										field : 'content',
										title : '反馈内容',
										width : 100,
										align : 'center'
									},
									{
										field : 'backtime',
										title : '反馈时间',
										width : 30,
										align : 'center'
									},
									{
										field : 'person',
										title : '反馈人',
										width : 30,
										align : 'center'
									},
									{
										field : 'status',
										title : '状态',
										width : 20,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == "2") {
												return "<img src='images/green-flag.png' title='已处理' width='20px' height='20px'>";
											} else {
												return "<img src='images/yellow-flag.png' title='未处理' width='20px' height='20px'>";
											}
										}
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
											return '<img src="images/view-flag.png" title="查看" width="20px" style="cursor:pointer;" height="20px" onclick="backShow('
													+ id + ')">';
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

	// 查看反馈
	function backShow(index) {
		window.location.href = global_ctxPath + '/backShow?id=' + index;
	}

	// 处理反馈
	function backUpdate(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('提示', '标记为已处理？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/updateBack',
					data : {
						"id" : id
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
			status : $("#status").combobox("getValue"),
			backTime : $("#backTime").datebox("getValue"),
			stuUser : $("#stuUser").val()
		});
	}

	function resetValue() {
		$("#status").combobox('setValue', '0');
		$("#backTime").datebox('setValue', '');
		$("#stuUser").val('');
	}

	//获取反馈多选的ID
	function getRowsIdfield() {
		var str = "";
		var rows = $('#tables').datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			str += rows[i].id + ",";
		}
		$('#tables').datagrid("clearSelections")
		return str;
	}

	//批量处理
	function updates() {
		var ary = getRowsIdfield();
		if (ary.length > 0) {
			$.messager.confirm('提示', '你确定将所有选中项标记为已处理？', function(r) {
				if (r) {
					backUpdates(ary);
				}
			})
		} else {
			divfloat('没有选择任何一项', 160, 40);
		}
	}

	//批量处理方法
	function backUpdates(ary) {
		$.ajax({
			type : 'post',//传输方式
			cache : false,
			url : global_ctxPath + '/updateBacks',//路径
			dataType : 'json',
			data : {
				"ary" : ary
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
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height:45px;padding:10px 14px 0px 14px;background:#ffffff;overflow:hidden;">
			<span>状态：</span> <select id="status" class="easyui-combobox"
				name="status" style="width:120px;" data-options="editable:false">
				<option value="0">所有项</option>
				<option value="1">未处理</option>
				<option value="2">已处理</option>
			</select><span>反馈时间：</span> <input id="backTime" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser,editable:false"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" /><span
				style="margin-left: 6px;">反馈人：</span> <input id="stuUser"
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
			<table id="tables" class="easyui-datagrid"
				style="width:inherit;height:auto; text-align: center;"
				pagination="true">

			</table>
		</div>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width:100%">
				<tr>
					<td align="left"><a href="#" onclick="updates()"
						data-options="iconCls:'icon-edit'" class="easyui-linkbutton"
						plain="false">标记已处理</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>