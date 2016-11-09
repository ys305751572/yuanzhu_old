<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
	<%@ include file="/common/resource.jsp" %>
	<script type="text/javascript">
	$(function(){
        $('#tables').datagrid({
            url : global_ctxPath + '/keywordlist',
            method : 'post',
            fit : false,
            title : '关键字列表',
            fitColumns : true,
            singleSelect : true,
            pagination : true,
            rownumbers : true,
            pageSize : 10,
            pageList : [ 10, 20, 30 ],
            remoteSort : false,
            idField : 'id',
            loadMsg : '数据装载中......',
            columns : [ [ {
                field : 'keyName',
                title : '关键字',
                width : 30,
                align : 'center'
            },{
                field : 'updatedate',
                title : '创建时间',
                width : 30,
                align : 'center'
            },{
                field : 'id',
                title : '操作',
                width : 40,
                align : 'center',
                formatter : function(value, row, index) {
                    return "<a href=\"###\" onclick=\"delete_key('" + value + "')\">删除</a>";
                }
            } ] ]
            ,toolbar : '#toolbar_t'
        });
//         分页参数配置
        $('#tables').datagrid('getPager').pagination({
            displayMsg : '当前显示从{from}到{to}共{total}记录',
            beforePageText : '第',
            afterPageText : '页 共 {pages} 页',
            onBeforeRefresh : function(pageNumber, pageSize) {
                $(this).pagination('loading');
                $(this).pagination('loaded');
            }
        });
//         对话框初始化
        $('#search_win').dialog({
            title : '新建行为',
            top : 200,
            width : 300,
            height : 150,
            cache : false,
            modal : true,
            closed : true,
            inline : true,
            buttons : [ {
                text : '保存',
                handler : save_key
            }, {
                text : '关闭',
                handler : function() {
                    $('#search_win').dialog('close');
                }
            } ],
            onClose : function() {
                $("#key").val("");
            }
        });
    });
	
// 	添加关键字
    function save_key(){
    	var  validate = check_validate();
        if(validate){
            $.ajax({
                type : "post",
                async : false,
                url : global_ctxPath + '/search/addkey',
                data : {
                	"keyword" : $("#key").val()
                },
                dataType : "json",
                success : function(result) {
                	console.log(result);
                   if(result=="1"){
                	   alert("添加成功");
                   }else{
                	   alert("添加失败");
                   }
                    $('#search_win').dialog('close');
                    $('#tables').datagrid('reload');
                }
            });
        }
    }
//     表单验证
    function check_validate(){
        var keyword = $("#key").val();
        if(keyword==""){
            alert("请填写内容");
            return false;
        }
        return true;
    }
// 	添加关键字
    function add_key(){
                $('#search_win').dialog('open');
    }
//     删除用户
    function delete_key(id){
        $.messager.defaults = { ok: "确定", cancel: "取消" };
        $.messager.confirm('删除角色', '是否确定删除此角色？', function(r){
            if (r){
                $.ajax({
                    type : "post",
                    async : false,
                    url : global_ctxPath + '/search/detelekey',
                    data : {
                        "id" : id
                    },
                    dataType : "json",
                    success : function(result) {
                    	if(result=="1"){
                     	   alert("删除成功");
                        }else{
                     	   alert("删除失败");
                        }
                        $('#tables').datagrid('reload');
                    }
                });
            }
        });
    }
//     查询按钮
    function refresh_grid(){
        var keyname = $("#keyname").val();
        searcher(keyname);
    }
//     查询
    function searcher(keyname){
        $('#tables').datagrid('load',{
            "keyword": keyname
        });
    }

	</script>
<div data-options="region:'center',border:false"
	style="padding: 10px 0px 0px 0px;">
	<table id="tables"></table>
	 <div id="toolbar_t" class="info">
            <table cellspacing="0" cellpadding="0" border="0" style="width:100%">
                <tr>
                    <td align="left" >
                      	  关键字：
                        <input id="keyname" style="width:160px;height:23px; border:solid 1px #dfdfdf" />
                        <a href="###" class="easyui-linkbutton" onclick="refresh_grid()">查询</a>
                        <a href="###" class="easyui-linkbutton" onclick="add_key()">添加</a>
                    </td>
                </tr>
            </table>
            </div>
	<div id="search_win">
		<table style="width: 100%; padding: 5px;" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right">关键字：</td>
				<td colspan="2">
					<input id="key" type="text" />
				</td>
			</tr>
		</table>
	</div>
</div>
