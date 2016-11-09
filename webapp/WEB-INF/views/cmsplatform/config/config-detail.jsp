<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	//跳转到列表页面
	function doEdit() {
		window.location.href = global_ctxPath + '/config/editPage';
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 20px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<form action="">
			<table class="ttab" height="100%" width="80%" border="0" cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">爱佑币奖励系数<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.coefficient }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">爱佑币-人民币比例<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.coin }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">注册奖励<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.coinRegister }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">完善个人信息奖励<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.coinPerson }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">学生证认证奖励<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.coinCard }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">他人协助认证-自己<span style="color: red;">*</span>：</td>
					<td colspan="2">
					 	${config.coinUs }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">他人协助认证-他人<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.coinRec }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">发起人奖励纠纷<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.integralRelease }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">接收人奖励积分<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.integralAccept }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">发起人惩罚积分<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.integralReleaseDeduct }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">接收人惩罚积分积分<span style="color: red;">*</span>：</td>
					<td colspan="2">
						${config.integralAcceptDeduct }
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2">
						<input type="button" class="easyui-linkbutton"
							style="padding: 3px 5px 3px 5px;" onclick="doEdit()" value="编辑" />
				    </td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>