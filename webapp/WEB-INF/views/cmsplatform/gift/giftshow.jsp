<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title>EasyUI from</title>
	<%@include file="/common/common-css.jsp"%>
	<%@ include file="/common/resource.jsp"%>
	<%@ include file="/common/taglibs.jsp"%>
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/editor/themes/default/default.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/editor/plugins/code/prettify.css" />
	
	<script charset="utf-8" src="<%=request.getContextPath()%>/editor/kindeditor-min.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath()%>/editor/lang/zh-CN.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath()%>/editor/plugins/code/prettify.js"></script>
	<script>
		var editor1;
		KindEditor.ready(function(K) {
				editor1 = K.create('textarea[name="content1"]', {
				cssPath : '<%=request.getContextPath()%>/editor/plugins/code/prettify.css',
				uploadJson : '<%=request.getContextPath()%>/editor/jsp/upload_json.jsp',
				fileManagerJson : '<%=request.getContextPath()%>/editor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				items: ["undo", "redo", "|", "preview", "print","code", "cut", "copy", "paste", 
				        "|", "justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent",
				        "subscript", "superscript", "quickformat", "selectall", "|", "fullscreen", "formatblock", "fontname", "fontsize", "/", 
				        "forecolor", "hilitecolor", "bold", "italic", 
				        "underline", "strikethrough", "lineheight", "removeformat", "|", 
				        "image", "flash", "media", "table", "emoticons",
				        "anchor", "link", "unlink"],
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['detail'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['detail'].submit();
					});
				}
			});
			prettyPrint();
		});
		
		 $(function() {
				//头像预览
				$("#pic").uploadPreview({
						Img : "imgs",
						Width : 180,
						Height : 180
				});
				
				$("#deletebtn").click(function(){
					document.getElementById("pic").value="";
					$("#imgs").attr("src","");
				});
				
				$("#dosubmit").click(function(){
					validate();
					editor1.sync();
					$.ajaxFileUpload({
						url : global_ctxPath + '/addGift', //需要链接到服务器地址
						type : 'post',
						frameId : 'temp_upload_frame',
						secureuri : false,
						fileElementId : 'pic', //文件选择框的id属性
						dataType : 'json', //服务器返回的格式，可以是json
						param : {
							'name' : $("#name").val(),
			    			'coin' : $("#coin").val(),
			    			'detail' : $("#content1").val()
						},
						success : function(data) {
							if(data == "-1"){
								divfloat("添加失败",120,40);
								$("#name").foucs();
							}
							else{
								back();
							}
						}
					});
				});
			});
	    	
		 //返回
		function back(){
			location.href=global_ctxPath +"/listGitfPage";
		}
		 
		 function validate() {
			 var giftname = $("#name").val();
			 var giftcoin = $("#coin").val();
			 var imgs = $("#imgs").prop("src");
			 
			 editor1.sync();
			 var detail = $("#content1").val();
			 
			 if(giftname == null || giftname == '') {
				 divfloat('商品名不能为空', 160, 40);
				 $("#name").focus();
				 return ;
			 }
			 var reg = /^[0-9]*[1-9][0-9]*$/;
			 if(giftcoin == null || giftcoin == '') {
				 divfloat('爱有币不能为空', 160, 40);
				 $("#coin").focus();
				 return ;
			 }else if(!reg.test(giftcoin)){
				 divfloat('爱佑币只能为正整数', 180, 40);
				 $("#coin").focus();
				 return ;
			 }
			 if(detail == null || detail == '') {
				 divfloat('商品详情不能为空', 160, 40);
				 $("#content1").focus();
				 return ;
			 }
		 }
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 20px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
	    <form id="detail" name="detail" method="post" enctype="multipart/form-data">
	    	 <table class="ttab" height="400px" width="80%" border="0"
					cellpadding="0" cellspacing="1" align="center"
					style="margin-top: 30px">
			
            	<tr>
            		<input type="hidden" id="id" value="${gift.id }"/>
					<td style="text-align: right; width: 15%;height: 30px">商品名称：</td>
					<td colspan="2">
						${gift.name }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px"">爱佑币数量：</td>
					<td colspan="2">
						${gift.coin }
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 280px">商品图片：</td>
					<td colspan="2">
						<img id="imgs" src="${gift.pic }" width="180px;" height="250px;"/><br/>
					</td>
				</tr>
                <tr>
                    <td style="text-align: right; width: 15%;height: 220px">商品详情：</td>
                    <td>
                       <textarea id="content1" name="content1" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${gift.detail }</textarea>
                    </td>
                </tr>
                <tr>
					<td colspan="4" style="padding: 10px">
						<div align="center">
							<input type="button" value="　返　回　" class="input_btn_style1"
								onclick="back();">
						</div>
					</td>
				</tr>
            </table>
		</form>
	</div>
	<iframe src="" name="temp_upload_frame" id="temp_upload_frame"
	style="display: none;"></iframe>
    </div>
</body>
</html>