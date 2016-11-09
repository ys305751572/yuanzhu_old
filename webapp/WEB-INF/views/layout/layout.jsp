<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>原助后台管理系统</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.7.2/jquery.min.js${tagVersion}"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.json-2.4.min.js${tagVersion}"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/idangerous.swiper-2.1.min.js${tagVersion}"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.dialog.js${tagVersion}"></script>
<script type="text/javascript">
	var global_ctxPath = '<%=request.getContextPath()%>';
	
	function f_dojump(href){
		location.href = global_ctxPath + href;
	}
	
	function f_doformget(action, method, params){
		var form = $('<form></form>').attr({
			action : global_ctxPath + action,
			method : method
		}).css('display','none').appendTo('body');
		if(params != null){
			for(var username in params){
				$("<input />").attr({name:username,value:params[username]}).appendTo(form);
			}
		}
		form.submit();
		form.remove();
	}
</script>
</head>
<body>
	<tiles:insertAttribute name="main" />
</body>
</html>