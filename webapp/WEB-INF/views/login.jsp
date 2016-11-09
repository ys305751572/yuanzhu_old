<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>登录</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/common/login.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
			var global_ctxPath = '<%=request.getContextPath()%>';
</script>
<style type="">
</style>
<script type="text/javascript">
	$(function() {
		$("#j_username").focus().keydown(function(event) {
			if (event.which == 13) {
				$("#j_password").focus();
			}
		});

		$("#j_password").keydown(function(event) {
			if (event.which == 13) {
				$("#j_loginform").submit();
			}
		});

		if (self != top) {
			window.parent.location.href = global_ctxPath + "/login";
		}
	});

	function changeValidate() {
		alert("test");
		$("#validateCode").attr("src",
				global_ctxPath + "/captcha?abc=" + Math.random());
	}

	function newtrim(text) {
		var strTrim = text.replace(/(^\s*)|(\s*$)/g, "");
		strTrim = strTrim.replace(/^[\s　\t]+|[\s　\t]+$/, "");
		var strf = strTrim;
		strf = strf.replace(/(^\s*)|(\s*$)/g, "");
		strf = strf.replace(/^[\s　\t]+|[\s　\t]+$/, "");
		return strf;
	}

	function submitForm() {
		var credentials = {};
		var unv = $('#j_username').val() != "";
		var pwv = $('#j_password').val() != "";
		if (!unv) {
			$('#j_username').focus();
			return false;
		}
		if (!pwv) {
			$('#j_password').focus();
			return false;
		}
		$("#j_loginform").submit();
	}

	function closeMsg() {
		$("#error_msg").hide();
	}
</script>
</head>
<body>
	<div class="container-fluid main">
		<form:form modelAttribute="credentials" method="post"
			action="${ pageContext.request.contextPath}/login"
			class="form-signin" id="j_loginform">
			<h2 class="form-signin-heading">登录</h2>
			<c:if test="${not empty message }">
				<div class="alert alert-warning fade in alert-dismissable"
					id="error_msg">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true" onclick="closeMsg();">&times;</button>
					${message }
				</div>
			</c:if>
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td colspan="2"><form:input type="text" path="name"
							id="j_username" class="input-block-level" placeholder="用户名"
							tabindex="1" onblur="this.value=newtrim(this.value);" /></td>
				</tr>
				<tr>
					<td colspan="2"><form:input type="password"
							class="input-block-level" path="password" id="j_password"
							tabindex="2" placeholder="密码" /></td>
				</tr>
				<tr>
					<td><input type="checkbox" name="flag" value="true"
						style="vertical-align: middle; margin-top: 0px;" id="savePassword" /><label
						for="savePassword">自动登录</label></td>
					<td><button type="button" id="btn_1" class="btn btn-large btn-primary"
							tabindex="4" onclick="submitForm();">登录</button></td>
				</tr>
			</table>
		</form:form>
	</div>
	<!-- 		<div class="navbar navbar-fixed-bottom">©2013 XXX 版权所有</div> -->
</body>
</html>

