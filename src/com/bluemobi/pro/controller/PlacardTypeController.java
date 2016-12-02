package com.bluemobi.pro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Acttype;
import com.bluemobi.pro.pojo.PlacardType;
import com.bluemobi.pro.service.impl.ActtypeServiceImpl;
import com.bluemobi.pro.service.impl.PlacardTypeServiceImpl;
import com.bluemobi.sys.page.Page;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class PlacardTypeController {

	@Autowired
	private PlacardTypeServiceImpl placardTypeServiceImpl;

	// ****************************框架外代码*********************************

	@RequestMapping("/placardTypelist")
	public String placardTypelist(HttpSession session) {
		Page<List<PlacardType>> page = placardTypeServiceImpl.list(null, 1, 10000);
		session.setAttribute("acttypeList", page.getRows());
		return "main/placard/type";
	}

	/***
	 * 添加或更新
	 * 
	 * @param map
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/addOrupdatePlacardType")
	@ResponseBody
	public String addOrupdatePlacardType(@RequestParam Map<String, Object> map) {
		int offset = 0;
		try {
			if (null == map.get("id") || ((String) map.get("id")).equals("0")) {

				// 判断类型名称是否相同
				Map _map = new HashMap();
				_map.put("type", map.get("type").toString());
				PlacardType rsMap = placardTypeServiceImpl.getById(_map);
				if (rsMap == null) {
					offset = placardTypeServiceImpl.save(map);
				} else {
					offset = -1;
				}
			} else {
				offset = placardTypeServiceImpl.update(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offset + "";
	}

	/**
	 * 处理--多选
	 * 
	 * @return
	 */
	@RequestMapping("/deletePlacardtypes")
	@ResponseBody
	public String deletePlacardtypes(@RequestParam Map<String, Object> params) {
		// 要处理的数据集，以字符串的形式存在，中间用逗号隔开
		String ids = params.get("ary").toString();
		// 获取到要处理的id的数组
		String[] list = ids.split(",");
		// 调用更新方法，传入要处理id的数组
		return placardTypeServiceImpl.deletes(list);
	}
}
