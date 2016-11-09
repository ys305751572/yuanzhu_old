<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/login.css" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/webcss/information.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/webcss/search_results.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/webcss/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/webcss/wang.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/pagination.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jpaginate.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/webjs/w_index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.pagination.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.paginate.js"></script>


<title></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
</head>
<body>
	<div class="main">
		<div class="webheader">
			<tiles:insertAttribute name="header" />
		</div>
		<!-- end of header -->
		<div class="clear"></div>
		<div style="width:1100px;margin:10px auto 0;">
			<div class="left-ban">
				<tiles:insertAttribute name="left" />
			</div>
			<!-- end of left-ban -->
			<div class="webcenter-cont">
				<tiles:insertAttribute name="main" />
			</div>
		</div>
		<!-- end of center-cont -->
		<div class="clear"></div>
		<div class="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>