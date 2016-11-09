<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@include file="/common/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.ztree.exedit-3.5.js"></script>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
</head>
<body>
<div style="margin:2px;border: 1px solid #DDD;"  >
        <div style="width: auto;height: auto;" align="center">
        	 <ul id="menuTree" class="ztree">  
        	</ul>
        </div>
       	<div align="center" style="margin: 5px;">
			<a href="##" class="easyui-linkbutton" tabindex="2" id="submitBtn">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="##" class="easyui-linkbutton" tabindex="2" id="clearBtn">清空</a>
		</div>
    </div> 
</body>
<script type="text/javascript">
		<!--
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false,
				showLine:false,
				expandSpeed: "slow",
				showIcon: true,
				txtSelectedEnable: true
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeRemove:beforeRemove,//点击删除时触发，用来提示用户是否确定删除
				beforeEditName: beforeEditName,//点击编辑时触发，用来判断该节点是否能编辑
				beforeRename:beforeRename,//编辑结束时触发，用来验证输入的数据是否符合要求
				onRemove:onRemove,//删除节点后触发，用户后台操作
				onRename:onRename,//编辑后触发，用于操作后台
				beforeDrag:beforeDrag,//用户禁止拖动节点
				onClick:clickNode//点击节点触发的事件
			}
		};
		var log, className = "dark";
		function beforeDrag(treeId, treeNodes) {
			return true;
		}
		function beforeEditName(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			zTree.selectNode(treeNode);
			return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			zTree.selectNode(treeNode);
			return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		}
		function onRemove(e, treeId, treeNode) {
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("menuTree");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode, isCancel) {
		}
		function showRemoveBtn(treeId, treeNode) {
			return treeNode.id==1?false:true;
		}
		function showRenameBtn(treeId, treeNode) {
			return treeNode.id==1?false:true;
			
		}
		function clickNode(e,treeId,treeNode){
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("menuTree");
				zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
				return false;
			});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		var menuNodes=[];
		$(document).ready(function(){
			var zNodes=${zNodes};
			var type=zNodes.length>1?1:0;
			$.fn.zTree.init($("#menuTree"), setting, zNodes);
			$("#selectAll").bind("click", selectAll);
			$("#submitBtn").click(function(){
				var treeObj=$.fn.zTree.getZTreeObj("menuTree");
				var nodes = treeObj.transformToArray(treeObj.getNodes());
				 $.ajax({
	                    type : "post",
	                    async : false,
	                    url :'cms_siteMenuSave',
	                    data : {
	                        "nodes" :JSON.stringify(nodes),
	                        "type":type
	                    },
	                    dataType : "json",
	                    success : function(data) {
	                    	if(data!=null && data!=undefined){
	                    		alert(data.msg);
	                    	}
	                    }
	               });
			});
		});
		//-->
	</script>
</html>