<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
    <%@ include file="/common/resource.jsp" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ajaxfileupload.js"></script>
    <script type="text/javascript">
    $(function(){
        $('#tables').datagrid({
            url : global_ctxPath + '/allinformation',
            method : 'post',
            fit : false,
            title : '资讯管理',
            fitColumns : true,
            singleSelect : true,
            pagination : true,
            rownumbers : true,
            pageSize : 10,
            pageList : [ 10, 20, 30 ],
            remoteSort : false,
            idField : 'id',
            loadMsg : '数据装载中......',
            columns : [ [ 
//                           {
//                 field : 'infoClassid',
//                 title : '分类',
//                 width : 30,
//                 align : 'center',
//                 formatter:function(value,row,index){
//                     if(value=="1"){
//                         return "公司动态";
//                     }
//                     return "业内新闻";
//                 }   
//             },
            {
                field : 'infoTitle',
                title : '资讯标题',
                width : 30,
                align : 'center',
            },{
                field : 'infoImgs',
                title : '资讯图片',
                width : 30,
                align : 'center',
            },{
                field : 'publishtime',
                title : '发布时间',
                width : 30,
                align : 'center',
            },
            {
                field : 'infoContent',
                title : '资讯内容',
                width : 100,
                sortable:true
            },
            {
            	 field : 'id',
                 title : '操作',
                 width : 40,
                align : 'center',
                formatter : function(value, row, index) {
                    return "<a href=\"###\" onclick=\"modify_information('" + value + "')\">修改</a>&nbsp;&nbsp;<a href=\"###\" onclick=\"del_information('" + value + "')\">删除</a>";
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
//      对话框初始化
        $('#information_win').dialog({
            title : '新建资讯',
            top : 200,
            width : 400,
            height : 300,
            cache : false,
            modal : true,
            closed : true,
            inline : true,
            buttons : [ {
                text : '保存',
                handler : save_information
            }, {
                text : '关闭',
                handler : function() {
                    $('#information_win').dialog('close');
                }
            } ],
            onClose : function() {
            	$("#id").val("");
                $("#infoTitle").val("");
                $("#content").val("");
                $("#classid").combobox("select", "");
                $("#logo_file_img").attr("src","");
                $("#logo_file_img").hide();
            }
        });
        $('#classids').combobox({
            method : 'POST',
           url:global_ctxPath + '/getClcname',
           valueField:'clcMenuid',
           textField:'clcName',
           panelHeight : 'auto',
           editable : false,
           required : false,
           multiple : false,
           loadFilter : function(row){
               if(row!=""){
                   if(row.length>0){
                       var all = {};
                       all.clcMenuid = "-1";
                       all.clcName = "全部";
                       row.push(all);
                   }
               }
               return row;
           }
       });
    });
    
   
//  添加资讯
    function add_grid(){
                $('#information_win').dialog('open');
//                 $("#classid").combobox({
//                     valueField : 'value',
//                     textField : 'label',
//                     data : [ {
//                     	 label : '公司动态',
//                          value : '1'
//                     }, {
//                     	label : '业内新闻',
//                         value : '2'
//                     } ],
//                     panelHeight : 'auto',
//                     editable : false,
//                     required : false,
//                     multiple : false,
//                 });
                $('#classid').combobox({
                	 method : 'POST',
                    url:global_ctxPath + '/getClcname',
                    valueField:'clcMenuid',
                    textField:'clcName',
                    panelHeight : 'auto',
                    editable : false,
                    required : false,
                    multiple : false,
                });
    }
    //详情
    function modify_information(id){
        $.ajax({
            type : "post",
            async : false,
            url : global_ctxPath + '/infoInformation',
            data : {
                "method" : "info",
                "id" : id
            },
            dataType : "json",
            success : function(result) {
            	add_grid();
                 $("#id").val(result.id);
                 $("#infoTitle").val(result.infoTitle);
                 $("#classid").combobox("setValue",result.infoClassid);
                 $("#content").val(result.infoContent);
                 show_detail('logo_file',result.infoImgs);
            }
        });
    }
    

//  保存资讯
    	 function save_information(){
    		 var    validate = check_validate();
            if(validate){
             var json = getAdpcJson();
                 $.ajax({
                     type : "post",
                     async : false,
                     url : global_ctxPath + '/saveInformation',
                     data : {
                         "method" : "saveInformation",
                         "json" : json
                     },
                     dataType : "json",
                     success : function(result) {
                         alert(result.msg);
                         $('#information_win').dialog('close');
                         $('#tables').datagrid('reload');
                     }
                 });
            }
         }
         
         function getAdpcJson(){
             var id = $("#id").val();
             var infoClassid = $("#classid").combobox('getValue');
             var infoTitle =$("#infoTitle").val();
             var infoContent = $("#content").val();
             var infoImg = $("#logo_file_img").attr("src");
             var pcObject = {};
             pcObject.id = id;
             pcObject.infoClassid = infoClassid;
             pcObject.infoTitle = infoTitle;
             pcObject.infoImgs = infoImg;
             pcObject.infoContent = infoContent;
             return $.toJSON(pcObject);
         }
         
    //查询按钮
    function refresh_grid(){
        var title = $("#title").val();
//         var classids = $("#classids").val();
        var classids = $("#classids").combobox('getValue');
        searcher(title,classids);
    }
    //查询
    function searcher(title,classids){
        $('#tables').datagrid('load',{
            "infoTitle": title,
            "infoClassid": classids
        });
    }
    
    function del_information(id){
        $.messager.defaults = { ok: "确定", cancel: "取消" };
        $.messager.confirm('删除资讯', '是否确定删除此资讯？', function(r){
            if (r){
                $.ajax({
                    type : "post",
                    url : global_ctxPath + "/del",
                    data : {
                        "method" : "delete",
                        "id" : id
                    },
                    dataType : "json",
                    success : function(result) {
                        alert(result.msg);
                        $('#tables').datagrid('reload');
                    }
                });
            }
        });
    }

 //上传图片操作
    function uploadImage(targetId){
        $.ajaxFileUpload({
            url : global_ctxPath + '/file/upload/saveUpload',
            fileElementId : targetId,
            frameId : 'temp_upload_frame',
            success : function(data, status) {
                if(data.success){
                   var msg = data.msg;
                    show_detail(targetId, msg);
                } else {
                    alert(data.msg);
                }
            }
        });
    }
    //显示‘查看图片’按钮
    function show_detail(targetId, path){
        if(path != '' && path != null){
            $("#" + targetId + "_img").attr("href",global_ctxPath + path);
            $("#" + targetId + "_img").attr("src", path);
            $("#" + targetId + "_img").show();
        } else {
            $("#" + targetId + "_img").hide();
        }
    }
    
    
    function check_validate(){
        var  classid = $("#classid").combobox('getValue');
        var  infoTitle = $("#infoTitle").val();
        if(classid==""){
            alert("分类不能为空");
            return false;
        }
        if(infoTitle==""){
            alert("标题不能为空");
            return false;
        }
     var big = $("#logo_file_img").attr("src");
     if(big == ""){
         alert("请上传图片");
         return false;
     }
        return true;
    }

    </script>
<div data-options="region:'center',border:false"
    style="padding: 10px 0px 0px 0px;">
    <table id="tables"></table>
     <div id="toolbar_t" class="info">
            <table cellspacing="0" cellpadding="0" border="0" style="width:100%">
                <tr>
                    <td align="left" >
                          资讯标题：
                        <input id="title" style="width:160px;height:23px; border:solid 1px #dfdfdf" />
                        <input type="text" id="classids" name="classids"/>
                        <a href="###" class="easyui-linkbutton" onclick="refresh_grid()">查询</a>
                          <a href="###" class="easyui-linkbutton" onclick="add_grid()">添加</a>
                    </td>
                </tr>
            </table>
            </div>
   <div id="information_win">
         <table style="width:100%;padding:5px;" border="0" cellpadding="0" cellspacing="0">  
               <tr>
            <td align="right">分类：</td>
            <td><input type="hidden" id="id" /><input type="text" id="classid" name="classid"/></td>
        </tr>
        <tr>
            <td align="right">标题：</td>
            <td><input type="text" id="infoTitle" /></td>
        </tr>
       <tr>  
                     <td align="right">图片：<p><a style="display:none;" target="_blank" id="logo_file_img" name="logo_file_img"  href="###" src="">查看图片</a></p>
                     </td>
                    <td>
                        <input name="logo_file_result" id="logo_file_result" type="hidden" /> 
                        <input name="logo_file" id="logo_file" type="file" />
                    </td>
                     <td><input type="button" onclick="uploadImage('logo_file')" value="上传"/> </td>
                    </tr>
        <tr>
            <td align="right">内容：</td>
            <td><input type="text" id="content" /></td>
        </tr>
            </table>
    </div>
</div>
    <iframe src="" name="temp_upload_frame" id="temp_upload_frame"
        style="display: none;"></iframe>
