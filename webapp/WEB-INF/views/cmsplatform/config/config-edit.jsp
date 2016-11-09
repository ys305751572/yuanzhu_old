<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
		
	function doSubmit() {
		var coefficient = $('#coefficient').val();
		var coin = $("#coin").val();
		var coinRegister = $("#coinRegister").val();
		var coinPerson = $("#coinPerson").val();
		var coinCard = $("#coinCard").val();
		var coinUs = $("#coinUs").val();
		var coinRec = $("#coinRec").val();
		
		if(coefficient == null || coefficient.trim() == "") {
			divfloat('系数不能为空', 160, 40);
			return;
		}
		if(coin == null || coin.trim() == "") {
			divfloat('爱佑币-人民币比例不能为空', 160, 40);
			return;
		}
		if(coinRegister == null || coinRegister.trim() == "") {
			divfloat('注册奖励不能为空', 160, 40);
			return;
		}
		if(coinPerson == null || coinPerson.trim() == "") {
			divfloat('完善奖励不能为空', 160, 40);
			return;
		}
		if(coinCard == null || coinCard.trim() == "") {
			divfloat('学生证认证奖励不能为空', 160, 40);
			return;
		}
		if(coinUs == null || coinUs.trim() == "") {
			divfloat('推荐奖励-自己奖励不能为空', 160, 40);
			return;
		}
		if(coinRec == null || coinRec.trim() == "") {
			divfloat('推荐奖励-他人奖励不能为空', 160, 40);
			return;
		}
		
		$("form").submit();
	}
	
	function goBack() {
		window.location.href = global_ctxPath + "/config/detailPage";
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 20px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<form action="${contextPath}/config/edit" method="post">
				<table class="ttab" height="100%" width="80%" border="0"
				cellpadding="0" cellspacing="1" align="center">
					<input type="hidden" id="id" name="id" value="${config.id}">
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">爱佑币奖励系数<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="coefficient" name="coefficient" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.coefficient }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">爱佑币-人民币比例<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="coin" name="coin" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.coin }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">注册奖励<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="coinRegister" name="coinRegister" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.coinRegister}">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">完善个人信息奖励<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="coinPerson" name="coinPerson" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.coinPerson }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">学生证认证奖励<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="coinCard" name="coinCard" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.coinCard }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">他人协助认证-自己<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="coinUs" name="coinUs" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.coinUs }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">他人协助认证-他人<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="coinRec" name="coinRec" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.coinRec }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">发起人奖励纠纷<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="integralRelease" name="integralRelease" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.integralRelease }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">接收人奖励积分<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="integralAccept" name="integralAccept" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.integralAccept }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">发起人惩罚积分<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="integralReleaseDeduct" name="integralReleaseDeduct" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.integralReleaseDeduct }">
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%;height: 30px">接收人惩罚积分积分<span style="color: red;">*</span>：</td>
						<td colspan="2">
							<input id="integralAcceptDeduct" name="integralAcceptDeduct" style="height: 20px;width: 200px" class="textbox" type="text" value="${config.integralAcceptDeduct }">
						</td>
					</tr>
					<tr>
						<td style="text-align: center;" colspan="2"><input
							type="button" class="easyui-linkbutton"
							style="padding: 3px 5px 3px 5px;" onclick="doSubmit()" value="保 存" />
							&nbsp;&nbsp; 
						    <input
							type="button" class="easyui-linkbutton"
							style="padding: 3px 5px 3px 5px;" onclick="goBack()" value="返回" />
						     	
					    </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>