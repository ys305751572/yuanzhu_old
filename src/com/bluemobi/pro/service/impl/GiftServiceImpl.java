package com.bluemobi.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.constant.Constant;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.ImageUtils;
import com.bluemobi.utils.QrCodeUtil;

/**
 * 礼品中心业务模块
 * @author yes
 *
 */
@Service
public class GiftServiceImpl extends BaseService{

	public static final String PRIFIX = GiftServiceImpl.class.getName();
	
	/**
	 * 后台新增礼品
	 * @param params
	 * @throws Exception
	 */
	public int addGift(Map<String,Object> params,MultipartFile file) throws Exception {
		params.put("id", -1);
		int flag = -1;
		String[] picPath = ImageUtils.saveImage(file, true);
		params.put("pic", picPath[0]);
		params.put("small_image_url", picPath[1]);
		String coinStr = params.get("coin") == null ? "0" :  params.get("coin").toString();
		int coin = Integer.parseInt(coinStr);
		double price = coin / 100.0;
		params.put("price", price);
		flag = this.getBaseDao().save(PRIFIX + ".insert", params);
		QrCodeUtil.generateQrCode(Constant.QRCODE_GIFT, params.get("id").toString(), "gift",true, coin);
		return flag;
	}
	
	/**
	 * 后台礼品列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Page getPage(Map<String,Object> params) throws  Exception {
		Integer rows = new Integer((String) params.get("rows"));
	    Integer pages = new Integer((String) params.get("page"));
	    return this.getBaseDao().page(PRIFIX + ".giftList", params, pages, rows);
	}
	
	/**
	 * 后台更新礼品信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateGift(Map<String,Object> params,MultipartFile file) throws Exception {
		String[] picPath = ImageUtils.saveImage(file, true);
		params.put("pic", picPath[0]);
		params.put("small_image_url", picPath[1]);
		String coinStr = params.get("coin") == null ? "0" :  params.get("coin").toString();
		int coin = Integer.parseInt(coinStr);
		double price = coin / 100.0;
		params.put("price", price);
		return this.getBaseDao().update(PRIFIX + ".updateGift", params);
	}
	
	/**
	 * 后台删除礼品记录
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delGift(Map<String,Object> params) throws Exception {
		return this.getBaseDao().delete(PRIFIX + ".delGift", params);
	}
	
	/**
	 * 后台根据ID获取礼品信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getById(Map<String,Object> params) throws Exception {
		return this.getBaseDao().get(PRIFIX + ".getById", params);
	}
	
	/**
	 * 修改礼品上下架状态
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int exchangeGiftStatus(Map<String,Object> params) throws Exception {
		return this.getBaseDao().update(PRIFIX + ".updateGift", params);
	}
	
	/**
	 * 获取订单列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Page getOrderList(Map<String,Object> params) throws Exception {
		Integer rows = new Integer((String) params.get("rows"));
	    Integer pages = new Integer((String) params.get("page"));
		return this.getBaseDao().page(PRIFIX + ".getOrderList", params, pages, rows);
	}
	
	/** *********************接口************************ */
	
	/**
	 * 获取礼品列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getGiftList(Map<String,Object> params) throws Exception{
		List<Map<String, Object>> giftList = this.getBaseDao().getList(PRIFIX + ".list", params); 
		for (Object object : giftList) {
			Map<String,Object> gift = (Map<String,Object>) object;
			isMeet(gift);
		}
		return giftList;
	}
	
	public void isMeet(Map<String,Object> gift) {
		int need = Integer.parseInt(gift.get("gift_coin") == null ? "0" :gift.get("gift_coin").toString()) ;
		int have = Integer.parseInt(gift.get("haveCoin") == null ? "0" : gift.get("haveCoin").toString());
		if(need > have) {
			gift.put("can_exchange", "1");
		}
		else {
			gift.put("can_exchange", "0");
		}
	}
	
	/**
	 * 获取礼品详情
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,Object> getGiftDetail(Map<String,Object> params) throws Exception {
		Map<String,Object> _returnParmas = new HashMap<String, Object>();
		
		List<?> detailList = this.getBaseDao().getList(PRIFIX + ".detail", params);
		if(detailList == null || detailList.size() <= 0) return null;
		_returnParmas.put("gift_desc",  ((Map)detailList.get(0)).get("gift_detail"));
		for (Object object : detailList) {
			((Map<String,Object>)object).remove("gift_detail");
		}
		_returnParmas.put("gift_exchange_list", detailList);
		return _returnParmas;
	}
	
	/**
	 * 兑换礼品
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int exchangeGift(Map<String,Object> params) throws Exception {
		//TODO 减掉用户的爱佑币
		if(!checkCoin(params)) return -1;
		this.getBaseDao().save(PRIFIX + ".exchange", params);
		return deductCoin(params);
	}

	/**
	 * 检测爱佑币是否足够
	 * @param params
	 * @return
	 */
	private boolean checkCoin(Map<String, Object> params) throws Exception{
		Map<String,Object> map = this.getBaseDao().get(PRIFIX + ".getCoinAndPirce", params);
		int totalCoin = Integer.parseInt(map.get("coin").toString()) * Integer.parseInt(params.get("nums").toString()) ;
		int haveCoin = Integer.parseInt(map.get("haveCoin").toString());
		params.put("totalCoin", totalCoin);
		return totalCoin > haveCoin ? false : true;
	}

	/**
	 * 扣除用户对应爱佑币
	 * @param params
	 * @throws Exception
	 */
	private int deductCoin(Map<String, Object> params) throws Exception {
		return this.getBaseDao().update(PRIFIX + ".updateCoin", params);
	}
}
