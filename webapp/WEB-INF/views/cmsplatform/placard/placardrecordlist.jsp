<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/allplacardRecord',
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
							queryParams : {
								"placardId" : $("#placardId").val()
							},
							columns : [ [
									{
										field : 'name',
										title : '姓名',
										width : 30,
										align : 'center'
									},
									{
										field : 'mobile',
										title : '手机号',
										width : 30,
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
		
		$.post(global_ctxPath + '/userType',function(data) {
			if(data != 2) {
				$("#selectDiv").css("display","none");
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

	function showplacard(id) {
		window.location.href = global_ctxPath + "/placardRecordlist?id=" + id;
	}
	
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


	
	//跳转到修改页面
	function updateplacard(index) {
		window.location.href = global_ctxPath + '/getPlacard?type=update&id='
				+ index;
	}

	//跳转到查看页面
	function showplacard(index) {
		window.location.href = global_ctxPath + '/getPlacard?type=show&id='
				+ index;
	}

	//跳转到新增页面
	function addplacard() {
		window.location.href = global_ctxPath + '/placardAdd';
	}

	// 重置搜索条件
	function resetValue() {
		$("#province").combobox('reload',
				global_ctxPath + '/findProvinceByParamsList');
		$("#createTime").datebox('setValue', '');
		$("#stuUser").val('');
	}

	//获取公告多选的ID
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
			$.messager.confirm('删除提示框', '你确定删除这些公告吗?删除后数据将不能恢复', function(r) {
				if (r) {
					deleteplacardss(ary);
				}
			})
		} else {
			divfloat('没有选择任何一项', 160, 40);
		}
	}

	//批量删除方法
	function deleteplacardss(ary) {
		$.ajax({
			type : 'post',//传输方式
			cache : false,
			url : global_ctxPath + '/deletePlacards',//路径
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

	//删除操作
	function deleteplacard(index) {
		var dataValue = $('#tables').datagrid("getRows");
		var usersid = dataValue[index].id;
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('删除公告', '是否确定删除此公告？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/deletePlacard',
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

	function doSearch() {
		$('#tables').datagrid("load", {
			provinceId : $("#province").combobox("getValue"),
			cityId : $("#city").combobox("getValue"),
			schoolId : $("#school").combobox("getValue"),
			createTime : $("#createTime").datebox("getValue"),
			stuUser : $("#stuUser").val()
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
			<input type="hidden" id="placardId" name="placardId" value="${placardId}">
			<div id="selectDiv" style="display: inline;">
			<span>省份：</span> <select id="province" class="easyui-combobox"
				name="province" style="width:120px;" data-options="editable:false" >
			</select> <span>城市：</span> <select id="city" class="easyui-combobox"
				name="city" style="width:120px;" data-options="editable:false" >
			</select> <span>学校：</span> <select id="school" class="easyui-combobox"
				name="school" style="width:120px;" data-options="editable:false" >
			</select>
			</div>
			<div style="display: inline;">
			<span>发布时间：</span> <input id="createTime" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser,editable:false"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" /><span
				style="margin-left: 6px;">编辑人：</span> <input id="stuUser"
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
					<td align="left"><a href="#" onclick="addplacard()"
						data-options="iconCls:'icon-add'" class="easyui-linkbutton"
						plain="false">添加公告</a> <a href="#"
						data-options="iconCls:'icon-remove'" onclick="deletes()"
						class="easyui-linkbutton" plain="false">删除</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>