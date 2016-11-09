<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
var current = '${pages}';
		
		if(current == null || current == ""){
			current = '0'
		}
		
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/xiu/findAllUser?postBarId=' + $("#postBarId").val(),
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
										field : 'mobile',
										title : '账号',
										width : 40,
										align : 'center',
									},
									{
										field : 'nickname',
										title : '呢称',
										width : 45,
										align : 'center'
									},
									{
										field : 'name',
										title : '姓名',
										width : 35,
										align : 'center'
									},
									{
										field : 'sex',
										title : '性别',
										width : 20,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == "0") {
												return "男";
											} else {
												return "女";
											}
										}
									},
									{
										field : 'startYear',
										title : '入学年份',
										width : 20,
										align : 'center'
									},
									{
										field : 'school',
										title : '学校',
										width : 30,
										align : 'center'
									},
									{
										field : 'college',
										title : '学院',
										width : 30,
										align : 'center'
									},
									{
										field : 'registerTime',
										title : '创建时间',
										width : 35,
										align : 'center'
									},
									{
										field : 'recUserId',
										title : '推荐人',
										width : 35,
										align : 'center'
									}
									] ],
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
			}
		});
	
		/****/
		
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

		$('#startYear').combobox({
			url : global_ctxPath + '/findYearsByParams',
			valueField : "id",
			textField : "year",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#startYear').combobox('getData');
				if (data.length > 0) {
					$('#startYear').combobox("select", data[0].id);
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
	
	function exchange(index,status) {
		$.ajax({
			type : 'post',
			url : global_ctxPath + '/exchangeVliadte' ,
			dataType : 'json',
			data : {
				'userId' : index,
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

	//跳转到详情页面
	function activitiesShow(index,pageNumber) {
		var pageNumber = $('#tables').datagrid('options').pageNumber;
		window.location.href = global_ctxPath + '/stuuserShow?id=' + index + '&pageNumber=' + pageNumber;
	}

	// 审核通过
	function activitiesUpdateToYes(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		var str;
		if(getStuuserById(id)){
			str = "确定将其认证为通过？";
		}else{
			str = "用户信息未完善,确定将其认证为通过？";
		}
		//如果用户管理页面个人信息没完善，点击认证通过，弹出对话框，提示：该用户未完善个人信息，是否同意认证
		$.messager.confirm('提示', str, function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/updatestuUser',
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
	
	// 审核拒绝
	function activitiesUpdateToNo(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('提示', '确定将其认证为未通过？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/updatestuUser',
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
			
			//provinceId : $("#province").combobox("getValue"),
			//cityId : $("#city").combobox("getValue"),
			//startYear : $("#startYear").combobox("getValue"),
			//schoolId : $("#school").combobox("getValue"),
			name : $("#name").val(),
			mobile : $("#mobile").val(),
		});
	}

	function resetValue() {
		$("#province").combobox('reload',
				global_ctxPath + '/findProvinceByParamsList');
		$("#startYear").combobox('setValue', '-1');
		$("#status").combobox('setValue', '-1');
		$("#name").val('');
		$("#mobile").val('');
	}

	//获取多选的ID
	function getRowsIdfield() {
		var str = "";
		var rows = $('#tables').datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			str += rows[i].id + ",";
		}
		$('#tables').datagrid("clearSelections");
		return str;
	}

	//批量处理
	var flag = -1;
	function check(ary) {
		var ary = getRowsIdfield();
		if(ary.length > 0) {
			$.ajax({
				type : 'post',//传输方式
				cache : false,
				url : global_ctxPath + '/xiu/check',//路径
				dataType : 'json',
				data : {
					"ary" : ary,
					"postBarId" : $("#postBarId").val()
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
		else {
			divfloat('请选择至少一项未认证的记录', 250, 40);
			return;
		}
	}

	//叶松20151-27
	function getStuuserById(ary){
		$.ajax({
			type : "post",
			async : false,
			url : global_ctxPath + '/stuuserShow2?ary='+ary,
			dataType : "json",
			success : function(result) {
				var str;
				if (result == "1") {
					str = "确定将其认证为通过？";
				} else if(result == "-1"){
					str = "用户信息未完善,确定将其认证为通过？";
				}
				$.messager.confirm('提示', str, function(r) {
					if (r) {
						activitiesUpdates(ary, 2);
					}
				})
			}
		});
	}
	
	//批量处理
	function updatestono() {
		var ary = getRowsIdfield();
		if (ary.length > 0) {
			$.messager.confirm('提示', '你确定将所有选中项认证为未通过？', function(r) {
				if (r) {
					activitiesUpdates(ary, 1);
				}
			})
		} else {
			divfloat('请选择至少一项未认证的记录', 250, 40);
		}
	}

	//批量处理方法
	function activitiesUpdates(ary, flag) {
		$.ajax({
			type : 'post',//传输方式
			cache : false,
			url : global_ctxPath + '/updatestuUsers',//路径
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
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<input type="hidden" id="postBarId" name="postBarId" value="${postBar.id}">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height:auto;padding:10px 14px 5px 14px;background:#ffffff;overflow:hidden;">
			<!-- 
			<span>省份：</span> <select id="province" class="easyui-combobox"
				name="province" style="width:100px;" data-options="editable:false">
			</select> <span>城市：</span> <select id="city" class="easyui-combobox"
				name="city" style="width:100px;" data-options="editable:false">
			</select> <span>入学年份：</span> <select id="startYear" class="easyui-combobox"
				name="startYear" style="width:100px;" data-options="editable:false">
			</select> <span>学校：</span> <select id="school" class="easyui-combobox"
				name="school" style="width:100px;" data-options="editable:false">
			</select> <br />
			 -->
			 <span>姓名：</span> <input id="name" class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:148px; height:20px;" /><span
				style="margin-left: 4px;">账号：</span> <input id="mobile"
				class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:148px; height:20px;" />
			 <a href="javascript:void(0)" style="width: 60px;"
				data-options="iconCls:'icon-search'" class="easyui-linkbutton"
				plain="false" onclick="doSearch()">查询</a> <a href="#"
				style="width: 60px;" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload'" plain="false"
				onclick="resetValue()">重置</a>
		</div>
		<div data-options="region:'center',border:false"
			style="padding-left: 4px;padding-top: 4px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table id="tables" style="width:inherit;height:auto; text-align: center;" pagination="true">
			</table>
		</div>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width:100%">
				<tr>
					<td align="left"><a href="#" onclick="check()"
						data-options="iconCls:'icon-ok'" class="easyui-linkbutton"
						plain="false">选择</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>