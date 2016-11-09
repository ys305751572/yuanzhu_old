package com.bluemobi.pro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.pro.service.impl.GiftServiceImpl;
import com.bluemobi.sys.page.Page;

/**
 * 礼品
 * @author yes
 *
 */
@Controller
public class GiftController {

	@Autowired
	private GiftServiceImpl service;
	
	/**
	 * 新增礼品
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/addGift" ,method = RequestMethod.POST)
	@ResponseBody
	public String addGift(@RequestParam Map<String,Object> params,@RequestParam("pic") MultipartFile file) {
		int flag = -1;
		Object id = params.get("id");
		try { 
			if(id == null || id.toString().equals("")) {
				flag = service.addGift(params, file);
			}
			else{
				flag = service.updateGift(params,file);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return flag + "";
	}
	
	/**
	 * 编辑礼品
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/updateGift", method = RequestMethod.POST)
	@ResponseBody
	public String updateGift(@RequestParam Map<String,Object> params,@RequestParam("pic") MultipartFile file) {
		int flag = -1;
		try {
			flag = service.updateGift(params,file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag + "";
	}
	
	/**
	 * 后台跳转新增页面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "main/gift/giftadd";
	}
	
	/**
	 * 后台 跳转详细信息页面 
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toShow")
	public String toShow(@RequestParam Map<String, Object> params,Model model) {
		try {
			Map<String,Object> gift = service.getById(params);
			model.addAttribute("gift",gift);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main/gift/giftshow";
	}
	
	/**
	 * 后台 跳转编辑页面
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toEdit")
	public String toEdit(@RequestParam Map<String,Object> params,Model model) {
		try {
			Map<String,Object> gift = service.getById(params);
			model.addAttribute("gift", gift);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main/gift/giftedit";
	}
	
	/**
	 * 后台礼品列表请求
	 * @return
	 */
	@RequestMapping(value = "/listGitfPage")
	public String listPage() {
		return "main/gift/giftlist";
	}
	
	/**
	 * 后台礼品列表
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getlist",method = RequestMethod.POST)
	@ResponseBody
	public Page giftList(@RequestParam Map<String,Object> params,Model model) {
		Page page = null;
		try {
			page = service.getPage(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 后台修改礼品上架状态
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/exchangeGift" ,method = RequestMethod.POST)
	@ResponseBody
	public String exChangeGiftStatus(@RequestParam Map<String,Object> params) {
		int flag = -1;
		try {
			flag = service.exchangeGiftStatus(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "" + flag;
	}
	
	/**
	 * 后台礼品列表请求
	 * @return
	 */
	@RequestMapping(value = "/listOrderPage")
	public String listOrderPage() {
		return "main/gift/orderlist";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getOrderList")
	@ResponseBody
	public Page getOrderList(@RequestParam Map<String,Object> params) {
		Page page = null ;
		try {
			page = service.getOrderList(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}
