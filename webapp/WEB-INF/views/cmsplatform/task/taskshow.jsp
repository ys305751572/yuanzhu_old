<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/editor/themes/default/default.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/editor/plugins/code/prettify.css" />
	
	<script charset="utf-8" src="<%=request.getContextPath()%>/editor/kindeditor-min.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath()%>/editor/lang/zh-CN.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath()%>/editor/plugins/code/prettify.js"></script>
	<script>
		 $(function() {
				//头像预览
				$("#pic").uploadPreview({
						Img : "imgs",
						Width : 180,
						Height : 180
				});
				
				$("#deletebtn").click(function(){
					document.getElementById("pic").value="";
					$("#imgs").attr("src","");
				});
			});
	    	
		 //返回
		function back(){
			location.href=global_ctxPath +"/totasklist";
		}
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 20px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
	    <form id="detail" name="detail" method="post" enctype="multipart/form-data">
	    	 <table class="ttab" height="400px" width="80%" border="0"
					cellpadding="0" cellspacing="1" align="center"
					style="margin-top: 30px">
			
            	<tr>
					<td style="text-align: right; width: 15%;height: 30px">发起人：</td>
					<td colspan="2">
						${task.release_user_name }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px"">任务类型：</td>
					<td colspan="2">
						${task.task_type_name }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">接单性别：</td>
					<td colspan="2">
						<c:if test="${task.task_sex eq 0}">
							不限
						</c:if>
						<c:if test="${task.task_sex eq 1}">
							男
						</c:if>
						<c:if test="${task.task_sex eq 2}">
							女
						</c:if>
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px"">活动地点：</td>
					<td colspan="2">
						${task.task_address}
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px"">截止时间：</td>
					<td colspan="2">
						${task.task_end_time}
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px"">任务标题：</td>
					<td colspan="2">
						${task.task_title}
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px"">任务内容：</td>
					<td colspan="2">
						${task.task_desc}
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px"">接单状态：</td>
					<td colspan="2">
						<c:if test="${task.accept_id eq 0}">
							未接单
						</c:if>
						<c:if test="${task.accept_id ne 0}">
							已接单
						</c:if>
					</td>
				</tr>
				<c:if test="${task.accept_id ne 0}">
					<tr>
						<td style="text-align: right; width: 15%;height: 30px"">接单人：</td>
						<td colspan="2">
							${task.accept_name }
						</td>
					</tr>
				</c:if>
				<tr>
					<td style="text-align: right; width: 15%;height: 180px"">活动图片：</td>
					<td colspan="2">
						<c:forEach items="${task.rep_list}" var="pic">
							<img src="${pic.url}" width="300px;" height="200px;" />
						</c:forEach>
					</td>
				</tr>
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