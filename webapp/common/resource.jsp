<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tablestyle.css"/>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/style.css"/> --%>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery_1_7_2_min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/divfloat.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.json-2.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/uploadPreview.min.js"></script>
<script type="text/javascript">
	var global_ctxPath = '<%=request.getContextPath()%>';
	var xx_path = '<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>';
	$.messager.defaults = { ok: "确定", cancel: "取消" };
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
