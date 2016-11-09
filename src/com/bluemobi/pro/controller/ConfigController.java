package com.bluemobi.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Config;
import com.bluemobi.pro.service.impl.ConfigServiceImpl;
import com.bluemobi.utils.PropertiesUtils;
import com.bluemobi.utils.Result;

@RequestMapping("/config/")
@Controller
public class ConfigController {

	public static final String CONFIG_EDIT_PAGE = "main/config/config-edit";
	public static final String CONFIG_DETAIL_PAGE = "main/config/config-detail";
	
	@Autowired
	private ConfigServiceImpl service;
	
	@RequestMapping(value="editPage", method = RequestMethod.GET)
	public String editPage(Model model) {
		Config config = null;
		try {
			config = service.find();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("config", config);
		return CONFIG_EDIT_PAGE;
	}
	
	@RequestMapping(value="detailPage", method = RequestMethod.GET)
	public String detailPage(Model model) {
		Config config = null;
		try {
			config = service.find();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("config", config);
		return CONFIG_DETAIL_PAGE;
	}
	
	
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String edit(Config config,Model model) {
		
		try {
			service.update(config);
			Config _config = service.find();
			model.addAttribute("config", _config);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return CONFIG_DETAIL_PAGE;
	}
	
	@RequestMapping(value="test", method = RequestMethod.POST)
	@ResponseBody
	public Result testProperties() {
		System.out.println(PropertiesUtils.getPropertiesValues("coin", Constant.COIN_PROP));
		return Result.success();
	}
}
