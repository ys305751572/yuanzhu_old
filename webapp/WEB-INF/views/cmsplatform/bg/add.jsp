<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	$(function() {
		//项目图片预览
		$("#add_img").uploadPreview({
			Img : "picture",
			Width : 120,
			Height : 120
		});
	});
	
		
	function tijiao() {
		var IMG = $('#picture').attr('src');
		
		$.ajaxFileUpload({
			url : global_ctxPath + '/bg/save',
			type : 'post',
			secureuri : false,
			frameId : 'temp_upload_frame',
			fileElementId : [ 'add_img' ], //文件选择框的id属性
			dataType : 'json', //服务器返回的格式，可以是json
			param : {
				"IMG" : IMG
			},
			success : function(data) {
				console.log("data:" + data);
				if (data == '0') {
					divfloat('添加成功', 160, 40);
						window.location.href = global_ctxPath + '/bg/index';
				} else {
					divfloat('添加失败', 160, 40);
				}
			}
		});
	}

	function delPic() {
		$("#picture").attr('src', '');
	}

	//跳转到列表页面
	function back() {
		window.location.href = global_ctxPath + '/bg/index';
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 20px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table class="ttab" height="100%" width="80%" border="0"
			cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td style="text-align: right;">图片：</td>
					<td style="text-align: left;">
						<div>
							<img id="picture" src=""
								style="height: 200px; width: 300px;display: inherit;" border="0" />
						</div>
						<div>
							<input type="file" style="height: 20px; width: 220px"
								name="add_img" id="add_img" onchange="newFile('hpicture')" /> <input
								type="hidden" id="hpicture" name="picture" value="" /><input
								type="button" class="easyui-linkbutton"
								style="padding: 3px 5px 3px 5px;" onclick="delPic()" value="删 除" />
						</div>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input
						type="button" class="easyui-linkbutton"
						style="padding: 3px 5px 3px 5px;" onclick="tijiao()" value="保 存" />
						&nbsp;&nbsp; <input type="button" class="easyui-linkbutton"
						style="padding: 3px 5px 3px 5px;" onclick="back()"
						value="返 回" /></td>
				</tr>
			</table>
		</div>
	</div>

	<iframe src="" name="temp_upload_frame" id="temp_upload_frame"
		style="display: none;"></iframe>
</body>