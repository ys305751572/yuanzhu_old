<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common-css.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#userName").focus().keydown(function(event) {
			if (event.which == 13) {
				$("#password").focus();
			}
		});
		
		$("#password").focus().keydown(function(event) {
			if (event.which == 13) {
				$("#realName").focus();
			}
		});
		
		$("#realName").focus().keydown(function(event) {
			if (event.which == 13) {
				$("#mobile").focus();
			}
		});

		$("#mobile").keydown(function(event) {
			if (event.which == 13) {
				tijiao();
			}
		});
	});

	function tijiao() {
		var username = $('#userName').val();
		var realname = $('#realName').val();
		var password = $('#password').val();
		var mobi = $('#mobile').val();
		
		var provinceId = $("#province").combobox("getValue");
		var cityId = $("#city").combobox("getValue");
		var schoolId = $("#school").combobox("getValue");
		
		if (null == username || username == '') {
			divfloat('登录名不能为空', 160, 40);
			$('#userName').focus();
			return;
		}
		if (null == realname || realname == '') {
			divfloat('用户姓名不能为空', 200, 40);
			$('#realName').focus();
			return;
		}
		if (null == password || password == '') {
			divfloat('密码不能为空', 200, 40);
			$('#password').focus();
			return;
		}
		var lenusername = username.replace(/[^\x00-\xff]/g, "**").length;
		if (lenusername > 10) {
			divfloat('登录名过长', 160, 40);
			$('#userName').focus();
			return;
		}
		var lenRealname = realname.replace(/[^\x00-\xff]/g, "**").length;
		if (lenRealname > 10) {
			divfloat('用户姓名过长', 160, 40);
			flag = false;
			$('#realName').focus();
			return;
		}
		if (null == mobi || mobi == '') {
			divfloat('手机号码不能为空', 200, 40);
			$('#mobile').focus();
			return;
		}
		if (isNaN(mobi) || (mobi.length != 11)) {
			divfloat('手机号码为11位数字！请正确填写', 300, 40);
			$('#mobile').focus();
			return;
		}
		var reg = /^0{0,1}(13[0-9]|15[0-9])[0-9]{8}$/;
		if (!reg.test(mobi)) {
			divfloat('您的手机号码不正确，请重新输入', 300, 40);
			$('#mobile').focus();
			return;
		}
		var reg2 = /^[a-zA-Z0-9]\w{5,11}$/;
		if (!reg2.test(password)) {
			divfloat('密码必须为6-12位，且只能包含数字和字母', 400, 50);
			$('#password').focus();
			return;
		}
		var type = $("input[name='type']:checked").val();
		$.ajax({
			type : 'post',
			async : false,
			cache : false,
			url : global_ctxPath + '/addUser',
			dataType : 'json',
			data : {
				"userName" : username,
				"realName" : realname,
				"password" : password,
				"mobile" : mobi,
				"userType" : type,
				"provinceId" : provinceId,
				"cityId":cityId,
				"schoolId": schoolId
				
			},
			success : function(data) {
				if (data == '1') {
					divfloat('新增成功', 160, 40);
					returnBack();
				} 
				else if(data == '-1'){
					divfloat('您输入的登录名已经存在，请重新输入', 350, 40);
				}
				else {
					divfloat('新增失败', 160, 40);
				}
			}
		});
	}
	function returnBack() {
		location.href = global_ctxPath + '/userlist';
	}
	
	function checkUserType(type) {
		if(type == 4) {
			$("#trSchool").css("display","");
			loadProvince();
		}
		else {
			$("#trSchool").css("display","none");
		}
	}
	
	function loadProvince() {
		$('#province').combobox({
			url : global_ctxPath + '/findProvinceByParamsList',
			valueField : "id",
			textField : "name",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#province').combobox('getData');
				if (data.length > 0) {
					$('#province').combobox("select", data[0].id);
				}
			},
			onSelect : function() {
				loadCity();
			}
		});
	}

	// 加载城市信息
	function loadCity() {
	// 加载城市信息
		$('#city').combobox(
		{
			url : global_ctxPath + '/findCityByParamsList?provinceId='
					+ $("#province").combobox("getValue"),
			valueField : "id",
			textField : "name",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onSelect : function() {
				//加载学校信息
				loadSchool();
			},
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#city').combobox('getData');
				if (data.length > 0) {
					$('#city').combobox("select", data[0].id);
				}
			}
		});
	}

	// 加载学校信息
	function loadSchool() {
		$('#school').combobox(
		{
			url : global_ctxPath
					+ '/findSchoolByParamsList?provinceId='
					+ $("#province").combobox("getValue") + '&cityId='
					+ $("#city").combobox("getValue") + '&id='
					+ $("#school").combobox("getValue"),
			valueField : "id",
			textField : "proName",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#school').combobox('getData');
				if (data.length > 0) {
					$('#school').combobox("select", data[0].id);
				}
			}
		});
	}
</script>
</head>

<body>
	<div style="height: 100%;overflow-y: auto;">
		<br /> <br /> <br />
		<h2 style="text-align: center; color: red; font-size: 30px;">添加用户</h2>
		<form>
			<table class="ttab" height="100" width="85%" border="1"
				cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">登录名<span style="color: red;">*</span>：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" name="userName"
								class="textbox" id="userName" type="text" />
						</div>
					</td>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">密码<span style="color: red;">*</span>：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" id="password"
								class="textbox" name="password" type="password" />
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">
							用户姓名<span style="color: red;">*</span>：
						</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" id="realName"
								class="textbox" name="realname" type="text" />
						</div>
					</td>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">
							手机号码<span style="color: red;">*</span>：
						</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" id="mobile"
								class="easyui-numberbox textbox" name="mobile" />
						</div>
					</td>
				</tr>
				<tr id="trSchool" style="display: none;">
					<td height="30" width="15%">
						<div align="right" class="STYLE1">学校：</div>
					</td>
					<td colspan="3" align="left">
						<div align="left" class="STYLE1" style="padding-left:10px;">
						<label style="cursor:pointer" for="systemuser">省份：</span> </label>
						<select id="province" class="easyui-combobox" name="province" style="width:100px;" data-options="editable:false">
						</select> 
						<label style="cursor:pointer" for="systemuser">城市：</span></label>
						<select id="city" class="easyui-combobox" name="city" style="width:100px;" data-options="editable:false">
						</select>
					    <label style="cursor:pointer" for="systemuser">学校：</span></label>
					    <select id="school" class="easyui-combobox" name="school" style="width:100px;" data-options="editable:false">
						</select>
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">用户类型：</div>
					</td>
					<td colspan="3" align="left">
						<input type="radio" id="systemuser" name="type" value="2" onclick="checkUserType(2)"/><label style="cursor:pointer" for="systemuser">系统管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" id="normaluser" checked="checked" name="type" onclick="checkUserType(1)" value="1" />
						<label style="cursor:pointer" for="normaluser" >普通管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<input type="radio" id="systemuser" name="type" value="3" onclick="checkUserType(3)"/>
						<label style="cursor:pointer" for="systemuser">任务管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<input type="radio" id="systemuser" name="type" value="4" onclick="checkUserType(4)"/>
						<label style="cursor:pointer" for="systemuser">咨询管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<input type="radio" id="systemuser" name="type" value="5" onclick="checkUserType(5)"/>
						<label style="cursor:pointer" for="systemuser">活动管理员</label>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="padding: 10px">
						<div align="center">
							<input type="button" value="　提　交　" class="input_btn_style1"
								onclick="tijiao();"> &nbsp;&nbsp;&nbsp;&nbsp; <input
								type="button" value="　返　回　" class="input_btn_style1"
								onclick="returnBack();">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>