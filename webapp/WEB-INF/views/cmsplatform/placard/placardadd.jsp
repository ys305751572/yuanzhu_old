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

		if(${user.provinceId == 0} || ${user.cityId} == 0  || ${user.schoolId} == 0) {
			loadProvince_admin();
		}
		else {
			loadProvince_zixun();
			$("#province").combobox("select", ${user.provinceId});
			$("#city").combobox("select",  ${user.cityId});
			$("#school").combobox("select", ${user.schoolId});
		}
		
		loadPlacardType();
	});
	
	function loadProvince_admin() {
		$('#province').combobox({
			url : global_ctxPath + '/findProvinceByParamsList',
			valueField : "id",
			textField : "name",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#province').combobox('getData');
				if (data.length > 0) {
					$('#province').combobox("select", data[0].id);
				}
			},
			onSelect : function() {
				loadCity_admin();
			}
		});
	}

	// 加载城市信息
	function loadCity_admin() {
	// 加载城市信息
		$('#city').combobox(
		{
			url : global_ctxPath + '/findCityByParamsList?provinceId='
					+ $("#province").combobox("getValue"),
			valueField : "id",
			textField : "name",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onSelect : function() {
				//加载学校信息
				loadSchool_admin();
			},
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#city').combobox('getData');
				if (data.length > 0) {
					$('#city').combobox("select", data[0].id);
				}
			}
		});
	}

	// 加载城市信息
	function loadPlacardType() {
	// 加载城市信息
		$('#ptype').combobox(
		{
			url : global_ctxPath + '/findTypeByParamsList',
			valueField : "id",
			textField : "type",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#ptype').combobox('getData');
				if (data.length > 0) {
					$('#ptype').combobox("select", data[0].id);
				}
			}
		});
	}
	
	// 加载学校信息
	function loadSchool_admin() {
		$('#school').combobox(
		{
			url : global_ctxPath
					+ '/findSchoolByParamsList?provinceId='
					+ $("#province").combobox("getValue") + '&cityId='
					+ $("#city").combobox("getValue") + '&id='
					+ $("#school").combobox("getValue"),
			valueField : "id",
			textField : "proName",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#school').combobox('getData');
				if (data.length > 0) {
					$('#school').combobox("select", data[0].id);
				}
			},
			onSelect : function() {
				//加载学校信息
				loadCollege();
			},
		});
	}
	
	function loadProvince_zixun() {
		$('#province').combobox({
			url : global_ctxPath + '/findProvinceByParams',
			valueField : "id",
			textField : "name",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onSelect : function() {
				$('#city').combobox('clear');
				loadCity_zixun();
			}
		});
	}

	function loadCity_zixun() {
		$('#city').combobox(
				{
					url : global_ctxPath + '/findCityByParams?provinceId='
							+ $("#province").combobox("getValue"),
					valueField : "id",
					textField : "name",
					editable : false,
					width : 150,
					multiple : false,
					required : false,
					dataType : 'json',
					onSelect : function() {
						$('#school').combobox('clear');
						// 加载学校信息
						loadSchool_zixun();
					}
				});
	}

	function loadSchool_zixun() {
		$('#school').combobox(
				{
					url : global_ctxPath + '/findSchoolByParams?provinceId='
							+ $("#province").combobox("getValue") + '&cityId='
							+ $("#city").combobox("getValue") + '&id='
							+ $("#school").combobox("getValue"),
					valueField : "id",
					textField : "proName",
					editable : false,
					width : 150,
					multiple : false,
					required : false,
					dataType : 'json',
					onSelect : function() {
						$('#college').combobox('clear');
						loadCollege();
					}
				});
	}
	
	// 加载学院信息
	function loadCollege() {
		$('#college').combobox(
			{
				url : global_ctxPath + '/findCollegeByParams?provinceId='
						+ $("#province").combobox("getValue") + '&cityId='
						+ $("#city").combobox("getValue") + '&id='
						+ $("#school").combobox("getValue") + "&collegeId=" + $("#college").combobox("getValue"),
				valueField : "id",
				textField : "proName",
				editable : false,
				width : 150,
				multiple : false,
				required : false,
				dataType : 'json',
				onLoadSuccess : function() {
					//选择返回数据中的第一条
					var data = $('#college').combobox('getData');
					if (data.length > 0) {
						$('#college').combobox("select", data[0].id);
				}
			}
		});
	}
		
	function tijiao() {
		var content = $('#content').val();
		var title = $("#title").val();
		var provinceid = $('#province').combobox('getValue');
		var cityid = $('#city').combobox('getValue');
		var schoolid = $('#school').combobox('getValue');
		var collegeid = $('#college').combobox('getValue');
		var ptype = $('#ptype').combobox('getValue');
		var IMG = $('#picture').attr('src');

		if(title == null || title.trim() == "") {
			divfloat('公告标题不能为空', 160, 40);
			return;
		}
		if (content.replace(/\s/gi, '').length < 1) {
			divfloat('公告内容不能为空', 160, 40);
			return false;
		}
		if (content.length > 10000 ) {
			divfloat('公告内容不能大于10000', 160, 40);
			return false;
		}
		if (null == cityid || cityid == '') {
			divfloat('城市不能为空', 160, 40);
			return;
		}
		if (null == schoolid || schoolid == '') {
			divfloat('学校不能为空', 160, 40);
			return;
		}
		if (null == collegeid || collegeid == '') {
			divfloat('学院不能为空', 160, 40);
			return;
		}
		
		$.ajaxFileUpload({
			url : global_ctxPath + '/addPlacard',
			type : 'post',
			secureuri : false,
			frameId : 'temp_upload_frame',
			fileElementId : [ 'add_img' ], //文件选择框的id属性
			dataType : 'json', //服务器返回的格式，可以是json
			param : {
				"provinceId" : provinceid,
				"cityId" : cityid,
				"schoolId" : schoolid,
				"title" : title,
				"content" : content,
				"collegeId" : collegeid,
				"ptype" : ptype,
				"linkUrl" : $("#linkUrl").val(),
				"type": $("input[name='type']:checked").val(),
				"IMG" : IMG
			},
			success : function(data) {
				if (data == '1') {
					divfloat('添加成功', 160, 40);
						window.location.href = global_ctxPath + '/placardlist';
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
	function placardlist() {
		window.location.href = global_ctxPath + '/placardlist';
	}
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"
			style="padding-left: 20px;padding-top: 20px;padding-right: 4px;background:#ffffff;overflow:hiadden;">
			<table class="ttab" height="100%" width="80%" border="0"
			cellpadding="0" cellspacing="1" align="center">
				<tr>
					<td style="text-align: right;">类型<span style="color: red;">*</span>：</td>
					<td style="text-align: left;">
						<input type="radio" id="systemuser" name="type" value="1" checked="checked"/>
						<label style="cursor:pointer" for="systemuser" checked="checked">对外</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" id="normaluser" name="type" value="2" />
						<label style="cursor:pointer" for="normaluser">对内</label>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">公告类型<span style="color: red;">*</span>：</td>
					<td style="text-align: left;"><select id="ptype"
						name="ptype" class="easyui-combobox" style="width: 100px;"
						data-options="editable:false"></select></td>
				</tr>
				<tr>
					<td style="text-align: right;">省份<span style="color: red;">*</span>：</td>
					<td style="text-align: left;"><select id="province"
						name="province" class="easyui-combobox" style="width: 100px;"
						data-options="editable:false" <c:if test="${user.provinceId ne 0}">disabled="disabled"</c:if>></select></td>
				</tr>
				<tr>
					<td style="text-align: right;">城市<span style="color: red;">*</span>：</td>
					<td style="text-align: left;"><select id="city" name="city"
						class="easyui-combobox" style="width: 100px;"
						data-options="editable:false" <c:if test="${user.cityId ne 0}">disabled="disabled"</c:if>></select></td>
				</tr>
				<tr>
					<td style="text-align: right;">学校<span style="color: red;">*</span>：</td>
					<td style="text-align: left;"><select id="school"
						name="school" class="easyui-combobox" style="width: 100px;"
						data-options="editable:false" <c:if test="${user.provinceId ne 0}">disabled="disabled"</c:if>></select></td>
				</tr>
				<tr>
					<td style="text-align: right;">学院<span style="color: red;">*</span>：</td>
					<td style="text-align: left;"><select id="college"
						name="college" class="easyui-combobox" style="width: 100px;"
						data-options="editable:false"></select></td>
				</tr>
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
					<td style="text-align: right; width: 15%;height: 30px">公告标题<span style="color: red;">*</span>：</td>
					<td colspan="2">
						<input id="title" name="title" style="height: 20px;width: 200px" class="textbox" type="text" >
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%;height: 30px">公告链接：</td>
					<td colspan="2">
						<input id="linkUrl" name="linkUrl" style="height: 20px;width: 200px" class="textbox" type="text" >
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">公告内容<span style="color: red;">*</span>：</td>
					<td style="text-align: left;"><textarea
							style="height: 100px; width: 500px; resize: none;" id="content"
							name="content"></textarea></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input
						type="button" class="easyui-linkbutton"
						style="padding: 3px 5px 3px 5px;" onclick="tijiao()" value="保 存" />
						&nbsp;&nbsp; <input type="button" class="easyui-linkbutton"
						style="padding: 3px 5px 3px 5px;" onclick="placardlist()"
						value="返 回" /></td>
				</tr>
			</table>
		</div>
	</div>

	<iframe src="" name="temp_upload_frame" id="temp_upload_frame"
		style="display: none;"></iframe>
</body>