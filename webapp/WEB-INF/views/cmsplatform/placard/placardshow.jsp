<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
//跳转到列表页面
function placardlist() {
	window.location.href = global_ctxPath + '/placardlist';
}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 20px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table class="ttab" height="450px" width="80%" border="0"
			cellpadding="0" cellspacing="1" align="center" style="margin-top: 30px;">
				<tr>
					<td style="text-align: right;">公告编号：</td>
					<td style="text-align: left;">${placard.id }</td>
				</tr>
				<tr>
					<td style="text-align: right;">公告标题：</td>
					<td style="text-align: left;">${placard.title }</td>
				</tr>
				<tr>
					<td style="text-align: right;">公告类型：</td>
					<td style="text-align: left;">${placard.ptypeName }</td>
				</tr>
				<tr>
					<td style="text-align: right;">公告链接：</td>
					<td style="text-align: left;">${placard.linkUrl }</td>
				</tr>
				<tr>
					<td style="text-align: right;">公告内容：</td>
					<td style="text-align: left;">${placard.content }</td>
				</tr>
				<tr>
					<td style="text-align: right;">发布时间：</td>
					<td style="text-align: left;">${placard.time }</td>
				</tr>
				<tr>
					<td style="text-align: right;">编辑人：</td>
					<td style="text-align: left;">${placard.person }</td>
				</tr>
				<tr>
					<td style="text-align: right;">省份：</td>
					<td style="text-align: left;">${placard.province }</td>
				</tr>
				<tr>
					<td style="text-align: right;">城市：</td>
					<td style="text-align: left;">${placard.city }</td>
				</tr>
				<tr>
					<td style="text-align: right;">学校：</td>
					<td style="text-align: left;">${placard.school }</td>
				</tr>
				<tr>
					<td style="text-align: right;">图片：</td>
					<td style="text-align: left;"><img src="${placard.picture }" width="300px" height="200px" /></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input type="button" class="easyui-linkbutton" style="padding: 3px 5px 3px 5px;" onclick="placardlist()" value="返 回" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>