<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/bg/list',
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
										title : 'ID',
										width : 30,
										align : 'center',
									},
									{
										field : 'path',
										title : '图片地址',
										width : 100,
										align : 'center',
										formatter : function(value, row, index) {
											return '<img src="'+ value +'" width="100px" height="100px" style="cursor:pointer;" height="20px">';
										}
									},
									{
										field : 'createTime',
										title : '发布时间',
										width : 30,
										align : 'center',
										formatter : function(value, row, index) {
											return new Date(value).format("yyyy-MM-dd hh:mm")
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
											return '<img src="images/no-flag.png" title="查看" width="20px" style="cursor:pointer;" height="20px" onclick="delete1('
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
	//跳转到新增页面
	function add() {
		window.location.href = global_ctxPath + '/bg/add';
	}

	//删除操作
	function delete1(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('删除', '删除此装扮同时会删除关联用户装扮,是否确定删除此装扮？', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					async : false,
					url : global_ctxPath + '/bg/delete',
					data : {
						"id" : id
					},
					dataType : "json",
					success : function(result) {
						if (result == "0") {
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
<script type="text/javascript">
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, // month
            "d+": this.getDate(), // day
            "h+": this.getHours(), // hour
            "m+": this.getMinutes(), // minute
            "s+": this.getSeconds(), // second
            "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        ;
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        ;
        return format;
    };
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
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width: 100%">
				<tr>
					<td align="left">
						<a href="#" onclick="add()" data-options="iconCls:'icon-add'" class="easyui-linkbutton" plain="false">添加</a> 
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>