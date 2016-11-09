package com.bluemobi.utils;

import com.bluemobi.constant.Constant;

public class YuanzhuUtils {

	public static final int COEFFICIENT = 10;
	
	public static int generateRewardMoney(int taskCoin) {
		String _coefficient = PropertiesUtils.getPropertiesValues("COEFFICIENT", Constant.COIN_PROP);
		int rewardMoney = taskCoin * Integer.parseInt(_coefficient) / 100;
		return rewardMoney;
	}
}
