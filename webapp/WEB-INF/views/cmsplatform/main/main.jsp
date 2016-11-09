<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/easyui/metro/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/common/common.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/ztree/zTreeStyle.css"
	type="text/css">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/jquery-2.0.3.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript">
			var global_ctxPath = '<%=request.getContextPath()%>';
	</script>
	<script type="text/javascript">
		$(function() {
			$.fn.extend({
				showtime : function() {
					var today = new Date();
					var year = today.getFullYear();//年
					var month = format_time(today.getMonth() + 1);//月
					var day = format_time(today.getDate());//日
					var hour = format_time(today.getHours());//时
					var min = format_time(today.getMinutes());//分
					var sec = format_time(today.getSeconds());//秒
					var w = today.getDay();//周
					var week = '';//星期
					switch (w) {
					case 0:
						week = "星期日";
						break;
					case 1:
						week = "星期一";
						break;
					case 2:
						week = "星期二";
						break;
					case 3:
						week = "星期三";
						break;
					case 4:
						week = "星期四";
						break;
					case 5:
						week = "星期五";
						break;
					case 6:
						week = "星期六";
						break;
					}
					$('#time').html(
							'现在是&nbsp;' + year + '-' + month + '-' + day
									+ '&nbsp;' + hour + ':' + min + ':' + sec
									+ '&nbsp;' + week);
					setTimeout('$(this).showtime()', 100);
				}
			});
			$(this).showtime();
		});

		function format_time(t) {
			return t >= 10 ? t : '0' + t;
		}
	</script>
</head>

<body class="easyui-layout">
	<div region="north" border="true" split="false"
		style="overflow: hidden; height: 80px;collapsed:true">
		<div class="top-bg">
			<div data-options="region:'north',border:false" class="topbg"
				style="height:80px;padding:10px 10px 10px 10px; background-color: #222222;">
				<div class="top_menu">
					<div class="left">
						<span style="color: white; height: 150px;"><h1>原助后台管理系统</h1></span>
					</div>
					<div class="right">
						<ul style="color: #9D9D9D;">
							<li><a href="<%=request.getContextPath()%>/logout"
								style="color: #9D9D9D;">退出</a></li>
							<li>您好&nbsp;${currentUser.name}</li>
						</ul>
						<div class="header"
							style="color: #9D9D9D; float: right; margin-top: -10px; margin-right: 8px;">
							<div id="time"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- <div region="south" border="true" split="false" style="overflow: hidden; height: 40px;collapsed:true" >
	        <div class="footer" border="true" align="center">版权所有：蓝色互动</div>
	    </div> -->
	<div region="west" border="true" split="false" title="菜单导航"
		style="width: 200px;collapsed:true;">
		<div style="margin:10px 0;">
			<menus:leftMenu />
		</div>
	</div>
	<div id="mainPanle" region="center" style="overflow: hidden;">
		<iframe id="mainframe" name="mainframe" src="" frameborder="0"
			height="100%" width="100%" allowTransparency="true" align="center"
			scrolling="auto"></iframe>
	</div>
</body>

</html>