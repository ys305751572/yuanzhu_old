package com.bluemobi.pro.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.pro.pojo.Years;
import com.bluemobi.sys.bean.DataCache;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.CoinLogUtil;
import com.bluemobi.utils.DateUtils;
import com.bluemobi.utils.PropertiesUtils;
import com.bluemobi.utils.ValidateUtils;

/**
 * 
 *  
 * <p>
 * Title: StuUserServiceImpl.java
 * </p>
 *    
 * <p>
 * Description: 移动端用户serivce
 * </p>
 *    @author yesong   @date 2014-11-13 下午04:45:16  @version V1.0 
 * ------------------------------------ 历史版本
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Service
public class StuUserServiceImpl extends BaseService {

    public static String PREFIX = StuUser.class.getName();

    /**
     * 更改用户在线状态
     * 
     * @param lineStatus
     * @return
     * @throws Exception
     */
    public int updatelineStatus(Map<?, ?> map) throws Exception {
        return this.getBaseDao().update(PREFIX + ".updateLineStatus", map);
    }

    // 基础方法 update
    public int update(Map<String, Object> params) throws Exception {
    	String id = params.get("id").toString();
    	String flag = params.get("status").toString();
    	if (Constant.WAIT.equals(flag)) {
            Map<String,Object> reParam = findRecUserId(id);
            String recUserId = reParam.get("recUserId") == null ? "" : reParam.get("recUserId").toString();
            String nickname = reParam.get("nickname") == null ? "" : reParam.get("nickname").toString();
            String coin = null;
            String coin_rec = null;
            if (StringUtils.isBlank(recUserId) || Integer.parseInt(recUserId) == 0) {
                coin = PropertiesUtils.getPropertiesValues("coin_card",
                        Constant.COIN_PROP);
            }
            else {
                coin = PropertiesUtils.getPropertiesValues("coin_us", Constant.COIN_PROP);
                coin_rec = PropertiesUtils.getPropertiesValues("coin_rec", Constant.COIN_PROP);
            }
            if (StringUtils.isNotBlank(coin)) {
                addCoin(Integer.parseInt(coin), id);
                CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(id), Integer.parseInt(coin), Constant.CARD));
            }
            //yesong 2014-1-27 协助认证他人增加100安心币
            if(StringUtils.isNotBlank(coin_rec)){
            	addCoin(Integer.parseInt(coin_rec),recUserId);
            	CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(recUserId), Integer.parseInt(coin_rec), Constant.ASSIST.replaceAll("a", nickname)));
            }
        }
        return this.getBaseDao().update(PREFIX + ".update", params);
    }
    
    
 // 基础方法 update
    public int update2(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".update", params);
    }

    // 基础方法 findOneById
    public Map<String, Object> findOneById(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".findOne", params);
    }

    /**
     * 登录检测
     * 
     * @param user
     * @return
     * @throws Exception
     */
	public Map queryStrUser(StuUser user) throws Exception {
        return this.getBaseDao().get(PREFIX + ".queryLogin", user);
    }

    /**
     * 
     * @param map
     *            请求参数,必须参数如下: userId
     * @return json格式信息
     * @author gaolei
     */
    public Map<String, Object> findUserDetailById(Map<String, Object> params) throws Exception {
    	Map map = this.getBaseDao().getObject(PREFIX + ".findUserDetailById", params);
    	String provinceId= map.get("provinceId").toString();
    	if(ValidateUtils.isZHIXIA(provinceId)){
    		Map _map = new HashMap();
        	_map.put("provinceId", provinceId);
        	_map.put("cityId", provinceId);
        	_map.put("schoolId",map.get("schoolId"));
        	_map.put("collegeId",map.get("collegeId"));
        	_map.put("specialtyId", map.get("specialtyId"));
        	List list = getScs(_map);
        	inputScs2Params2(list,map);
    	}else{
    		map = null;
    		map = this.getBaseDao().getObject(PREFIX + ".findUserDetailById2", params);
    	}
        return map;
    }

    /**
     * 更新用户的在线状态
     * 
     * @author gaolei
     */
    public int updateOnlineStatus(Map<String, Object> params) throws Exception {
        if (params.keySet().contains("userId") && params.keySet().contains("isOnline")) {
            return this.getBaseDao().update(PREFIX + ".updateOnlineStatus", params);
        }
        else {
            return 0;
        }
    }

    /**
     * 查询用户好友信息
     * 
     * @author gaolei
     */
    public List<Map<String, Object>> queryFriends(Map<String, Object> params) throws Exception {
        params.put("id", params.get("userId"));
        return this.getBaseDao().getList(PREFIX + ".queryFriends", params);
    }

    /**
     * 查询所有用户
     * 
     * @param params
     *            查询参数
     * @return Page<Map<String, Object>> 分页对象
     * @throws Exception
     * @author gaolei
     */
    public Page queryAll(Map<String, Object> params, int current, int pagesize) throws Exception {
        // 处理兴趣查询条件
        String interestId = (String) params.get("interestId");
        String labelsId = (String) params.get("lablesId");
        if (org.apache.commons.lang.StringUtils.isNotBlank(interestId)) {
            String[] interestIdArr = interestId.split(",");
            params.put("interestIdArr", interestIdArr);
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(labelsId)) {
            String[] labelsIdArr = labelsId.split(",");
            params.put("labelsIdArr", labelsIdArr);
        }
        return this.getBaseDao().page(PREFIX + ".queryAll", params, current, pagesize);
    }

    /**
     * @Description 个人中心模块，通过本接口获取个人资料信息
     * @param 参数列表
     *            map: String userId 用户id ;
     * @return Map<String, Object>
     * @date 2014-11-18 14:48:04
     * @author 龙哲
     */
    @Transactional
    public Map<String, Object> info(Map<String, Object> params) throws Exception {
        Map<String, Object> map = this.getBaseDao().getObject(PREFIX + ".info", params);
        Object province = map.get("provinceId");
        if(ValidateUtils.isZHIXIA(province.toString())){
        	Map _map = new HashMap();
        	_map.put("provinceId", province);
        	_map.put("cityId", province);
        	_map.put("schoolId",map.get("schoolId"));
        	_map.put("collegeId",map.get("collegeId"));
        	_map.put("specialtyId", map.get("specialtyId"));
        	List list = getScs(_map);
        	inputScs2Params(list,map);
        }
        queryInterestIds((Integer) map.get("id"), map);
        return map;
    }

    private void inputScs2Params(List list, Map<String, Object> map) {
    	String _scs = null;
		for (Object object : list) {
			Map _map = (Map) object;
			if(_map.get("level").toString().equals("1")){
				_scs = "school";
			}else if(_map.get("level").toString().equals("2")){
				_scs = "college";
			}else if(_map.get("level").toString().equals("3")){
				_scs = "specialty";
			}
			map.put(_scs, _map.get("proName") == null ? "" : _map.get("proName"));
		}
	}
    
    private void inputScs2Params2(List list, Map<String, Object> map) {
    	String _scs = null;
		for (Object object : list) {
			Map _map = (Map) object;
			if(_map.get("level").toString().equals("1")){
				_scs = "schoolName";
			}else if(_map.get("level").toString().equals("2")){
				_scs = "collegeName";
			}else if(_map.get("level").toString().equals("3")){
				_scs = "specialtyName";
			}
			map.put(_scs, _map.get("proName"));
		}
	}

	public List getScs(Map map){
    	List lsit = null;
    	try {
    		lsit = this.getBaseDao().getList(PREFIX + ".getScsByUserInfo",map);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return lsit;
    }
    
    /**
     * yesong 2014-12-18 查询该用户的所有兴趣，并以逗号分隔
     * 
     * @param integer
     * @return
     * @throws Exception
     */
    
	private void queryInterestIds(Integer userId, Map params) throws Exception {
        List<?> list = this.getBaseDao().getList(PREFIX + ".queryInterestIds", userId);
        StringBuffer bufferIds = new StringBuffer();
        StringBuffer bufferNames = new StringBuffer();
        for (Object object : list) {
            Map<?, ?> map = (Map<?, ?>) object;
            int inId = (Integer) map.get("interestId");
            String name = (String) map.get("inname");
            bufferIds.append("" + inId + ",");
            bufferNames.append(name + ",");
        }
        String inIds = bufferIds != null && bufferIds.toString().length() > 0 ? bufferIds
                .substring(0, bufferIds.length() - 1) : "";
        String names = bufferNames != null && bufferNames.toString().length() > 0 ? bufferNames
                .substring(0, bufferNames.length() - 1) : "";
        // return
        params.put("insterestIds", inIds);
        params.put("insterestNames", names);
    }

    /**
     * 保存用户信息
     * 
     * @author yesong
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional
    public int saveInfo(Map<String, Object> params) throws Exception {
        updateInterest(params);
        return this.getBaseDao().update(PREFIX + ".updateInfo", params);
    }

    /**
     * app端传过来的兴趣ID是以逗号分隔，现在将IDs分隔保存在MAP中 yesong 2014-12-18
     * 
     * @param params
     * @return
     * @throws Exception
     */
    private void updateInterest(Map<String, Object> params) throws Exception {

        int userId = (null != params.get("userId") ? Integer.parseInt(params.get("userId")
                .toString()) : -1);
        // 更新关联关系之前先删除之前的关联关系
        deleteInsterestByUserId(userId);

        String insterestIds = (String) params.get("insterestIds");
        String[] ids = null;
        if (StringUtils.isNotBlank(insterestIds)) {
            ids = insterestIds.split(",");
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    insertIds(id, userId);
                }
            }
        }
    }

    /**
     * 删除该用户的之前的关联关系 yesong 2014-12-18
     * 
     * @param userId
     * @throws Exception
     */
    private void deleteInsterestByUserId(int userId) throws Exception {
        this.getBaseDao().delete(PREFIX + ".deleteInsterestByUserId", userId);
    }

    /**
     * 保存兴趣ID yesong 2014-12-18
     * 
     * @throws Exception
     */
    private void insertIds(String inId, int userId) throws Exception {
        Map map = new HashMap();
        map.put("inId", Integer.parseInt(inId));
        map.put("userId", userId);
        this.getBaseDao().save(PREFIX + ".insertStuInsterest", map);
    }

    /**
     * 修改手机
     * 
     * @author yesong
     * @param params
     * @return
     * @throws Exception
     */
    public int updateMobile(Map<String, Object> params) throws Exception {
        // 查询该手机号是否已存在
        List isHas = this.getBaseDao().getList(PREFIX + ".queryUserByTel", params.get("mobile"));
        if (isHas != null && isHas.size() > 0) {
            return -1;
        }
        else {
            return this.getBaseDao().update(PREFIX + ".updateMobile", params);
        }
    }

    /**
     * 修改头像
     * 
     * @author yesong
     * @param params
     * @return
     * @throws Exception
     */
    public int updateHead(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".updateHead", params);
    }

    /**
     * 学生认证
     * 
     * @author yesong
     * @param params
     * @return
     * @throws Exception
     */
    public int cardAuth(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".cardAuth", params);
    }

    /**
     * 他人协助认证
     * 
     * @author yeson
     * @param params
     * @return
     * @throws Exception
     */
    public int userAuth(Map<String, Object> params) throws Exception {
        // 判断推荐人是否存在
		Map _user = this.getBaseDao().getObject(PREFIX + ".queryUserByMobileAndName", params);
		
        if (_user != null && !_user.isEmpty()) {
        	if(params.get("userId").toString().equals(_user.get("id").toString())) return -2;
        	
            Object resUserId = _user.get("id");
            if (resUserId != null) {
                params.put("recUserId", resUserId.toString());
                params.put("status", "3");
                return this.getBaseDao().update(PREFIX + ".addAuth", params);
            }
            else {
                return -1;
            }
        }
        else {
            return -1;
        }
    }

    /**
     * 查看签名
     * 
     * @author yeson
     * @param params
     * @return
     * @throws Exception
     */
    public Map myWord(Map<String, Object> params) throws Exception {
        return this.getBaseDao().get(PREFIX + ".myWord", params);
    }

    /**
     * 更新签名
     * 
     * @author yeson
     * @param params
     * @return
     * @throws Exception
     */
    public int saveWord(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".saveWord", params);
    }

    /**
     * 查询爱心币
     * 
     * @author yeson
     * @param params
     * @return
     * @throws Exception
     */
    public Map coin(Map<String, Object> params) throws Exception {
        return this.getBaseDao().get(PREFIX + ".getCoin", params);
    }

    /**
     * 操作爱心币
     * 
     * @author yeson
     * @param params
     * @return
     * @throws Exception
     */
    public int coinOperate(Map<String, Object> params) throws Exception {
        int in_coin = Integer.parseInt((String) params.get("coin"));
        Map map2 = this.coin(params);
        int out_coin = 0;
        if (map2 != null) {
            Object out_coin_obj = map2.get("coin");
            if (out_coin_obj != null) {
                out_coin = (Integer) out_coin_obj;
            }
        }
        String operateStr = (String) params.get("operate");
        int coin = 0;
        if ("1".equals(operateStr)) {
            coin = in_coin + out_coin;
        }
        else if ("2".equals(operateStr)) {
            if ((out_coin - in_coin) >= 0) {
                coin = out_coin - in_coin;
            }
            else {
                // 余额不够
                return -1;
            }
        }
        else {
            throw new Exception("操作类型异常..");
        }
        params.put("coin", coin);
        int offest = this.getBaseDao().update(PREFIX + ".coinOperate", params);
        return offest;
    }

    public Page coinList(Map<String, Object> params) throws Exception {
        int pageNum = Integer.parseInt((String) params.get("pageNum"));
        int pageSize = Integer.parseInt((String) params.get("pageSize"));
        return this.getBaseDao().page(PREFIX + ".coinList", params, pageNum, pageSize);
    }

    /**
     * 贡献爱心币给好友
     * 
     * @author gaolei
     */
    public String sendCoinToFriend(Map<String, Object> params) throws Exception {
        int sendCoin = Integer.parseInt((String) params.get("coin")); // 贡献的coin

        // 查询当前用户信息
        Map<String, Object> stuUser = new HashMap<String, Object>();
        stuUser.put("id", params.get("userId"));
        stuUser = this.getBaseDao().getObject(PREFIX + ".findUserDetailById", stuUser);
        int myCoin = (Integer) stuUser.get("coin"); // 自己的coin
        if (myCoin < sendCoin) { // 爱心币不足
            return Constant.ERROR_12;
        }

        // 1.修改自己的icon数量
        stuUser.put("coin", (myCoin - sendCoin) + "");
        this.getBaseDao().update(PREFIX + ".update", stuUser);

        // 查询好友信息
        Map<String, Object> friend = new HashMap<String, Object>();
        friend.put("id", params.get("friendId"));
        friend = this.getBaseDao().getObject(PREFIX + ".findUserDetailById", friend);
        int friendCoin = (Integer) friend.get("coin"); // friend的coin
        // 2.修改好友的icon数量
        friend.put("coin", (friendCoin + sendCoin) + "");
        this.getBaseDao().update(PREFIX + ".update", friend);
        
        //TODO 新增日志记录
        CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(params.get("friendId").toString()), sendCoin, ""
        		,"您于"+ DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") +"收到来自好友【" + stuUser.get("nickname")  +"】给予的" + sendCoin +"爱佑币"));
        
        CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(params.get("userId").toString()), sendCoin, ""
        		,"您于"+ DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") +"给予好友【" + friend.get("nickname")  +"】" + sendCoin +"爱佑币"));

        return Constant.MSG_OK;
    }

    /**
     * 查询移动端用户列表--非接口
     * 
     * @param params
     *            查询条件
     * @param current
     *            页码
     * @param pagesize
     *            每页显示的条数
     * @author 涂奕恒
     * @return
     */
    public Page<List<StuUser>> list(Map<String, Object> params, int current, int pagesize) {
        if (params.get("provinceId") == null || params.get("provinceId").toString().length() == 0
                || params.get("provinceId").toString().equals("")
                || params.get("provinceId").toString().equals("-1")) {// 鏌ヨ鎵�湁鐪佷唤锛屽煄甯傚氨涓嶇敤鍒ゆ柇
            params.put("provinceId", null);
            params.put("cityId", null);
            params.put("schoolId", null);
        }
        else if (params.get("cityId") == null || params.get("cityId").toString().length() == 0
                || params.get("cityId").toString().equals("")
                || params.get("cityId").toString().equals("-1")) {// 鐪佷唤纭畾锛屽煄甯傛湭鐭ワ紝鏌ヨ璇ョ渷浠戒笅鐨勬墍鏈夊煄甯傜殑瀛︽牎
            params.put("cityId", null);
            params.put("schoolId", null);
        }
        else if (params.get("schoolId") == null || params.get("schoolId").toString().length() == 0
                || params.get("schoolId").toString().equals("")
                || params.get("schoolId").toString().equals("-1")) {// 鐪佷唤纭畾锛屽煄甯傜‘瀹氾紝瀛︽牎鏈煡锛屾煡璇㈣鐪佷唤涓嬬殑璇ュ煄甯傜殑涓嬬殑鎵�湁瀛︽牎
            params.put("schoolId", null);
        }
        if (params.get("startYear") == null || params.get("startYear").toString().length() == 0
                || params.get("startYear").toString().equals("")
                || params.get("startYear").toString().equals("-1")) {// 鐘舵�鏈煡
            params.put("startYear", null);
        }
        if (params.get("status") == null || params.get("status").toString().length() == 0
                || params.get("status").toString().equals("")
                || params.get("status").toString().equals("-1")) {// 鐘舵�鏈煡
            params.put("status", null);
        }
        if ((params.get("validate") == null) || (params.get("validate").toString().length() == 0)
				|| (params.get("validate").toString().equals("")) || (params.get("validate").toString().equals("-1"))) {
			params.put("validate", null);
		}
        Page page = this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
        toAddSchool(page);
        return page;
    }

    private void toAddSchool(Page page) {
		Collection<?> list = page.getRows();
		for (Object object : list) {
			Map<String,Object> stuuser = (Map<String,Object>) object;
			Object schoolObj = DataCache.getInstance().getObjectByCode("scs", "" + stuuser.get("provinceId") + stuuser.get("cityId") + stuuser.get("schoolId"));
			Object collegeObj =DataCache.getInstance().getObjectByCode("scs" ,"" + stuuser.get("provinceId") + stuuser.get("cityId") + stuuser.get("collegeId"));
			if(schoolObj != null){
				String school =((Map<String,Object>)schoolObj).get("proName").toString();
				stuuser.put("school",school);
				System.out.println(stuuser.get("id").toString());
			}
			else{
				
				stuuser.put("school","");
			}
			if(collegeObj != null) {
				stuuser.put("college", ((Map<String,Object>)collegeObj).get("proName").toString());
			}
			else{
				stuuser.put("college", "");
			}
			
		} 
    }
	/**
     * 根据编号查看移动端用户的详细信息
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> getById(Map<String, Object> params) throws Exception {
    	Map map = this.getBaseDao().getObject(PREFIX + ".get", params);
    	Object province = map.get("provinceId");
        if(ValidateUtils.isZHIXIA(province.toString())){
        	Map _map = new HashMap();
        	_map.put("provinceId", province);
        	_map.put("cityId", province);
        	_map.put("schoolId",map.get("schoolId"));
        	_map.put("collegeId",map.get("collegeId"));
        	_map.put("specialtyId", map.get("specialtyId"));
        	List list = getScs(_map);
        	inputScs2Params(list,map);
        }
        return map;
    }

    
    /**
     * 根据编号查看移动端用户的详细信息
     * @author yesong 2015-1-26
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> getById2(int id) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".checkUser", id);
    }
    /**
     * 更新多个群信息
     * 
     * @param params
     *            群编号
     * @param session
     *            session对象
     * @return
     */
    public String updates(Map<String, Object> params) {
        // 要处理的数据集，以字符串的形式存在，中间用逗号隔开
        String ids = params.get("ary").toString();
        // 获取到要处理的反馈id的数组
        String[] list = ids.split(",");
        // 获取当前处理的状态
        String flag = (String) params.get("status");

        int type = 0;
        for (int i = 0; i < list.length; i++) {
            params = new HashMap<String, Object>();
            params.put("id", list[i]);
            params.put("status", flag);
            try {
                type += this.getBaseDao().update(PREFIX + ".update", params);
                // yesong 2014-12-29 判断 1.如果审核成功给用户新增爱心币
                // 2.如果是学生证认证加500爱心币，是他人协助认证加200爱心币
                if (Constant.WAIT.equals(flag)) {
                    Map<String,Object> reParam = findRecUserId(list[i]);
                    
                    String recUserId = reParam.get("recUserId") == null ? "" : reParam.get("recUserId").toString();
                    String nickname = reParam.get("nickname") == null ? "" : reParam.get("nickname").toString();
                    
                    String coin = null;
                    String coin_rec = null;
                    if (StringUtils.isBlank(recUserId) || Integer.parseInt(recUserId) == 0) {
                        coin = PropertiesUtils.getPropertiesValues("coin_card",
                                Constant.COIN_PROP);
                    }
                    else {
                        coin = PropertiesUtils.getPropertiesValues("coin_us", Constant.COIN_PROP);
                        coin_rec = PropertiesUtils.getPropertiesValues("coin_rec", Constant.COIN_PROP);
                    }
                    if (StringUtils.isNotBlank(coin)) {
                        addCoin(Integer.parseInt(coin), list[i]);
                        CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(list[i]), Integer.parseInt(coin), Constant.CARD));
                    }
                    //yesong 2014-1-27 协助认证他人增加100安心币
                    if(StringUtils.isNotBlank(coin_rec)){
                    	addCoin(Integer.parseInt(coin_rec),recUserId);
                    	CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(recUserId), Integer.parseInt(coin_rec), Constant.ASSIST.replaceAll("a", nickname)));
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return type + "";
    }

    /**
     * yesong 2014-12-29
     * 
     * @param coin
     * @param userId
     * @throws Exception
     */
    public void addCoin(int coin, String userId) throws Exception {
        Map map = new HashMap();
        map.put("coin", "" + coin);
        map.put("userId", userId);
        map.put("operate", "1");
        coinOperate(map);
    }

    private Map<String,Object> findRecUserId(String id) throws Exception {
        return this.getBaseDao().get(PREFIX + ".findRecUserId", id);
    }

    public int findPerfectInfo(String id) throws Exception {
        return this.getBaseDao().get(PREFIX + ".findPerfectInfo", id);
    }

    // 查询入学年份
    public List<Years> findYearsByParams(Map<String, Object> params) {
        try {
            return this.getBaseDao().getList(PREFIX + ".findYears", params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取个人二维码
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> getQrCode(Map<String,Object> params) throws Exception{
    	return this.getBaseDao().get(PREFIX + ".getQrCode", params);
    } 
    
    /**
     * 2015-6-23
     * @author yesong
     * @param params
     * @return
     * @throws Exception
     * @Description: 获取个人中心首页 活动消息数/任务消息数
     */
    public Map<String,Object> getIndex(Map<String,Object> params) throws Exception {
    	Map<String,Object> actCount = this.getBaseDao().get(PREFIX + ".getActCount", params);
    	Map<String,Object> taskCount = this.getBaseDao().get(PREFIX + ".getTaskCount", params);
    	//Map<String,Object> rsMap = new HashMap<String,Object>();
    	actCount.putAll(taskCount);
    	return actCount;
    }
    
    // ***********************************************************************
    // *****************************三期接口*************************************
    // ***********************************************************************
    
    /**
     * 绑定支付宝账号
     * @param params
     * @throws Exception
     */
    public void bindAlipay(Map<String,Object> params) throws Exception {
    	this.getBaseDao().update(PREFIX + ".bindAlipay", params);
    }
    
    /**
     * 我的钱包
     * @param params
     * @throws Exception
     */
    public Map<String,Object> wallet(Map<String,Object> params) throws Exception {
    	return this.getBaseDao().getObject(PREFIX + ".wallet", params);
    }
    
    /**
     * 根据类型获取记录信息
     * type 类型 0.支付宝 1。积分
     * @param params
     * @return
     * @throws Exception
     */
    public List recordList(Map<String,Object> params) throws Exception {
    	return this.getBaseDao().getList(PREFIX + ".findRecordList", params);
    }
    
    @Transactional
	public int exchangeVlidate(Map<String,Object> params) throws Exception {
		return this.getBaseDao().update(PREFIX + ".exchangevlidate", params);
	}
}
