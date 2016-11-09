<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 显示控件 -->
<div id="progressBar_dialog" class="easyui-dialog">
	<table style="padding: 5px;" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td>
				<div id="progressNumber" class="easyui-progressbar" style="width: 400px;"></div>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
$(function() {
	$('#progressBar_dialog').dialog({
		title : '上传进度',
		width : 460,
		height : 80,
		closed : true,
		cache : false,
		modal : true,
		closable : false,
		resizable : false,
	});
});

//新增读取进度条信息
var intel;
var data;
/* var isManager = true; */
function callback() {
	$.ajax({
		type : "post",
		url : global_ctxPath + '/uploadProgress',
		success : function(msg) {
			$('#progressNumber').progressbar('setValue', Math.floor(msg));
			if (Math.floor(msg) == 100) {
				$('#progressBar_dialog').dialog("close");
				$('#updatebrandPage').dialog('close');
				$('#addbrandPage').dialog('close');
				$('#tables').datagrid('reload');
				window.clearInterval(intel);
			}
		}
	});
}
</script>