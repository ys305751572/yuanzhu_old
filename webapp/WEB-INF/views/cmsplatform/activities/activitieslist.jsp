<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/allactivities',
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
										title : '活动编号',
										width : 20,
										align : 'center',
									},
									{
										field : 'content',
										title : '活动内容',
										width : 60,
										align : 'center'
									},
									{
										field : 'time',
										title : '发布时间',
										width : 30,
										align : 'center'
									},
									{
										field : 'status',
										title : '状态',
										width : 20,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == "1") {
												return "<img src='images/green-flag.png' title='审核通过' width='20px' height='20px'>";
											} else if (value == "2") {
												return "<img src='images/red-flag.png' title='审核拒绝' width='20px' height='20px'>";
											} else {
												return "<img src='images/yellow-flag.png' title='未审核' width='20px' height='20px'>";
											}
										}
									},
									{
										field : 'edit',
										title : '操作',
										width : 20,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables')
													.datagrid("getRows");
											var usersid = dataValue[index].id;
											return '<img src="images/view-flag.png" title="查看" width="20px" style="cursor:pointer;" height="20px" onclick="activitiesShow('
													+ usersid + ')">';
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

		$('#province').combobox({
			url : global_ctxPath + '/findProvinceByParamsList',
			valueField : "id",
			textField : "name",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#province').combobox('getData');
				if (data.length > 0) {
					$('#province').combobox("select", data[0].id);
				}
			},
			onSelect : function() {
				loadCity();
			}
		});
	});

	// 加载城市信息
	function loadCity() {
		// 加载城市信息
		$('#city').combobox(
				{
					url : global_ctxPath + '/findCityByParamsList?provinceId='
							+ $("#province").combobox("getValue"),
					valueField : "id",
					textField : "name",
					editable : false,
					width : 150,
					multiple : false,
					required : false,
					dataType : 'json',
					onLoadSuccess : function() {
						//选择返回数据中的第一条
						var data = $('#city').combobox('getData');
						if (data.length > 0) {
							$('#city').combobox("select", data[0].id);
						}
					}
				});
	}

	// 查看活动详细信息
	function activitiesShow(index) {
		window.location.href = global_ctxPath + '/activitiesShow?id=' + index;
	}

	// 审核通过
	function activitiesUpdateToYes(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('提示', '确定将其审核为通过？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/updateActivities',
					data : {
						"id" : id,
						"status" : "1"
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
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height:45px;padding:10px 14px 0px 14px;background:#ffffff;overflow:hidden;">
			<span>省份：</span> <select id="province" class="easyui-combobox"
				name="province" style="width:120px;" data-options="editable:false">
			</select> <span>城市：</span> <select id="city" class="easyui-combobox"
				name="city" style="width:120px;" data-options="editable:false">
			</select> <span>活动内容：</span> <input id="content" class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" />
			<span>发布时间：</span> <input id="createTime" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser,editable:false"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" /><span
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
			<table id="tables" class="easyui-datagrid"
				style="width:inherit;height:auto; text-align: center;"
				pagination="true">

			</table>
		</div>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width:100%">
				<tr>
					<td align="left"><a href="#" onclick="updatestoyes()"
						data-options="iconCls:'icon-ok'" class="easyui-linkbutton"
						plain="false">审核通过</a>&nbsp;&nbsp;<a href="#"
						onclick="updatestono()" class="easyui-linkbutton"
						data-options="iconCls:'icon-no'" plain="false">审核拒绝</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>