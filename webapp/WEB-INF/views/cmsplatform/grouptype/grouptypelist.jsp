<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	var updateId = 0;
	$(function() {
		$("#grouptypeName").focus().keydown(function(event) {
			if (event.which == 13) {
				grouptypeAddorUpdate();
			}
		});
		
		//对话框初始化
		$('#addOrupdateGroupType').dialog({
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
				handler : grouptypeAddorUpdate
			}, {
				text : '取消',
				handler : function() {
					$('#addOrupdateGroupType').dialog('close');
				}
			} ],
			onClose : function() {
				$("#grouptypeName").val('');
			}
		});
	});

	function addOrupdateGroupType(type, id, name) {
		if (type == 'add') {
			$('#addOrupdateGroupType').dialog('setTitle', '新增');
			$("#grouptypeId").html('请输入兴趣爱好名称：');
		} else {
			updateId = id;
			$('#addOrupdateGroupType').dialog('setTitle', '修改--' + name);
			$("#grouptypeId").html('请输入新的兴趣爱好名称：');
		}
		$('#addOrupdateGroupType').dialog('open');
		$("#grouptypeName").focus();
	}

	function grouptypeAddorUpdate() {
		var flag = "sdfsdfsdf";

		if (updateId == "0") {
			flag = '新增';
		} else {
			flag = '修改';
		}

		var name = $("#grouptypeName").val().trim();

		if (name.replace(/\s/gi, '').length < 1) {
			divfloat(flag + '群类型名不能为空', 250, 40);
			$('#grouptypeName').focus();
			return false;
		}
		var lenname = name.replace(/[^\x00-\xff]/g, "**").length;
		if (lenname > 10) {
			divfloat('群类型名过长', 250, 40);
			$('#grouptypeName').focus();
			return;
		}

		$.ajax({
			type : "post",
			async : false,
			url : global_ctxPath + '/addOrupdateGroupType',
			data : {
				"id" : updateId,
				"name" : name
			},
			dataType : "json",
			success : function(result) {
				if (result == "1") {
					$('#addOrupdateGroupType').dialog('close');
					$("#grouptypeName").val('');
					divfloat(flag + '成功', 160, 40);
					updateId = 0;
					window.location.reload(true);
				} 
				else if(result == "-1"){
					divfloat("此类型已经存在，请重新输入",250,40);
				}
				else {
					divfloat(flag + '失败', 160, 40);
				}
			}
		});
	}

	//批量删除
	function deleteGroupTypes() {
		var ary = [];
		$('input:checkbox:checked').each(function() {
			ary.push($(this).val());
		});

		if (ary.length > 0) {
			$.messager.confirm('提示', '你确定将所有选中项删除？', function(r) {
				if (r) {
					gtypeDeletes(ary + ',');
				}
			})
		} else {
			divfloat('未选择任何一项', 160, 40);
		}
	}

	//批量删除方法
	function gtypeDeletes(ary) {
		$.ajax({
			type : 'post',//传输方式
			cache : false,
			url : global_ctxPath + '/deleteGroupTypes',//路径
			dataType : 'json',
			data : {
				"ary" : ary
			},
			success : function(data) {
				if (data > 0) {
					divfloat('操作成功', 160, 40);
					window.location.reload(true);
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
	<div style="border: 1px; padding-left: 80px; padding-top: 20px;">
		<div style="width: 93%;border: solid 1px #000000;">
			<table style="width: 100%;">
				<tr style="background-color: #E4E4E4; height: 35px;">
					<td
						style="text-align: center; color: #333333; font-size: 16px; padding-left: 100px;"
						colspan="5">群类型</td>
					<td style="text-align: right;"><a href="#"
						data-options="iconCls:'icon-add'" onclick="addOrupdateGroupType('add','${n.id }','${n.name }')"
						style="width: 80px;" class="easyui-linkbutton" plain="false">添加类型</a><a
						href="#" data-options="iconCls:'icon-remove'"
						onclick="deleteGroupTypes()"
						style="width: 60px; margin-left: 2px; margin-right: 5px;"
						class="easyui-linkbutton" plain="false">删除</a></td>
				</tr>
				<tr>
					<c:forEach var="n" items="${grouptypeList }" varStatus="status">
						<td style="padding-top: 10px; padding-left: 50px;"><input
							type="checkbox" name="checkbox" id="${n.id }"
							style="vertical-align: middle;" value="${n.id }" /> <a href="#"
							style="text-decoration: none; color: #333333;" title="点击编辑"
							onclick="addOrupdateGroupType('edit','${n.id }','${n.name }')">${n.name }</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<c:if test="${status.count%6==0}">
				</tr>
				<tr>
					</c:if>

					</c:forEach>
				</tr>
			</table>
		</div>
	</div>

	<div id="addOrupdateGroupType">
		<table style="margin-left: 15px; margin-top: 30px;">
			<tr>
				<td><span id="grouptypeId">请输入名称：</span></td>
				<td><input id="grouptypeName" type="textbox" /></td>
			</tr>
		</table>
	</div>
</body>