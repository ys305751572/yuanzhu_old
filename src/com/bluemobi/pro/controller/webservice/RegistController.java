package com.bluemobi.pro.controller.webservice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.pro.service.impl.RegisterServiceImpl;
import com.bluemobi.pro.service.impl.StuUserServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.utils.CoinLogUtil;
import com.bluemobi.utils.EasemobUtil;
import com.bluemobi.utils.PropertiesUtils;
import com.bluemobi.utils.QrCodeUtil;

/**
 * 
 *  
 * <p>
 * Title: RegistController.java
 * </p>
 *    
 * <p>
 * Description: 注册controller
 * </p>
 *    @author yesong   @date 2014-11-13 下午03:17:12  @version V1.0 
 * ------------------------------------ 历史版本
 *
 */
@Controller
@RequestMapping(value = "/api")
public class RegistController extends BaseController {

	private static Log log = LogFactory.getLog(RegistController.class);

	@Autowired
	private RegisterServiceImpl registerService;
	@Autowired
	private StuUserServiceImpl userServiceImpl;

	/**
	 * 验证手机号是否被注册过
	 * 
	 * @param tel
	 * @return
	 */
	public boolean checkName(String tel) {
		boolean isCan = false;
		try {
			isCan = registerService.checkMobile(tel);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("手机验证异常...");
		}
		return isCan;
	}

	/**
	 * 用户注册
	 * 
	 * @param stuUser
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/account/regist", method = RequestMethod.POST)
	@ResponseBody
	public Map registerUser(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		String tel = (String) map.get("mobile");
		String psw = (String) map.get("password");
		String nickname = map.get("nickname") != null ? (String) map.get("nickname") : "";
		
		if(map.get("code") == null) return null;
		Map stuUser = null;

		if (checkName(tel)) {
			try {
				if (StringUtils.isNotBlank(tel) && StringUtils.isNotBlank(psw)) {
					// 注册
					stuUser = registerService.registerStuUser(new StuUser(tel.trim(), psw.trim(), nickname.trim()));
					this.doResp(stuUser, "userInfo", null, STATUS_SUCCESS, MSG_NULL);

					// 在第三放平台创建用户
					String url = "{\"username\":\"" + Constant.EASEMOB_ + stuUser.get("id") + "\",\"password\":\""
							+ psw + "\",\"nickname\":\"" + nickname + "\"}";
					String resp = EasemobUtil.createUser(url);


					// 如果在环信注册失败，则删除本地数据，返回新增错误信息
					if (StringUtils.isBlank(resp)) {
						registerService.deleteByMobile(tel);
						this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_08);
					}
					// 验证验证码
					if ( !this.validateCode(map.get("code").toString(), tel) ) {
						registerService.deleteByMobile(tel);
						this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_15);
					} else {
						// 注册成功加100爱心币
						String coin = PropertiesUtils.getPropertiesValues("coin_register", Constant.COIN_PROP);
						userServiceImpl.addCoin(Integer.parseInt(coin), stuUser.get("id").toString());
						// 记录登陆奖励记录
						CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(stuUser.get("id").toString()), Integer
								.parseInt(coin), Constant.REGISTER));
						QrCodeUtil.generateQrCode(Constant.QRCODE_PERSON, stuUser.get("id").toString(), "stuuser",
								false, 0);
					}
				} else {
					this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_08);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("用户注册失败");
				doError();
			}
		} else {
			// 该手机号已注册
			this.doResp(DATA_NULL, "userInfo", null, STATUS_FAIL, Constant.ERROR_09);
		}
		return getJsonString();
	}
}
