<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>EasyUI from</title>
<%@include file="/common/common-css.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/editor/themes/default/default.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/editor/plugins/code/prettify.css" />

<script charset="utf-8"
	src="<%=request.getContextPath()%>/editor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/editor/lang/zh-CN.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/editor/plugins/code/prettify.js"></script>
<script>
	function groupList(type) {
		window.location.href = global_ctxPath + "/grouplist1?type=" + type;
	}

	//返回
	function back() {
		location.href = global_ctxPath + "/totasklist";
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px; padding-top: 20px; padding-right: 4px; background: #ffffff; overflow: hiadden;">
			<form id="detail" name="detail" method="post"
				enctype="multipart/form-data">
				<table class="ttab" height="400px" width="80%" border="0"
					cellpadding="0" cellspacing="1" align="center"
					style="margin-top: 30px">

					<tr>
						<td style="text-align: center; width: 40%; height: 30px">任务类型</td>
						<td  style="text-align: center;">绑定群名称</td>
						<td>操作</td>
					</tr>
					<c:forEach items="${list}" var="taskType">
						<tr>
							<td style="text-align: center; width: 40%; height: 30px">${taskType.type}</td>
							<td>${taskType.groupName}</td>
							<td><img src="images/view-flag.png" title="选择群" width="20px"
								style="cursor: pointer;" height="20px" onclick="groupList(${taskType.id})"></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="4" style="padding: 10px">
							<div align="center">
								<input type="button" value="　返　回　" class="input_btn_style1"
									onclick="back();">
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<iframe src="" name="temp_upload_frame" id="temp_upload_frame"
			style="display: none;"></iframe>
	</div>
</body>
</html>