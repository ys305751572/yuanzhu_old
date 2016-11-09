<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common-css.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
	});

	function returnBack() {
		location.href = global_ctxPath + '/xiu/listPage';
	}
</script>
</head>

<body>
	<div style="height: 100%;overflow-y: auto;">
		<br /> <br /> <br />
		<h2 style="text-align: center; color: red; font-size: 30px;">秀吧详情</h2>
		<form>
			<table class="ttab" height="100" width="85%" border="1"
				cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">秀吧名称：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${pb.title}
						</div>
					</td>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">秀吧简称：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${pb.shortName }
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">贴吧内容：</div>
					</td>
					<td colspan="3" align="left">
						${pb.content}
					</td>
				</tr>
				<tr>
					<td colspan="4" style="padding: 10px">
						<div align="center">
							 <input type="button" value="　返　回　" class="input_btn_style1"
								onclick="returnBack();">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>