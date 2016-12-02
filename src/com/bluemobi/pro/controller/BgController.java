package com.bluemobi.pro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bluemobi.pro.pojo.BackgroudImg;
import com.bluemobi.pro.service.impl.BackgroudImgSeviceImpl;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.ImageUtils;

@RequestMapping(value = "/bg")
@Controller
public class BgController{

	@Autowired
	private BackgroudImgSeviceImpl backgroudImgSeviceImpl;
	
	/**
	 * 个性装扮列表
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index() {
		return "main/bg/list";
	}
	
	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Page<List<BackgroudImg>> list(@RequestParam Map<String, Object> map) {
		Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
		Page<List<BackgroudImg>> page = backgroudImgSeviceImpl.page(pages, rows);
		return page;
	}
	
	/**
	 * 新增页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add() {
		return "main/bg/add";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(BackgroudImg bg, @RequestParam Map<String, Object> map, MultipartHttpServletRequest request) {
		try {
			 // 判断图片是否被删除
	        if (map.get("IMG").equals(null) || map.get("IMG").equals("")) {
	            map.put("picture", null);
	        }
	        else {
	            try {
	                // 将图片保存在服务器
	                MultipartFile imgFile = request.getFile("add_img");
	                if (!imgFile.isEmpty()) {
	                    String[] imgurl = ImageUtils.saveImage(imgFile, false);
	                    bg.setPath(imgurl[0]);
	                }
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        bg.setCreateTime(System.currentTimeMillis());
			backgroudImgSeviceImpl.add(bg);
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		return  "0";
	}
	
	/**
	 * 删除个性装扮
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(Integer id) {
		try {
			backgroudImgSeviceImpl.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		return "0";
	}
}
