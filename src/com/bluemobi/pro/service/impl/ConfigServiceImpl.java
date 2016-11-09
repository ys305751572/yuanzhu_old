package com.bluemobi.pro.service.impl;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Config;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.PropertiesUtils;

@Service
public class ConfigServiceImpl extends BaseService{

	public static final String PRIFIX = Config.class.getName();
	
	public Config find() throws Exception {
		return this.getBaseDao().getObject(PRIFIX + ".find", null);
	}
	
	public void update(Config config) throws Exception {
		this.getBaseDao().update(PRIFIX + ".update", config);
		updateProperties(config);
	}

	private void updateProperties(Config config) {
		PropertiesUtils.writePropetiesValues("coin", String.valueOf(config.getCoin()), "爱星币与人民币的比例");
		PropertiesUtils.writePropetiesValues("coin_register", String.valueOf(config.getCoinRegister()), "注册奖励");
		PropertiesUtils.writePropetiesValues("coin_persion", String.valueOf(config.getCoinPerson()), "完善个人信息");
		PropertiesUtils.writePropetiesValues("coin_card", String.valueOf(config.getCoinCard()), "学生证认证奖励");
		PropertiesUtils.writePropetiesValues("coin_us", String.valueOf(config.getCoinUs()), "推荐奖励-自己");
		PropertiesUtils.writePropetiesValues("coin_rec", String.valueOf(config.getCoinRec()), "推荐奖励-他人");
		PropertiesUtils.writePropetiesValues("coin_rec", String.valueOf(config.getCoinRec()), "推荐奖励-他人");
		PropertiesUtils.writePropetiesValues("COEFFICIENT", String.valueOf(config.getCoefficient()), "任务爱佑币奖励系数");
		
		PropertiesUtils.writePropetiesValues("INTEGRAL_RELEASE", String.valueOf(config.getIntegralRelease()), "发起人获得的积分");
		PropertiesUtils.writePropetiesValues("INTEGRAL_ACCEPT", String.valueOf(config.getIntegralAccept()), "接单人 获得的积分");
		PropertiesUtils.writePropetiesValues("INTEGRAL_RELEASE_DEDUCT", String.valueOf(config.getIntegralReleaseDeduct()), "发起人获得的积分");
		PropertiesUtils.writePropetiesValues("INTEGRAL_ACCEPT_DEDUCT", String.valueOf(config.getIntegralAcceptDeduct()), "接单人 获得的积分");
	}
}
