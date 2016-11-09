<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
<!--

//-->
	function goBack() {
		window.location.href = global_ctxPath + '/grouplist?pages=' + ${group.pageNumber };
	}

</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 40px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table class="ttab" height="250px" width="80%" border="0"
			cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td style="width: 15%; text-align: right;">群号：</td>
					<td>${group.id }</td>
					<td style="width: 15%; text-align: right;">群名称：</td>
					<td>${group.name }</td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">群规模：</td>
					<td>${group.gscale }</td>
					<td style="width: 15%; text-align: right;">类型：</td>
					<td>${group.gtype }</td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">省份：</td>
					<td><c:if test="${group.province eq '0'}"></c:if><c:if test="${group.province ne '0'}">${group.province}</c:if></td>
					<td style="width: 15%; text-align: right;">城市：</td>
					<td><c:if test="${group.city eq '0'}"></c:if><c:if test="${group.city ne '0'}">${group.city}</c:if></td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">爱心币：</td>
					<td>${group.coin }</td>
					<td style="width: 15%; text-align: right;">群主：</td>
					<td>${group.stuUser }</td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">入群人数：</td>
					<td colspan="3">${group.joincount}</td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">群公告：</td>
					<td colspan="3">${group.notice }</td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: right;">群寄语：</td>
					<td colspan="3">${group.message }</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="4"><input
						type="button" class="easyui-linkbutton"
						style="padding: 3px 5px 3px 5px;" onclick="goBack()" value="返 回" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>