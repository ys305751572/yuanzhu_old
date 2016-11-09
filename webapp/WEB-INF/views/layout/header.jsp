<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/tags/function/my.tld" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/webjs/w_index.js"></script>
<script type="text/javascript">
function searchlist(){
	var param = new Object();
	param.keyword = $("#searchid").val();
	var url = '<%=request.getContextPath()%>/searchprc';
	for ( var i in param) {
		$("<input />").attr({
			"type" : "hidden",
			"name" : i,
			"value" : param[i]
		}).appendTo($('#form1'));
	}
	$('#form1').attr('action', url).submit();
}
function searchs(key){
	var param = new Object();
	param.keyword = key;
	var url = '<%=request.getContextPath()%>/searchprc';
	for ( var i in param) {
		$("<input />").attr({
			"type" : "hidden",
			"name" : i,
			"value" : param[i]
		}).appendTo($('#form2'));
	}
	$('#form2').attr('action', url).submit();
}

</script>
<div class="g_head">
  <div class="g_top">
    <div class="head_left"> <img src="<%=request.getContextPath()%>/images/logo_top.png"> </div>
    <div class="head_right">
      <div class="head_list">
        <ul>
          <li><a href="#">会员登陆&nbsp;&nbsp;&nbsp;|</a></li>
          <li><a href="#">新浪微博&nbsp;&nbsp;&nbsp;|</a></li>
          <li><a href="#">腾讯微博&nbsp;&nbsp;&nbsp;|</a></li>
          <li><a href="#">收藏本站&nbsp;&nbsp;&nbsp;|</a></li>
          <li><a href="#">帮助中心</a></li>
        </ul>
      </div>
      <div class="h_r">
        <div class="h_cat"><i></i><a href="#">天猫专卖店</a></div>
        <div class="jd"><i></i><a href="#">京东旗舰店</a></div>
      </div>
    </div>
  </div>
</div>
<div class="logo_box">
  <div class="logo_left"><img src="<%=request.getContextPath()%>/images/logo.png"></div>
  <div class="logo_right">
    <div class="tel_box">
      <div class="logo_text"><img src="<%=request.getContextPath()%>/images/a3.png"></div>
      <div class="tel">全国招商热线：400-865-8999</div>
    </div>
    <div class="nav_list">
      <ul>
      	<c:forEach var="mainMenu" items="${menus }" varStatus="mainStatus">
				<li><a href="<%=request.getContextPath()%>${mainMenu.menuUrl }">${mainMenu.menuName }</a></li>
			</c:forEach>
      </ul>
    </div>
  </div>
</div>
<div class="nav_box">
  <div class="m_list">
    <ul>
      <c:forEach var="subMenu" items="${subMenus }" varStatus="subStatus">
      	<li><i></i><a href="${subMenu.menuUrl }">${subMenu.menuSonnname }</a></li>
      </c:forEach>
    </ul>
  </div>
  <div class="search_box">
    <div class="search">
    <form id="form1" action="">
   	  <div class="input_span">商品名称</div>
      <input id="searchid" type="text" placeholder="搜索">
      <i onclick="searchlist();"></i>
    </form>
    </div>
    
    <div class="hot_word">搜索关键词：<a id="hr1" href="###" onclick="searchs('${subMenus[0].key1 }');">${subMenus[0].key1 }</a><a id="hr2" href="###" onclick="searchs('${subMenus[0].key2 }');">${subMenus[0].key2 }</a><a id="hr3" href="###" onclick="searchs('${subMenus[0].key3 }');">${subMenus[0].key3 }</a></div>
  </div>
   <form id="form2" action=""></form>
</div>

 <div class="slowup">
  <ul>
    <li>商品名称</li>
    <li>产品系列</li>
  </ul>
</div>