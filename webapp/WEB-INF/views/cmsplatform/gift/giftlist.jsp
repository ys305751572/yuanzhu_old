<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/getlist',
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
										title : '商品编号',
										width : 40,
										align : 'center',
									},
									{
										field : 'name',
										title : '商品名称',
										width : 90,
										align : 'center'
									},
									{
										field : 'coin',
										title : '兑换需要',
										width : 50,
										align : 'center'
									},
									{
										field : 'g_status',
										title : '上架否',
										width : 20,
										align : 'center',
										formatter : function(value ,row ,index) {
											if(value == '0'){
												return '否'
											}
											else{
												return '是'
											}
										}
									},
									{
										field : 'gs',
										title : '操作',
										width : 50,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables').datagrid("getRows");
											var giftId = dataValue[index].id;
											var detail = '<img src="images/view-flag.png" title="查看" width="20px" style="cursor:pointer;" height="20px" onclick="toShow(' + giftId + ')">'
											var edit = '<img src="images/edt.gif" title="编辑" width="20px" style="cursor:pointer;" height="20px" onclick="toEdit(' + giftId + ')">'
											var g_status;
											if(value == '0'){
												g_status = '<img src="images/no-flag.png" title="上架" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + giftId + ',1)">'
											}
											else{
												g_status = '<img src="images/ok-flag.png" title="下架" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + giftId + ',0)">'
											}
											return detail + edit + g_status;
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
	});

	// 跳转详情页面
	function toShow(index) {
		window.location.href = global_ctxPath + '/toShow?id=' + index;
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
			url : global_ctxPath + '/exchangeGift' ,
			dataType : 'json',
			data : {
				'id' : index,
				'g_status' : status
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
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height:45px;padding:10px 14px 0px 14px;background:#ffffff;overflow:hidden;">
			<span>活动内容：</span> <input id="content" class="textbox"
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
					<td align="left"><a href="#" onclick="toAdd();" data-options="iconCls:'icon-ok'"
						 class="easyui-linkbutton" plain="false">
						新增
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>