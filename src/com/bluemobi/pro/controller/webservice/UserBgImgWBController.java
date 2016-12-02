package com.bluemobi.pro.controller.webservice;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bluemobi.pro.pojo.BackgroudImg;
import com.bluemobi.pro.pojo.UserBgImg;
import com.bluemobi.pro.service.impl.BackgroudImgSeviceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.utils.ImageUtils;
import com.bluemobi.utils.Result;

/**
 * 个性状态API
 * 
 * @author 叶松
 */
@RequestMapping(value = "/api/bg")
@Controller
public class UserBgImgWBController extends BaseController {

	@Autowired
	private BackgroudImgSeviceImpl backgroudImgSeviceImpl;

	/**
	 * 查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	@ResponseBody
	public Result all() {
		List<BackgroudImg> list = null;
		try {
			list = backgroudImgSeviceImpl.all();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success(list);
	}

	/**
	 * 查询用户个性装扮
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public Result find(Integer userId) {
		String bg = "";
		try {
			bg = backgroudImgSeviceImpl.findByUserId(userId);
			if (bg == null) {
				bg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success(bg,"backgroudimg");
	}

	/**
	 * 设置个性状态
	 * 
	 * @param userBgImg
	 * @return
	 */
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	@ResponseBody
	public Result set(UserBgImg userBgImg, MultipartHttpServletRequest request) {
		try {
			String diyPath = "";
			MultipartFile imgFile = request.getFile("pic");
			if (imgFile != null && !imgFile.isEmpty()) {
				String[] imgurl = ImageUtils.saveImage(imgFile, false);
				diyPath = imgurl[0];
			}
			if (StringUtils.isNotBlank(userBgImg.getBgimgPath())) {
				diyPath = userBgImg.getBgimgPath();
			}
			backgroudImgSeviceImpl.userSet(userBgImg.getUserId(), diyPath);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success();
	}
}
