package com.bluemobi.pro.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.sys.identifier.AlipayUtils;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.DateUtils;

/**
 * 支付宝API service
 * @author sye
 *
 */
@Service
public class AlipayServiceImpl extends BaseService{

	public static final String PREFIX = Coinlog.class.getName();
	public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
	public static final String TRADE_FINISHED = "TRADE_FINISHED";
	@Autowired
	private StuUserServiceImpl userServiceImpl;
	
	/**
	 * 获取商品号，商品名，需要支付的价格(人民币)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<?, ?> getUUIDAndName(Map<?,?> params) throws Exception {
		String idStr = (String) params.get("userId");
		String coinStr = (String) params.get("coin");
		if(StringUtils.isBlank(idStr))return null;
		if(StringUtils.isBlank(coinStr))return null;
		String _uuid = AlipayUtils.generatorUUID();
		int userId = Integer.parseInt(idStr);
		String _name = AlipayUtils.generatorName(userId);
		int coin = Integer.parseInt(coinStr);
		double _rmb = AlipayUtils.getTotalRMB(coin);
		
		Map map = build(_uuid,_name,_rmb);
		
		
		String content = new StringBuffer().append("您于")	
										   .append(DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"))
										   .append("充值")
										   .append(_rmb)
										   .append("元,兑换爱佑币")
										   .append(coin)
										   .append("个").toString();
		insertCoinLog(coin, _rmb, userId, _uuid, _name,content);
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<?, ?> build(String _uuid, String _name, double _rmb) {
		Map map = new HashMap();
		map.put("orderId", _uuid);
		map.put("orderName",_name);
		map.put("money", _rmb);
		return map;
	} 
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public int getStatusAndResp(Map params) throws Exception {
		String status = "0";//状态默认为失败
		int result = -1;
		status = (String) params.get("trade_status");
		String orderId = (String) params.get("out_trade_no");
		String orderName = (String) params.get("subject");
		
		if(TRADE_SUCCESS.equals(status) || TRADE_FINISHED.equals(status)){
			//成功 1.更新stuuser表中的coin指，2.新增充值日志coinlog
			String money = (String) params.get("total_fee");
			int coin = (int) (Double.parseDouble(money) * AlipayUtils.PROPORTION);
			Map _result = getUserIdByOrderIdAndOrderName(orderId,orderName); 
			int status1 = Integer.parseInt(_result.get("status1").toString());
					
			if(_result.get("stuUserId") == null || status1 == 1) return -1;
			int offset1 = -1;
			// 修改订单记录的状态值
			if(updateCoinLogStatus(orderId,orderName) > 0)
			{
				offset1 = updateStuuserCoin(coin,Integer.parseInt(_result.get("stuUserId").toString()));
			}
			else
			{
				throw new Exception("chanage coinLog status error..");
			}
			result = offset1 > 1 ? 1 : -1;
		}
		else if(!"WAIT_BUYER_PAY".equals(status)){ 
			deleteCoinLog(orderId,orderName);
			//回滚
			result = -1;
		}
		return result;
	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int updateCoinLogStatus(String orderId, String orderName) throws Exception {
		Map map = new HashMap();
		map.put("orderId",orderId);
		map.put("orderName", orderName);
		return this.getBaseDao().delete(PREFIX + ".updateCoinLogStatus", map);
	}

	/**
	 * 根据订单号和订单名称获取用户ID
	 * yesong 2014-12-23
	 * @param orderId
	 * @param orderName
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getUserIdByOrderIdAndOrderName(String orderId,
			String orderName) throws Exception {
		Map map1 = new HashMap();
		map1.put("orderId",orderId);
		map1.put("orderName", orderName);
		
		Map map = this.getBaseDao().get(PREFIX+ ".getUserIdByOrderIdAndOrderName", map1);
//		Object userId = map.get("stuUserId");
//		System.out.println("==========userId=============:" + userId); 
		return map;
	}

	/**
	 * 根据订单号和订单名称删除充值记录
	 * yesong 2014-12-23
	 * @param orderId
	 * @param orderName
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteCoinLog(String orderId,String orderName) throws Exception { 
		Map map = new HashMap();
		map.put("orderId",orderId);
		map.put("orderName", orderName);
		this.getBaseDao().delete(PREFIX + ".deleteCoinLogByOrderIdAndOrderName", map);
	}

	// 更新用户表COIN
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateStuuserCoin(int coin,int userId) throws Exception {
		Map map = new HashMap();
		map.put("coin",""+coin);
		map.put("operate", "1");
		map.put("userId",userId);
		return userServiceImpl.coinOperate(map); 
	}
	
	/**
	 * 新增充值记录
	 * @param coin
	 * @param _rmb
	 * @param userId
	 * @param orderId
	 * @param orderName
	 * @return
	 * @throws Exception  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int insertCoinLog(int coin, double _rmb,int userId,String orderId,String orderName,String content) throws Exception {
		Map map = new HashMap();
		map.put("coin",coin);
		map.put("money", _rmb);
		map.put("stuUserId", userId);
		map.put("orderId",orderId);
		map.put("orderName", orderName);
		map.put("groupId", 0);
		map.put("status1", 0);
		map.put("createTime", System.currentTimeMillis());
		map.put("content", content);
		return this.getBaseDao().save(PREFIX + ".insertCoinLog", map);
	}
}
