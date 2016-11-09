<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>

<script type="text/javascript">
	$(function() {
		var current = '${pages}';

		if (current == null || current == "") {
			current = '0'
		}

		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/allgroup',
							queryParams : {
								'page' : current
							},
							method : 'post',
							fit : true,
							title : '',
							fitColumns : true,
							singleSelect : false,
							pagination : true,
							rownumbers : false,
							pageNumber : current,
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
										title : '群号',
										width : 30,
										align : 'center',
									},
									{
										field : 'name',
										title : '群名称',
										width : 30,
										align : 'center',
									},
									{
										field : 'gscale',
										title : '群规模',
										width : 30,
										align : 'center'
									},
									{
										field : 'gtype',
										title : '话题类型',
										width : 20,
										align : 'center',
									},
									{
										field : 'province',
										title : '群省份',
										width : 20,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == "0") {
												return "";
											} else {
												return value;
											}
										}
									},
									{
										field : 'city',
										title : '群城市',
										width : 30,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == "0") {
												return "";
											} else {
												return value;
											}
										}
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
											var groupid = dataValue[index].id;
											var groupidStr = groupid.toString();
											console.log("groupid:" + groupid
													+ "==groupidStr:"
													+ groupidStr)
											return "<img src='images/view-flag.png' title='查看' width='20px' style='cursor:pointer;' height='20px' onclick=groupShow('"
													+ groupidStr + "')>";
										}
									} ] ],
							toolbar : '#toolbar_t',
							onLoadSuccess : function(data) {
								current = parseInt(data.current);
							}
						});
		//分页参数配置
		$('#tables').datagrid('getPager').pagination({
			displayMsg : '当前显示从{from}到{to}共{total}记录',
			beforePageText : '第',
			afterPageText : '页 共 {pages} 页',
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			},
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

		$('#scale').combobox({
			url : global_ctxPath + '/findScaleByParams',
			valueField : "id",
			textField : "scale",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#scale').combobox('getData');
				if (data.length > 0) {
					$('#scale').combobox("select", data[0].id);
				}
			}
		});

		$('#type').combobox({
			url : global_ctxPath + '/findTypeByParams',
			valueField : "id",
			textField : "name",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#type').combobox('getData');
				if (data.length > 0) {
					$('#type').combobox("select", data[0].id);
				}
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
					onSelect : function() {
						//加载学校信息
						loadSchool();
					},
					onLoadSuccess : function() {
						//选择返回数据中的第一条
						var data = $('#city').combobox('getData');
						if (data.length > 0) {
							$('#city').combobox("select", data[0].id);
						}
					}
				});
	}

	// 加载学校信息
	function loadSchool() {
		$('#school').combobox(
				{
					url : global_ctxPath
							+ '/findSchoolByParamsList?provinceId='
							+ $("#province").combobox("getValue") + '&cityId='
							+ $("#city").combobox("getValue") + '&id='
							+ $("#school").combobox("getValue"),
					valueField : "id",
					textField : "proName",
					editable : false,
					width : 150,
					multiple : false,
					required : false,
					dataType : 'json',
					onLoadSuccess : function() {
						//选择返回数据中的第一条
						var data = $('#school').combobox('getData');
						if (data.length > 0) {
							$('#school').combobox("select", data[0].id);
						}
					}
				});
	}

	// 查看群详细信息
	function groupShow(index) {
		console.log("groupid:" + index);
		window.location.href = global_ctxPath + '/groupShow?id=' + index
				+ '&pageNumber=1';
	}

	// 审核通过
	function groupUpdateToYes(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('提示', '确定将其审核为通过？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/updateGroup',
					data : {
						"id" : id,
						"groupType" : $("#_groupType").val()
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
	function groupUpdateToNo(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('提示', '确定将其审核为未通过？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/updateGroup',
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

	function doSearch() {
		$('#tables').datagrid("load", {
			provinceId : $("#province").combobox("getValue"),
			cityId : $("#city").combobox("getValue"),
			groupType : $("#type").combobox("getValue"),
			schoolId : $("#school").combobox("getValue"),
			scaleId : $("#scale").combobox("getValue"),
			id : $("#groupid").numberbox("getValue")
		});
	}

	function resetValue() {
		$("#province").combobox('reload',
				global_ctxPath + '/findProvinceByParamsList');
		$("#type").combobox('setValue', '-1');
		$("#scale").combobox('setValue', '-1');
		$("#groupid").numberbox('setValue', '');
	}

	//获取多选的ID
	function getRowsIdfield() {
		var str = "";
		var rows = $('#tables').datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			//if (rows[i].status == '0' || rows[i].status == '3') {

			//}
			str += rows[i].id + ",";
		}
		$('#tables').datagrid("clearSelections")
		return str;
	}

	//批量处理
	function updatestoyes() {
		var ary = getRowsIdfield();
		if (ary.length > 0) {
			$.messager.confirm('提示', '你确定将所有选中项审核为通过？', function(r) {
				if (r) {
					groupUpdates(ary, 1);
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
					groupUpdates(ary, 2);
				}
			})
		} else {
			divfloat('请选择至少一项未审核的记录', 250, 40);
		}
	}

	//批量处理方法
	function groupUpdates(ary, flag) {
		$.ajax({
			type : 'post',//传输方式
			cache : false,
			url : global_ctxPath + '/updateGroups',//路径
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
	
	// 确认绑定
	function confirm() {
		var ary = getRowsIdfield();
		var arrays = ary.split(",");
		console.log("ary:" + ary +"==arylength:" + arrays.length);
		 if(arrays.length == 0){
			divfloat('请选择至少一项记录', 250, 40);
		} else if(arrays.length > 2){
			divfloat('请选择最多一项记录', 250, 40);
		} else if (arrays.length > 0) {
			$.messager.confirm('提示', '你确定将选中项设置为绑定群？', function(r) {
				$.ajax({
					type : 'post',//传输方式
					cache : false,
					url : global_ctxPath + '/bindGroup',//路径
					dataType : 'json',
					data : {
						"groupType" : $("#_groupType").val(),
						"groupId" : ary
					},
					success : function(data) {
						if (data.status == 0) {
							divfloat('操作成功', 160, 40);
							window.location.href = global_ctxPath + "/toBindGroupPage";
						} else {
							divfloat('操作失败success', 160, 40);
						}
					},
					error : function() {
						divfloat('操作失败error', 160, 40);
					}
				});
			});
		}
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<input type="hidden" id="_groupType" name="_groupType" value="${type}">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height: auto; padding: 10px 14px 5px 14px; background: #ffffff; overflow: hidden;">
			<span>省份：</span> <select id="province" class="easyui-combobox"
				name="province" style="width: 120px;" data-options="editable:false">
			</select> <span>城市：</span> <select id="city" class="easyui-combobox"
				name="city" style="width: 120px;" data-options="editable:false">
			</select> <span>类型：</span> <select id="type" class="easyui-combobox"
				name="type" style="width: 120px;" data-options="editable:false">
			</select> <span>学校：</span> <select id="school" class="easyui-combobox"
				name="school" style="width: 120px;" data-options="editable:false">
			</select> <br /> <span>规模：</span> <select id="scale" class="easyui-combobox"
				name="scale" style="width: 120px;" data-options="editable:false">
			</select><span> 群号：</span> <input id="groupid"
				class="easyui-numberbox textbox"
				style="line-height: 16px; border: 1px solid #ccc; width: 116px; height: 20px;" />
			<a href="javascript:void(0)" style="width: 60px;"
				data-options="iconCls:'icon-search'" class="easyui-linkbutton"
				plain="false" onclick="doSearch()">查询</a> <a href="#"
				style="width: 60px;" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload'" plain="false"
				onclick="resetValue()">重置</a>
		</div>
		<div data-options="region:'center',border:false"
			style="padding-left: 4px; padding-top: 0px; padding-right: 4px; background: #ffffff; overflow: hiadden;">
			<table id="tables" class="easyui-datagrid"
				style="width: inherit; height: auto; text-align: center;"
				pagination="true">

			</table>
		</div>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width: 100%">
				<tr>
					<td align="left"><a href="#" onclick="confirm()"
						data-options="iconCls:'icon-ok'" class="easyui-linkbutton"
						plain="false">确认绑定</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>