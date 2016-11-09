package com.bluemobi.pro.controller.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.pro.service.impl.RegisterServiceImpl;
import com.bluemobi.pro.service.impl.StuUserServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.utils.EasemobUtil;
import com.bluemobi.utils.MD5;

/**
 * 
 *  
 * <p>
 * Title: LoginController.java
 * </p>
 *    
 * <p>
 * Description: 登录controller
 * </p>
 *    @author Administrator   @date 2014-11-13 下午05:06:17  @version V1.0 
 * ------------------------------------ 历史版本
 *
 */
@Controller
@RequestMapping(value = "/api")
public class LoginController extends BaseController {

	@Autowired
	private StuUserServiceImpl stuUserService;
	@Autowired
	private RegisterServiceImpl registerService;

	/**
	 * 用户登录
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/account/login", method = RequestMethod.POST)
	@ResponseBody
	public Map checkLogon(@RequestParam Map<String, Object> map) {
		String mobile = (String) map.get("mobile");
		String password = (String) map.get("password");
		Map stuUser = null;
		try {
			stuUser = stuUserService.queryStrUser(new StuUser(mobile.trim(), password.trim()));

			if (stuUser == null) {
				this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_04);
			} else {
				// 修改用户在线状态
				Map statusMap = new HashMap();
				statusMap.put("userId", stuUser.get("id"));
				statusMap.put("isOnline", Constant.ONLINE);

				stuUserService.updatelineStatus(statusMap);
				this.doResp(stuUser, "userInfo", null, STATUS_SUCCESS, MSG_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return getJsonString();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/account/logout", method = RequestMethod.POST)
	@ResponseBody
	public Map logout(@RequestParam Map<String, Object> map) {
		map.put("isOnline", Constant.OFFLINE);
		try {
			stuUserService.updatelineStatus(map);
			this.doResp(DATA_NULL, "", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return getJsonString();
	}

	/**
	 * 找回密码
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/account/findPsw", method = RequestMethod.POST)
	@ResponseBody
	public Map findPsw(@RequestParam Map<String, Object> map) {
		try {
			String mobile = ((String) map.get("mobile")).trim();
			String password = ((String) map.get("password")).trim();
			if (map.get("code") == null)
				return null;

			Map stuUser = null;
			boolean isHas = registerService.checkMobile(mobile);

			if (isHas) {
				this.doResp(DATA_NULL, "userInfo", null, STATUS_FAIL, Constant.ERROR_03);
			}
			if (!this.validateCode(map.get("code").toString(), mobile)) {
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_FAIL, Constant.ERROR_15);
			} else {
				stuUser = registerService.updatePsw(new StuUser(mobile, password));
				// 同步在环信修改密码
				String body = "{\"newpassword\":\"" + password + "\"}";
				String resp = EasemobUtil.chanagePwd(Constant.EASEMOB_ + stuUser.get("id"), body);
				if (StringUtils.isNotBlank(resp)) {
					this.doResp(stuUser, "userInfo", null, STATUS_SUCCESS, MSG_NULL);
				} else {
					this.doError();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return getJsonString();
	}

	/**
	 * 获取验证码
	 * 
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/getCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendVerificationCode(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		// 加密方式 MD5(mobile + timestamp + random + token )
		if (map.get("mobile") == null || map.get("timestamp") == null || map.get("random") == null
				|| map.get("token_code") == null || map.get("url_token") == null) {
			return null;
		}

		String mobile = (String) map.get("mobile");
		String timestamp = (String) map.get("timestamp");
		String random = (String) map.get("random");
		String token = (String) map.get("token_code");

		String url_token = (String) map.get("url_token");

		StringBuilder str = new StringBuilder();
		str.append(mobile).append(timestamp).append(random).append(token);
		String _url_token = MD5.md5(str.toString());

		if (!_url_token.toLowerCase().equals(url_token.toLowerCase())) {
			return null;
		}
		if (!this.sendSms(mobile).booleanValue()) {
			this.doResp(DATA_NULL, DATA_NULL, null, STATUS_FAIL, Constant.ERROR_14);
		} else {
			this.doResp(DATA_NULL, DATA_NULL, null, STATUS_SUCCESS, MSG_NULL);
		}
		return this.getJsonString();
	}
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
