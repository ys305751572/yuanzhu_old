<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common-css.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
	});

	function tijiao() {
		
		var id = $("#id").val();
		
		var title = $('#title').val();
		var shortName = $('#shortName').val();
		var content = $('#content').val();
		var index = $("#index").val();
		console.log("index:" + index);
		if (null == name || name.trim() == '') {
			divfloat('秀吧名称不能为空', 160, 40);
			$('#name').focus();
			return;
		}
		if (null == shortName || shortName.trim() == '') {
			divfloat('秀吧简称不能为空', 200, 40);
			$('#shortName').focus();
			return;
		}
		if (null == content || content == '') {
			divfloat('秀吧内容不能为空', 200, 40);
			$('#content').focus();
			return;
		}
		$.ajax({
			type : 'post',
			async : false,
			cache : false,
			url : global_ctxPath + '/xiu/saveOrUpadte',
			dataType : 'json',
			data : {
				"id" : id,
				"title" : title,
				"shortName" : shortName,
				"content" : content,
				"index" : index
			},
			success : function(data) {
				if (data.status == '0') {
					divfloat('新增成功', 160, 40);
					returnBack();
				} 
				else {
					divfloat('新增失败', 160, 40);
				}
			}
		});
	}
	function returnBack() {
		location.href = global_ctxPath + '/xiu/listPage';
	}
</script>
</head>

<body>
	<div style="height: 100%;overflow-y: auto;">
		<br /> <br /> <br />
		<h2 style="text-align: center; color: red; font-size: 30px;">编辑秀吧</h2>
		<form>
			<table class="ttab" height="100" width="85%" border="1"
				cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">秀吧名称<span style="color: red;">*</span>：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input type="hidden" id="id" name="id" value="${pb.id}" >
							<input style="height: 20px;width: 200px" name="title"
								class="textbox" id="title" type="text" value="${pb.title}"/>
						</div>
					</td>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">秀吧简称<span style="color: red;">*</span>：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<input style="height: 20px;width: 200px" id="shortName"
								class="textbox" name="shortName" type="text" value="${pb.shortName}"/>
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">排序<span style="color: red;">*</span>：</div>
					</td>
					<td>
						<div align="left" class="STYLE1" style="padding-left:10px;">
							<c:if test="${pb.index ne null}">
								<input style="height: 20px;width: 200px" id="index" name="index"
								class="textbox" id="title" type="text" value="${pb.index}"/>
							</c:if>
							<c:if test="${pb.index eq null}">
								<input style="height: 20px;width: 200px" id="index" name="index"
								class="textbox" id="title" type="text" value="${maxIndex}"/>
							</c:if>
							
						</div>
					</td>
				</tr>
				<tr>
					<td height="30" width="15%">
						<div align="right" class="STYLE1">贴吧内容：</div>
					</td>
					<td colspan="3" align="center">
						<textarea rows="3" cols="95" id="content" name="content">${pb.content}</textarea>
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