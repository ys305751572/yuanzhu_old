<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>

<script type="text/javascript">
	//跳转到列表页面
	function backlist() {
		window.location.href = global_ctxPath + '/activitieslist';
	}

	// 审核通过
	function activitiesUpdateToYes(id) {
		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		$.messager.confirm('提示', '确定将其认证为通过？', function(r) {
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
						window.location.href = global_ctxPath
								+ '/stuuserShow?id=' + ${stuuser.id};
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
						"status" : "2"
					},
					dataType : "json",
					success : function(result) {
						window.location.href = global_ctxPath
								+ '/stuuserShow?id=' + ${stuuser.id};
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
			<table class="ttab" height="100%" width="80%" border="0"
				cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td align="right" width="15%">呢称：</td>
					<td>${stuuser.nickname }</td>
					<td colspan="2" style="text-align: center;"><c:if
							test="${stuuser.status==0 }">
							<input type="button" class="easyui-linkbutton"
								style="padding: 1px 5px 1px 5px;"
								data-options="iconCls:'icon-ok'"
								onclick="activitiesUpdateToYes('${stuuser.id }')" value="认证通过" />&nbsp;&nbsp;<input
								type="button" class="easyui-linkbutton"
								data-options="iconCls:'icon-no'"
								style="padding: 1px 5px 1px 5px;"
								onclick="activitiesUpdateToNo('${stuuser.id }')" value="认证拒绝" />
						</c:if></td>
				</tr>
				<tr>
					<td align="right" width="15%">账号：</td>
					<td>${stuuser.mobile }</td>
					<td align="right" width="15%">姓名：</td>
					<td>${stuuser.name }</td>
				</tr>
				<tr>
					<td align="right" width="15%">手机：</td>
					<td>${stuuser.mobile }</td>
					<td align="right" width="15%">邮箱：</td>
					<td>${stuuser.email }</td>
				</tr>
				<tr>
					<td align="right" width="15%">生日：</td>
					<td>${stuuser.birthday }</td>
					<td align="right" width="15%">性别：</td>
					<td><c:if test="${stuuser.sex==0 }">男</c:if> <c:if
							test="${stuuser.sex==1 }">女</c:if></td>
				</tr>
				<tr>
					<td align="right" width="15%">入学年份：</td>
					<td>${stuuser.startYear }</td>
					<td align="right" width="15%">学校：</td>
					<td>${stuuser.school }</td>
				</tr>
				<tr>
					<td align="right" width="15%">学院：</td>
					<td>${stuuser.college }</td>
					<td align="right" width="15%">专业：</td>
					<td>${stuuser.specialty }</td>
				</tr>
				<tr>
					<td align="right" width="15%">兴趣：</td>
					<td>${stuuser.interest }</td>
					<td align="right" width="15%">标签：</td>
					<td>${stuuser.label }</td>
				</tr>
				<tr>
					<td align="right" width="15%">爱心币：</td>
					<td>${stuuser.coin }</td>
					<td align="right" width="15%">认证状态：</td>
					<td><c:if test="${stuuser.status==0 || stuuser.status ==3}">未认证</c:if> <c:if
							test="${stuuser.status==2 }">认证通过</c:if> <c:if
							test="${stuuser.status==1 }">认证失败</c:if></td>
				</tr>
				<tr>
					<td colspan="4"></td>
				</tr>
				<tr>
					<td align="right" width="15%">推荐人账号：</td>
					<td>${stuuser.recUserMobile }</td>
					<td align="right" width="15%">推荐人姓名：</td>
					<td>${stuuser.recUserName }</td>
				</tr>
				<tr>
					<td align="right" width="15%">系统存在与否：</td>
					<td colspan="3">是</td>
				</tr>
				<tr>
					<td colspan="4"></td>
				</tr>
				<tr>
					<td align="right" width="15%">学生证信息：</td>
					<td colspan="3"><img src="${stuuser.coverPic }" width="300px"
						height="200px" /> <img src="${stuuser.contentPic }" width="300px"
						height="200px" /></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="4"><input
						type="button" class="easyui-linkbutton"
						style="padding: 5px 5px 5px 5px;" onclick="backlist()" value="返 回" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>