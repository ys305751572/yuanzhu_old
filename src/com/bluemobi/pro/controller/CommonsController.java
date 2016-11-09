package com.bluemobi.pro.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bluemobi.pro.pojo.TaskExcel;
import com.bluemobi.pro.service.impl.TaskServiceImpl;
import com.bluemobi.utils.ExportExcel;

/** 
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/commons/")
public class CommonsController {

	@Autowired
	private TaskServiceImpl taskService;

	@RequestMapping(value = "downloadTaskExcel")
	public void downloadTaskExcel(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/x-download");
		String filename = "";
		try {
			filename = URLEncoder.encode("任务完成列表","UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.addHeader("Content-Disposition", "attachment;filename="+ filename +".xls");

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
	}
}
