<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="application/vnd.ms-excel; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="com.bluemobi.pro.pojo.TaskExcel" %>
<%@ page import="com.bluemobi.utils.ExportExcel" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	@Autowired
	TaskServiceImpl taskService;
	
	response.setContentType("application/x-download");
	response.addHeader("Content-Disposition", "attachment;filename=task.xls");
	List<TaskExcel> list = taskService.findTaskExcel();
	String[] headers = { "用户名", "手机号", "支付宝账号", "奖励金额 ", "奖励状态" };
	
	ExportExcel<TaskExcel> ex = new ExportExcel<TaskExcel>();
	try {
		OutputStream out = response.getOutputStream();
		ex.exportExcel(headers, list, out);
		out.close();
		System.out.println("excel导出成功！");
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html>