<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common-css.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#Edit_dialog').dialog({
			title : '修改密码',
			top : 140,
			width : 280,
			height : 160,
			closed : true,
			cache : false,
			modal : true,
			closable : true,
			resizable : false,
			buttons : [ {
				text : '提交',
				handler : updatePassword
			} ]
		});
		
		if(${user.userType} == 4) {
			$("#trSchool").css("display",'');
		}
		
		loadProvince();
		
		$("#province").combobox("select", ${user.provinceId});
		$("#city").combobox("select",  ${user.cityId});
		$("#school").combobox("select", ${user.schoolId});
	});

	function loadProvince() {
		$('#province').combobox({
			url : global_ctxPath + '/findProvinceByParams',
			valueField : "id",
			textField : "name",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onSelect : function() {
				$('#city').combobox('clear');
				loadCity();
			}
		});
	}

	function loadCity() {
		$('#city').combobox(
				{
					url : global_ctxPath + '/findCityByParams?provinceId='
							+ $("#province").combobox("getValue"),
					valueField : "id",
					textField : "name",
					editable : false,
					width : 150,
					multiple : false,
					required : false,
					dataType : 'json',
					onSelect : function() {
						$('#school').combobox('clear');
						// 加载学校信息
						loadSchool();
					}
				});
	}

	function loadSchool() {
		$('#school').combobox(
				{
					url : global_ctxPath + '/findSchoolByParams?provinceId='
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
					onSelect : function() {
						$('#college').combobox('clear');
					}
				});
	}
	
	function modifyPassword() {
		$('#Edit_dialog').dialog('open');
	};

	function updatePassword() {
		var oldPassword = $('#oldPassword').val();
		var newPassword = $('#newPassword').val();
		var newPasswordQ = $('#newPasswordQ').val();
		
		if (null == oldPassword || oldPassword == '') {
			divfloat('请输入原密码', 250, 40);
			return;
		}
		if (null == newPassword || newPassword == '') {
			divfloat('请输入新密码', 250, 40);
			return;
		}
		if (null == newPasswordQ || newPasswordQ == '') {
			divfloat('请输入确认密码', 250, 40);
			return;
		}
		if (oldPassword == newPassword) {
			divfloat('新密码和原密码不能一致，请重新输入', 350, 40);
			return;
		}
		if (newPassword != newPasswordQ) {
			divfloat('新密码和确认密码不一致，请重新输入', 350, 40);
			return;
		}
		var reg = /^[a-zA-Z0-9]$/;
		if (!reg.test(oldPassword) || !reg.test(newPassword)) {
			divfloat('新密码必须为6-12位', 250, 40);
			return;
		}
		$.ajax({
			type : 'post',
			async : false,
			cache : false,
			url : global_ctxPath + '/modifyPassword',
			dataType : 'json',
			data : {
				"id" : ${user.id},
				"oldPassword" : oldPassword,
				"password" : newPassword
			},
			success : function(data) {
				if (data == '1') {
					divfloat('修改密码成功', 160, 40);
					$('#Edit_dialog').dialog('close');
					$('#oldPassword').val('');
					$('#newPassword').val('');
				} else {
					divfloat('修改密码失败', 160, 40);
				}
			}
		});
	};

	function tijiao() {
		var realname = $('#realName').val();
		var mobi = $('#mobile').val();
		
		var provinceId = $("#province").combobox("getValue");
		var cityId = $("#city").combobox("getValue");
		var schoolId = $("#school").combobox("getValue");
		if (null == realname || realname == '') {
			divfloat('用户姓名不能为空', 160, 40);
			return;
		}
		var lenRealname = realname.replace(/[^\x00-\xff]/g, "**").length;
		if (lenRealname > 10) {
			divfloat('用户姓名过长', 160, 40);
			$('#realName').focus();
			return;
		}
		if (null == mobi || mobi == '') {
			divfloat('手机号码不能为空', 160, 40);
			return;
		}
		if (isNaN(mobi) || (mobi.length != 11)) {
			divfloat('手机号码为11位数字！请正确填写', 300, 40);
			return;
		}
		var reg = /^0{0,1}(13[0-9]|15[0-9])[0-9]{8}$/;
		if (!reg.test(mobi)) {
			divfloat('您的手机号码不正确，请重新输入', 300, 40);
			return;
		}
		$.ajax({
			type : 'post',
			async : false,
			cache : false,
			url : global_ctxPath + '/updateUser',
			dataType : 'json',
			data : {
				"id" : ${user.id},
				"realName" : realname,
				"mobile" : mobi,
				"provinceId" : provinceId,
				"cityId" : cityId,
				"schoolId" : schoolId
			},
			success : function(data) {
				if (data == '1') {
					divfloat('修改成功', 160, 40);
					location.href = global_ctxPath + '/userlist';
				} else {
					divfloat('修改失败', 160, 40);
				}
			}
		});
	}
	function returnBack() {
		location.href = global_ctxPath + '/userlist';
	}
</script>
</head>

<body>
	<div style="height: 100%;overflow-y: auto;">
		<br /> <br /> <br />
		<h2 style="text-align: center; color: red; font-size: 30px;">编辑用户</h2>
		<form>
			<input type="hidden" name="id" value="${user.id}">
			<table class="ttab" height="100" width="85%" border="0"
				style="padding-top: 5px; padding-bottom: 5px;" cellpadding="0"
				cellspacing="1" align="center">
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">登录名：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" readonly="readonly"
								name="userName" id="userName" class="textbox"
								value="${user.userName}" />
						</div>
					</td>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">密码：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px;" id="password"
								class="textbox" name="password" type="password"
								value="${user.password}" readonly="readonly" /> <a
								href="javascript:void(0)" style="height: 20px;width: 100px;"
								class="easyui-linkbutton" onclick="modifyPassword()"
								plain="false">修改密码</a>
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
								class="textbox" name="realname" type="text"
								value="${user.realName}" />
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
								class="easyui-numberbox textbox" name="mobile"
								value="${user.mobile}" />
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
					<td colspan="3" align="center">
						<input type="radio" id="systemuser" name="type" disabled="disabled"
						<c:if test="${user.userType=='2' }">checked="checked"</c:if>
						value="2" /><label style="cursor:pointer" for="systemuser">系统管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
						<input type="radio" id="normaluser" name="type"
						disabled="disabled"
						<c:if test="${user.userType=='1' }">checked="checked"</c:if>
						value="1" /><label style="cursor:pointer" for="normaluser">普通管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<input type="radio" id="systemuser" name="type" disabled="disabled"
						<c:if test="${user.userType=='3' }">checked="checked"</c:if>
						value="2" /><label style="cursor:pointer" for="systemuser">任务管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<input type="radio" id="systemuser" name="type" disabled="disabled"
						<c:if test="${user.userType=='4' }">checked="checked"</c:if>
						value="2" /><label style="cursor:pointer" for="systemuser">咨询管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<input type="radio" id="systemuser" name="type" disabled="disabled"
						<c:if test="${user.userType=='5' }">checked="checked"</c:if>
						value="2" /><label style="cursor:pointer" for="systemuser">活动管理员</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
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

		<div id="Edit_dialog" class="easyui-dialog">
			<table style="padding: 5px;" cellspacing="0" cellpadding="0"
				border="0">
				<tr style="height: 25px;">
					<td width="60px">原密码：</td>
					<td><input type="password" class="textbox" name="oldPassword"
						id="oldPassword" /></td>
				</tr>
				<tr style="height: 25px;">
					<td width="60px">新密码：</td>
					<td><input type="password" class="textbox" name="newPassword"
						id="newPassword" /></td>
				</tr>
				<tr style="height: 25px;">
					<td width="60px">确认密码：</td>
					<td><input type="password" class="textbox" name="newPasswordQ"
						id="newPasswordQ" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>