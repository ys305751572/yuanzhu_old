<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/resource.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tables')
				.datagrid(
						{
							url : global_ctxPath + '/xiu/post/findAllPost',
							method : 'post',
							fit : true,
							title : '',
							fitColumns : true,
							singleSelect : false,
							pagination : true,
							rownumbers : false,
							pageSize : 15,
							pageList : [ 15, 20, 25, 30 ],
							remoteSort : false,
							idField : 'id',
							loadMsg : '数据装载中......',
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									{
										field : 'id',
										title : '帖子编号',
										width : 20,
										align : 'center',
									},
									{
										field : 'title',
										title : '帖子标题',
										width : 60,
										align : 'center'
									},
									{
										field : 'content',
										title : '帖子内容',
										width : 60,
										align : 'left'
									},
									{
										field : 'postBarName',
										title : '所属贴吧',
										width : 25,
										align : 'left'
									},
									{
										field : 'createDate',
										title : '发布时间',
										width : 30,
										align : 'center',
										formatter : function(value ,row ,index) {
											return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
										}
									},
									{
										field : 'isList',
										title : '操作',
										width : 20,
										align : 'center',
										formatter : function(value, row, index) {
											var dataValue = $('#tables')
													.datagrid("getRows");
											var postId = dataValue[index].id;
											var isTop = dataValue[index].isTop;
											console.log("isTop:" + isTop);
											var detail = '<img src="'+ global_ctxPath +'/images/view-flag.png" title="查看" width="20px" style="cursor:pointer;" height="20px" onclick="postDetail('
												+ postId + ')">';
											var isList;
											
											if(value == '1') {
												isList = '<img src="'+ global_ctxPath +'/images/no-flag.png" title="下架" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + postId + ',-1)">';
											}
											else if(value == '-1') {
												isList = '<img src="'+ global_ctxPath +'/images/ok-flag.png" title="上架" width="20px" style="cursor:pointer;" height="20px" onclick="exchange(' + postId + ',1)">';
											}
											
											if(isTop == '1') {
												isTop = '<img src="'+ global_ctxPath +'/images/ok-flag.png" title="置顶" width="20px" style="cursor:pointer;" height="20px" onclick="top11(' + postId + ',2)">';
											}
											else if(isTop == '2') {
												isTop = '<img src="'+ global_ctxPath +'/images/no-flag.png" title="取消置顶" width="20px" style="cursor:pointer;" height="20px" onclick="top11(' + postId + ',1)">';
											}
											return detail + isList + isTop;
										}
									} ] ],
							toolbar : '#toolbar_t'
						});
		//分页参数配置
		$('#tables').datagrid('getPager').pagination({
			displayMsg : '当前显示从{from}到{to}共{total}记录',
			beforePageText : '第',
			afterPageText : '页 共 {pages} 页',
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
		

		// 对Date的扩展，将 Date 转化为指定格式的String 
		// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
		// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
		// 例子： 
		// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
		// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
		Date.prototype.Format = function(fmt) 
		{ //author: meizz 
		  var o = { 
		    "M+" : this.getMonth()+1,                 //月份 
		    "d+" : this.getDate(),                    //日 
		    "h+" : this.getHours(),                   //小时 
		    "m+" : this.getMinutes(),                 //分 
		    "s+" : this.getSeconds(),                 //秒 
		    "q+" : Math.floor((this.getMonth()+3)/3), //季度 
		    "S"  : this.getMilliseconds()             //毫秒 
		  }; 
		  if(/(y+)/.test(fmt)) 
		    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		  for(var k in o) 
		    if(new RegExp("("+ k +")").test(fmt)) 
		  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
		  return fmt; 
		}
		
		$('#_xiu').combobox({
			url : global_ctxPath + '/xiu/post/findAllXiu',
			valueField : "id",
			textField : "title",
			editable : false,
			width : 150,
			multiple : false,
			required : false,
			dataType : 'json',
			onLoadSuccess : function() {
				//选择返回数据中的第一条
				var data = $('#_xiu').combobox('getData');
				if (data.length > 0) {
					$('#_xiu').combobox("select", data[0].id);
				}
			}
		});
	});


	// 查看活动详细信息
	function postDetail(postId) {
		window.location.href = global_ctxPath + '/xiu/post/detail?postId=' + postId;
	}

	// 上/下架
	function exchange(postId,status) {
		$.ajax({
			type : 'post',
			url : global_ctxPath + '/xiu/post/exchange' ,
			dataType : 'json',
			data : {
				'id' : postId,
				'isList' : status
			},
			success : function(data){
				if(data.status == "1") {
					divfloat('操作失败', 150, 40);
				}
				else{
					divfloat('操作成功', 150, 40);
					$('#tables').datagrid('reload');
				}
			}
		});
	}
	
	function top11(postId,status) {
		$.ajax({
			type : 'post',
			url : global_ctxPath + '/xiu/post/top' ,
			dataType : 'json',
			data : {
				'id' : postId,
				'isTop' : status
			},
			success : function(data){
				if(data.status == "1") {
					divfloat('操作失败', 150, 40);
				}
				else{
					divfloat('操作成功', 150, 40);
					$('#tables').datagrid('reload');
				}
			}
		});
	}

	function doSearch() {
		$('#tables').datagrid("load", {
			postBarId : $("#_xiu").combobox("getValue"),
			title : $("#title").val()
		});
	}

	function resetValue() {
		$("#province").combobox('reload',
				global_ctxPath + '/findProvinceByParamsList');
		$("#content").val('');
		$("#createTime").datebox('setValue', '');
		$("#stuUser").val('');
	}

	//获取多选的ID
	function getRowsIdfield() {
		var str = "";
		var rows = $('#tables').datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			//if (rows[i].status == '0') {
				
			//}
			str += rows[i].id + ",";
		}
		$('#tables').datagrid("clearSelections");
		return str;
	}

	//批量处理
	function updatestoyes() {
		var ary = getRowsIdfield();
		if (ary.length > 0) {
			$.messager.confirm('提示', '你确定将所有选中项审核为通过？', function(r) {
				if (r) {
					activitiesUpdates(ary, 1);
				}
			})
		} else {
			divfloat('请选择至少一项记录', 250, 40);
		}
	}

	//批量处理
	function updatestono() {
		var ary = getRowsIdfield();
		if (ary.length > 0) {
			$.messager.confirm('提示', '你确定将所有选中项审核为未通过？', function(r) {
				if (r) {
					activitiesUpdates(ary, 2);
				}
			})
		} else {
			divfloat('请选择至少一项未审核的记录', 250, 40);
		}
	}

	//批量处理方法
	function activitiesUpdates(ary, flag) {
		$.ajax({
			type : 'post',//传输方式
			cache : false,
			url : global_ctxPath + '/updateActivitiess',//路径
			dataType : 'json',
			data : {
				"ary" : ary,
				"status" : flag
			},
			success : function(data) {
				if (data > 0) {
					divfloat('操作成功', 160, 40);
					$('#tables').datagrid('reload');
				} else {
					divfloat('操作失败', 160, 40);
				}
			},
			error : function() {
				divfloat('操作失败', 160, 40);
			}
		});
	}

	//格式化时间
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	};

	function myparser(s) {
		if (!s)
			return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	};
</script>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div
			data-options="region:'north',title:'查询条件',border:false,noheader:true"
			style="height: 45px; padding: 10px 14px 0px 14px; background: #ffffff; overflow: hidden;">
			<span>秀吧：</span> <select id="_xiu" class="easyui-combobox"
				name="_xiu" style="width: 120px;" data-options="editable:false">
			</select>
			<span>帖子标题：</span> <input id="title" name="title" class="textbox"
				style="line-height: 16px; border: 1px solid #ccc; width: 116px; height: 20px;" />
			<a href="javascript:void(0)" style="width: 60px;"
				data-options="iconCls:'icon-search'" class="easyui-linkbutton"
				plain="false" onclick="doSearch()">查询</a> <a href="#"
				style="width: 60px;" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload'" plain="false"
				onclick="resetValue()">重置</a>
		</div>
		<div data-options="region:'center',border:false"
			style="padding-left: 4px; padding-top: 0px; padding-right: 4px; background: #ffffff; overflow: hiadden;">
			<table id="tables"
				style="width: inherit; height: auto; text-align: center;"
				pagination="true">

			</table>
		</div>
		<div id="toolbar_t" class="info">
			<table cellspacing="0" cellpadding="0" border="0" style="width: 100%">
			</table>
		</div>
	</div>
</body>