<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables').datagrid({
			url : global_ctxPath + '/allreport',
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
			columns : [ [ {
				field : 'id',
				title : '举报编号',
				width : 20,
				align : 'center',
			}, {
				field : 'content',
				title : '举报内容',
				width : 60,
				align : 'center'
			}, {
				field : 'name',
				title : '举报人',
				width : 20,
				align : 'center'
			}, {
				field : 'time',
				title : '举报时间',
				width : 30,
				align : 'center'
			} ] ]
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
		
		$('#endDate').datebox({
			onSelect : function(date) {
				var startTime = $('#startDate').datebox('getValue'); // 获取开始日期输入框的值
				var endTime = $('#endDate').datebox('getValue'); // 获取截止日期输入框的值
				if (startTime > endTime) {
					$('#endDate').datebox('setValue', startTime);
					divfloat('截止日期不能大于开始日期！', 250, 40);
				}
			}
		});

		$('#startDate').datebox({
			onSelect : function(date) {
				var startTime = $('#startDate').datebox('getValue'); // 获取开始日期输入框的值
				var endTime = $('#endDate').datebox('getValue'); // 获取截止日期输入框的值
				if (startTime > endTime && endTime.length > 0) {
					$('#startDate').datebox('setValue', endTime);
					divfloat('开始日期不能大于截至日期！', 250, 40);
				}
			}
		});
	});

	function doSearch() {
		$('#tables').datagrid("load", {
			startDate : $('#startDate').datebox('getValue'),
			endDate : $('#endDate').datebox('getValue'),
			content : $('#content').val(),
			stuUser : $('#stuUser').val()
		});
	}

	function resetValue() {
		$('#startDate').datebox('setValue', '');
		$('#endDate').datebox('setValue', '');
		$('#content').val('');
		$('#stuUser').val('');
	}
	
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	}
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
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height:45px;padding:10px 14px 0px 14px;background:#ffffff;overflow:hidden;">
			<span>举报人：</span> <input id="stuUser" class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" />
			<span>举报内容：</span> <input id="content" class="textbox"
				style="line-height:16px;border:1px solid #ccc;
							width:116px; height:20px;" />
			<span>开始日期：</span> <input id="startDate" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser"
				name="startDate" editable="fasle"
				style="line-height:16px;border:1px solid #ccc;
							width:120px; height:20px;" />
			<span>截止日期:</span> <input id="endDate" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser" name="endDate"
				editable="fasle"
				style="line-height:16px;border:1px solid #ccc; width:120px;
							height:20px;" />
			<a href="javascript:void(0)" style="width: 60px;" data-options="iconCls:'icon-search'"
				class="easyui-linkbutton" plain="false" onclick="doSearch()">查询</a>
			<a href="#" style="width: 60px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'"
				plain="false" onclick="resetValue()">重置</a>
		</div>
		<div data-options="region:'center',border:false"
			style="padding-left: 4px;padding-top: 0px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table id="tables" class="easyui-datagrid"
				style="width:inherit;height:auto; text-align: center;"
				pagination="true">

			</table>
		</div>
	</div>
</body>