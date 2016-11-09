<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common-css.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
	});

	function returnBack() {
		location.href = global_ctxPath + '/xiu/post/listPage';
	}
</script>
</head>

<body>
	<div style="height: 100%;overflow-y: auto;">
		<br /> <br /> <br />
		<h2 style="text-align: center; color: red; font-size: 30px;">帖子详情</h2>
		<form>
			<table class="ttab" height="100" width="85%" border="1"
				cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">贴吧标题：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${post.title}
						</div>
					</td>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">发帖人名称：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${post.userName }
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">发帖时间：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${post.createDate}
						</div>
					</td>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">点赞数：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							${post.fcount }
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">贴子内容：</div>
					</td>
					<td colspan="3" align="left">
						${post.content}
					</td>
				</tr>
				<tr>
					<td align="right" width="15%">图片：</td>
					<td colspan="4" style="padding: 10px">
						<c:forEach items="${post.picList}" var="image">
							<img src="${image.url }" width="300px" height="200px" />
						</c:forEach>
						
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