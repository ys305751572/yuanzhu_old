package com.bluemobi.pro.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.pro.pojo.Config;
import com.bluemobi.sys.identifier.AlipayUtils;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.CommonUtils;
import com.bluemobi.utils.DateUtils;
import com.bluemobi.utils.PayConfig;

@Service
public class WeixinServiceImpl extends BaseService {

	public static final String PREFIX = Coinlog.class.getName();
	
	@Autowired
	private StuUserServiceImpl stuUserServiceImpl;
	
	public Map<String, Object> getPrepayId(HttpServletRequest request, HttpServletResponse response, Integer userId,
			Integer coin) throws Exception {
		double _rmb = AlipayUtils.getTotalRMB(coin);
		String sn = CommonUtils.generateSn();
		Map<String, Object> resultMap = PayConfig.config(request, response, sn, _rmb, "WEIXIN");

		String _name = AlipayUtils.generatorName(userId);

		String content = new StringBuffer().append("您于")
				.append(DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss")).append("充值").append(_rmb)
				.append("元,兑换爱佑币").append(coin).append("个").toString();
		insertCoinLog(coin, _rmb, userId, sn, _name, content);
		return resultMap;
	}

	/**
	 * 新增充值记录
	 * 
	 * @param coin
	 * @param _rmb
	 * @param userId
	 * @param orderId
	 * @param orderName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int insertCoinLog(int coin, double _rmb, int userId, String orderId, String orderName, String content)
			throws Exception {
		Map map = new HashMap();
		map.put("coin", coin);
		map.put("money", _rmb);
		map.put("stuUserId", userId);
		map.put("orderId", orderId);
		map.put("orderName", orderName);
		map.put("groupId", 0);
		map.put("status1", 0);
		map.put("createTime", System.currentTimeMillis());
		map.put("content", content);
		return this.getBaseDao().save(PREFIX + ".insertCoinLog", map);
	}

	@Transactional
	public void callSuccess(String sn, Double money) throws Exception {
		Coinlog coinlog = this.getBaseDao().getObject(PREFIX + ".findBySn", sn);
		if (coinlog != null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("status1", 1);
			paramMap.put("sn", sn);
			this.getBaseDao().update(PREFIX + ".updaeStatus", paramMap);
			
			stuUserServiceImpl.addCoin(coinlog.getCoin(), String.valueOf(coinlog.getStuUserId()));
		}
	}
}
