<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/webcss/information.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/webcss/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/webcss/wang.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
function dologin(){
	var name= $("#name").val();
	var pwds= $("#pwds").val();
	var check=0;
	var chk=$('#chk').is(':checked');
	if(chk){
		check=1;
	}
	if(""==name){
		alert("用户名不能为空");
		return;
	}if(""==pwds){
		alert("密码不能为空");
		return;
	}else{
		$.ajax({
			type : "post",
			async : false,
			url : '<%=request.getContextPath()%>/web/dologin',
			data : {
				"name" : name,
				"pwds" : pwds,
				"check" : check
			},
			dataType : "json",
			success : function(result) {
				if(result=="1"){
					window.location.href="<%=request.getContextPath()%>/homepage/list";
				}else {
					alert(result);
				}
			}
		});
}
}
</script>
<div class=" cont_box" style="height: 600px;">
	<div class="login_box">
		<div class="use_box">
			<ul>
				<li>
					<div class="useName">用户名</div>
					<div class="use_inp">
						<input id="name" type="text">
					</div></li>
				<li>
					<div class="passWord">密 码</div>
					<div class="use_inp">
						<input id="pwds" type="password">
					</div></li>
			</ul>
		</div>
		<div class="check_box">
			<input id="chk" type="checkbox"> 下次自动登录
		</div>
			<div class="w-btn" onclick="dologin();">登 录</div>
	</div>
</div>


