<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	//跳转到列表页面
	function backlist() {
		window.location.href = global_ctxPath + '/activitieslist';
	}

	//跳转到参加活动的用户列表页面
	function joinPerson(index) {
		window.location.href = global_ctxPath + '/joinpersonlist?id=' + index;
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
							window.location.reload(true);
						} else {
							divfloat('处理失败', 160, 40);
						}
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
							window.location.reload(true);
						} else {
							divfloat('处理失败', 160, 40);
						}
					}
				});
			}
		});
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 20px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table class="ttab" height="400px" width="80%" border="0"
				cellpadding="0" cellspacing="1" align="center"
				style="margin-top: 30px">
				<tr>
					<td style="text-align: right; width: 15%;">发起人：</td>
					<td>${activities.stuUser }</td>
					<td style="width: 30%;">限制名额：${activities.maxCount }&nbsp;&nbsp;已参与：${activities.joinCount }&nbsp;&nbsp;<input
						type="button" class="easyui-linkbutton"
						style="padding: 3px 5px 3px 5px;"
						onclick="joinPerson('+${activities.id }+')" value="查 看" /></td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;">活动类型：</td>
					<td colspan="2">${activities.type }</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;">活动地点：</td>
					<td colspan="2">${activities.location }</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;">活动时间：</td>
					<td colspan="2">${activities.startTime }~${activities.endTime }</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;">活动内容：</td>
					<td colspan="2">${activities.content }</td>
				</tr>
				<tr>
					<td colspan="3" style="text-align: center;"><img
						src="${activities.picture }" width="300px" height="200px" /></td>
				</tr>
				<tr>
					<td colspan="3" style="text-align: center;">
					<c:if
							test="${activities.status==0 or activities.status==3}">
							<input type="button" class="easyui-linkbutton"
								style="padding: 5px 5px 5px 5px;"
								data-options="iconCls:'icon-ok'"
								onclick="activitiesUpdateToYes('${activities.id }')"
								value="审核通过" />&nbsp;&nbsp;<input type="button"
								class="easyui-linkbutton" data-options="iconCls:'icon-no'"
								style="padding: 5px 5px 5px 5px;"
								onclick="activitiesUpdateToNo('${activities.id }')" value="审核拒绝" />
						</c:if>
						<c:if test="${activities.status==1}">
						    <input type="button"
								class="easyui-linkbutton" data-options="iconCls:'icon-no'"
								style="padding: 5px 5px 5px 5px;"
								onclick="activitiesUpdateToNo('${activities.id }')" value="审核拒绝" />
						</c:if>
						<c:if
							test="${activities.status==2}">
							<input type="button" class="easyui-linkbutton"
								style="padding: 5px 5px 5px 5px;"
								data-options="iconCls:'icon-ok'"
								onclick="activitiesUpdateToYes('${activities.id }')"
								value="审核通过" />
						</c:if>
						<input type="button" class="easyui-linkbutton"
						style="padding: 5px 10px 5px 10px;" onclick="backlist()"
						value="返   回" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>