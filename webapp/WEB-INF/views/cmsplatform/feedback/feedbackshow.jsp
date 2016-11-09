<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	//跳转到列表页面
	function backlist() {
		window.location.href = global_ctxPath + '/backlist';
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 40px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table class="ttab" height="150px" width="80%" border="0"
			cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td style="width: 15%; text-align: right;">反馈人：</td>
					<td>${back.person }</td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">反馈时间：</td>
					<td>${back.backtime }</td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">状态：</td>
					<td><c:if test="${back.status==1 }">未处理</c:if>
						<c:if test="${back.status==2 }">已处理</c:if></td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">反馈内容：</td>
					<td>${back.content }</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input
						type="button" class="easyui-linkbutton"
						style="padding: 5px 5px 5px 5px;" onclick="backlist()" value="返 回" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>