package com.bluemobi.pro.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.pro.pojo.Groupinfo;
import com.bluemobi.pro.pojo.Groupscale;
import com.bluemobi.pro.pojo.Grouptype;
import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.sys.bean.DataCache;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.CoinLogUtil;
import com.bluemobi.utils.DateUtils;
import com.bluemobi.utils.EasemobUtil;

/**
 * 群 impl
 * 
 * @author gaolei
 */
@Service
public class GroupServiceImpl extends BaseService {

    @Autowired
    public StuUserServiceImpl stuUserServiceImpl;

    @Autowired
    public CoinlogServiceImpl coinlogServiceImpl;

    @Autowired
    public GrouplivenessServiceImpl grouplivenessServiceImpl;

    @Autowired
    public GroupmemberServiceImpl groupmemberServiceImpl;

    public static final Integer DAY = 30; //获取30天内的数据
    
    public static String PREFIX = Groupinfo.class.getName();
    public static String PREFIX_STU = StuUser.class.getName();

    public int save(Groupinfo groupinfo) throws Exception {
        return this.getBaseDao().save(PREFIX + ".insert", groupinfo);
//    	return this.getBaseDao().saveWithUft8mb4(PREFIX + ".insert", groupinfo);
    }

    /**
     * 解散群
     * 
     * @author gaolei
     */
    public void delete(Map<String, Object> params) throws Exception {
        params.put("id", params.get("groupId"));
        // 解散群分配爱友币
        allot(params);
        
        // 删除群信息
        this.getBaseDao().update(PREFIX + ".delete", params);
        // 删除groupmember信息
        this.getBaseDao().update(PREFIX + ".deleteGroupmemberByGroupId", params);
    }
    
    // 解散群分配爱友币
    private void allot(Map<String, Object> params) throws Exception {
    	List<?> members = this.getBaseDao().getList(PREFIX + ".getMemberAndCoin", params);
    	allot(members);
    	updateMembersCoin(members);
	}
    
    //修改群成员爱友币
    private void updateMembersCoin(List<?> members) throws Exception {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("list", members);
    	this.getBaseDao().update(PREFIX + ".updateMembersCoin",map);
	}

	//分配群爱友币
    @SuppressWarnings("unchecked")
	public void allot(List<?> members) {
    	int t = (int) ((Map<String,Object>)members.get(0)).get("coin");
    	int min = 1;
    	Random rd = new Random();
    	int coin = 0;
    	int nums = members.size();
    	int m = t > nums ? nums : t;
    	for (int i = 1; i <= m; i++) {
    		if(i == nums) {
				coin = t;
			}else{
				int safeCoin = (t - (nums - i) * min ) / (nums - i) <= 0 ? 1 : (t - (nums - i) * min ) / (nums - i);
				coin = ((rd.nextInt(safeCoin * 100)) + (1 * 100)) / 100;
			}
			t = t - coin;
			((Map<String,Object>)members.get(i - 1)).put("addcoin", coin);
		}
    }
    
	/**
     * 查询群列表
     * 
     * @param params
     *            查询参数
     * @throws Exception
     * @author gaolei
     */
    public Page<List<Groupinfo>> getGroupList(Map<String, Object> params, int current, int pagesize)
            throws Exception {
        params.put("userId", params.get("userId") == null ? "0" : params.get("userId"));
        // int current = PageUtiles.parseCurrent((String)
        // params.get("pageNum"));
        // int pagesize = PageUtiles.parsePageSize((String)
        // params.get("pageSize"));
        return this.getBaseDao().page(PREFIX + ".getGroupList", params, current, pagesize);
    }

    /**
     * 查询我的群列表
     * 
     * @author gaolei
     */
    public List<?> getMyGroupList(Map<String, Object> params) throws Exception {
        params.put("stuUserId", params.get("userId"));
        // int current = PageUtiles.parseCurrent((String)
        // params.get("pageNum"));
        // int pagesize = PageUtiles.parsePageSize((String)
        // params.get("pageSize"));
        return this.getBaseDao().getList(PREFIX + ".getMyGroupList", params);
    }

    /**
     * 查询群规模
     * 
     * @author gaolei
     */
    public List<Map<String, Object>> queryGroupscale() throws Exception {
        return this.getBaseDao().getList(PREFIX + ".queryGroupscale");
    }

    /**
     * 查询群成员
     * 
     * @author gaolei
     */
    public List<Map<String, Object>> getMembers(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getList(PREFIX + ".getMembers", params);
    }

    /**
     * 查询群列表--非接口
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
    public Page<List<Groupinfo>> list(Map<String, Object> params, int current, int pagesize) {
        if (params.get("provinceId") == null || params.get("provinceId").toString().length() == 0
                || params.get("provinceId").toString().equals("")
                || params.get("provinceId").toString().equals("-1")) {// 查询所有省份，城市就不用判断
            params.put("provinceId", null);
            params.put("cityId", null);
            params.put("schoolId", null);
        }
        else if (params.get("cityId") == null || params.get("cityId").toString().length() == 0
                || params.get("cityId").toString().equals("")
                || params.get("cityId").toString().equals("-1")) {// 省份确定，城市未知，查询该省份下的所有城市的学校
            params.put("cityId", null);
            params.put("schoolId", null);
        }
        else if (params.get("schoolId") == null || params.get("schoolId").toString().length() == 0
                || params.get("schoolId").toString().equals("")
                || params.get("schoolId").toString().equals("-1")) {// 省份确定，城市确定，学校未知，查询该省份下的该城市的下的所有学校
            params.put("schoolId", null);
        }
        if (params.get("scaleId") == null || params.get("scaleId").toString().length() == 0
                || params.get("scaleId").toString().equals("")
                || params.get("scaleId").toString().equals("-1")) {// 所有规模
            params.put("scaleId", null);
        }
        if (params.get("groupType") == null || params.get("groupType").toString().length() == 0
                || params.get("groupType").toString().equals("")
                || params.get("groupType").toString().equals("-1")) {// 所有类型
            params.put("groupType", null);
        }
        Page page =  this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
        toAddSchool(page);
        return page;
    }
    
    
    private void toAddSchool(Page page) {
		Collection<?> list = page.getRows();
		for (Object object : list) {
			Map<String,Object> groupinfo = (Map<String,Object>) object;
			Object schoolObj = DataCache.getInstance().getObjectByCode("scs", "" + groupinfo.get("provinceId") + groupinfo.get("cityId") + groupinfo.get("schoolId"));
			if(schoolObj != null){
				String school =((Map<String,Object>)schoolObj).get("proName").toString();
				groupinfo.put("school",school);
			}
			else{
				groupinfo.put("school","");
			}
		} 
    }
    
    /**
     * 根据群编号获取群详细信息
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String, Object> findOneById(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".findOne", params);
    }

    /**
     * 根据群编号获取群详细信息
     * 
     * @author gaolei
     */
    public Map<String, Object> getDetail(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".getDetail", params);
    }

    /**
     * 更新单个群信息
     * 
     * @param params
     *            群编号
     * @return
     * @throws Exception
     */
    public int update(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".update", params);
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
                toSend(flag,list[i]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return type + "";
    }

    /**
     * 群审核通过给移动端发送消息
     * @param type
     * @param flag
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void toSend(String flag, String id) {
		Map map = new HashMap();
		map.put("id", id);
		Map actMap = null;
		try {
			actMap = this.getBaseDao().get(PREFIX + ".findOne", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(Integer.parseInt(flag) == ActivitiesServiceImpl.SUCCESS){
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX +String.valueOf(actMap.get("stuUserId")), G_SUCCESS.replace("s",actMap.get("name").toString()),"{'attr' : 'sys'}");
		}
		else{
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN, EasemobUtil.PREFIX +String.valueOf(actMap.get("stuUserId")),G_FAIL.replace("s",actMap.get("name").toString()),"{'attr' : 'sys'}");
		}
		
	}

	// 查询群规模
    public List<Groupscale> findScaleByParams(Map<String, Object> params) {
        try {
            return this.getBaseDao().getList(PREFIX + ".findScale", params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查询群类型
    public List<Grouptype> findTypeByParams(Map<String, Object> params) {
        try {
            return this.getBaseDao().getList(PREFIX + ".findType", params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发放爱心币
     * 
     * @author gaolei
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String sendMoney(Map<String, Object> params) throws Exception {
        String groupId = (String) params.get("groupId");
        int coin = Integer.parseInt((String) params.get("coin"));

        Map<String, Object> groupinfo = getDetail(params);
        // 群总爱心币
        int groupCoin = (Integer) groupinfo.get("coin");

        // 所有活跃的用户列表
        Map _map = new HashMap();
        _map.put("groupId", groupId);
        _map.put("startTime", getStartTime()) ;
        _map.put("endTime", getEndTime());
        
        
        List<Map<String, Object>> livenessUsers = grouplivenessServiceImpl
                .getLivenessUsers(_map);
        // 如果没有活跃用户，则返回错误
        if (livenessUsers.size() <= 0) {
            return Constant.ERROR_11;
        }
        // 如果前10%不够一个人，则发第一个
        int per10 = (int) Math.floor(livenessUsers.size() * 0.1);
        if (per10 <= 1) {
            per10 = 1;
        }
        // 把活跃度在前10%之后的remove
        for (int i = per10; i < livenessUsers.size(); i++) {
            livenessUsers.remove(i);
        }
        // 爱心币不够
        if (groupCoin < livenessUsers.size() * coin) {
            return Constant.ERROR_12;
        }
        // 前10%的用户id
        List<Integer> livenessUserIds = new ArrayList<Integer>();
        if (livenessUsers.size() > 0) {
            for (int i = 0; i < livenessUsers.size(); i++) {
                livenessUserIds.add((Integer) livenessUsers.get(i).get("stuUserId"));
            }
        }

        // 发放过程
        for (int i = 0; i < livenessUserIds.size(); i++) {
            int userId = livenessUserIds.get(i);
            // 1.更新stuuser表
            Map<String, Object> suParams = new HashMap<String, Object>();
            suParams.put("id", userId);
            // 查询出保函coin信息的map
            Map<String, Object> suResult = stuUserServiceImpl.findOneById(suParams);
            // 更新coin
            suParams.put("coin", (Integer) suResult.get("coin") + coin);
            stuUserServiceImpl.update2(suParams);
            
            // 记录爱佑币日志记录
            CoinLogUtil.insertCoinLog(new Coinlog(userId, coin, ""
            		,"您于"+ DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") +"收到来自群【" + groupinfo.get("name")  +"】发放的" + coin +"爱佑币"));

            
            // 2.添加coinlog信息
//            Coinlog coinlog = new Coinlog();
//            coinlog.setGroupId(groupId);
//            coinlog.setStuUserId(userId);
//            coinlog.setCoin(coin);
//            coinlog.setCreateTime(new Date().getTime());
//            coinlogServiceImpl.save(coinlog);
        }

        // 从群里减掉的爱心币
        int removeNum = livenessUserIds.size() * coin;
        Map<String, Object> grinfo = new HashMap<String, Object>();
        grinfo.put("id", groupId);
        grinfo.put("coin", (groupCoin - removeNum) + "");
        return this.getBaseDao().update(PREFIX + ".update", grinfo) + "";
    }

    private Object getEndTime() {
		return System.currentTimeMillis();
	}

	private Long getStartTime() {
		return  System.currentTimeMillis() - (DAY * 24 * 3600 * 1000L);
	}

	/**
     * 贡献coin到群
     * 
     * @author gaolei
     */
    public int sendCoinToGroup(Map<String, Object> params) throws Exception {
        // 贡献的coin
        int coin = Integer.parseInt((String) params.get("coin"));
        
        // 查询当前用户信息
        Map<String, Object> stuUser = new HashMap<String, Object>();
        stuUser.put("id", params.get("userId"));
        stuUser.put("userId", params.get("userId"));
        stuUser = this.getBaseDao().getObject(PREFIX_STU + ".findUserDetailById", stuUser);
        int myCoin = (Integer) stuUser.get("coin"); // 自己的coin
        if (myCoin < coin) { // 爱心币不足
            return Integer.parseInt(Constant.ERROR_12) ;
        }

        // 1.修改自己的icon数量
        stuUser.put("coin", (myCoin - coin) + "");
        @SuppressWarnings("unused")
		Object coin1 = stuUser.get("coin");
        this.getBaseDao().update(PREFIX_STU + ".update", stuUser);
        
        Map<String, Object> groupinfo = this.getBaseDao().getObject(PREFIX + ".getDetail", params);
        // 群原coin
        int groupCoin = (Integer) groupinfo.get("coin");
        groupinfo.put("coin", groupCoin + coin);
        
        // 记录爱佑币日志记录
        CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(params.get("userId").toString()), coin, ""
        		,"您于"+ DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") +"贡献群【" + groupinfo.get("name")  +"】" + coin +"爱佑币"));

        return this.getBaseDao().update(PREFIX + ".update", groupinfo);
    }
    
    /**
     * yesong 2015-2-12
     * @param params
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateMsgCount(Map params) throws Exception{
    	params.put("createTime", System.currentTimeMillis());
    	this.getBaseDao().save(PREFIX + ".insertMsgCount", params);
    }
    
    
    @SuppressWarnings("rawtypes")
	public Page getHotGroupList(Map<String,Object> params) throws Exception {
    	int pageNum = Integer.parseInt(params.get("pageNum").toString());
    	int pageSize = Integer.parseInt(params.get("pageSize").toString());
    	return this.getBaseDao().page(PREFIX + ".getHotGroups", params, pageNum, pageSize);
    }
    
    /**
     * 查询用户在该群 是否已签到 
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> getDaySign(Map<String,Object> params) throws Exception {
     	return this.getBaseDao().get(PREFIX + ".getSignType", params);   
    }
    
    /**
     * 群签到
     * @param params
     * @throws Exception
     */
    public void groupSign(Map<String,Object> params) throws Exception {
    	this.getBaseDao().update(PREFIX + ".sgin", params);
    }
    
    /**
     * 获取群二维码
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> groupQrcode(Map<String,Object> params) throws Exception {
    	return this.getBaseDao().get( PREFIX + ".getQrCode", params);
    }    
    
    /**
     * 修改群寄语
     * @param params
     * @return
     * @throws Exception
     */
    public int editMessage(Map<String,Object> params) throws Exception {
    	return this.getBaseDao().update(PREFIX + ".editMessage", params);
    }
    
   // public List<?> getGrank(Map<>) 
    public void updateGroupCoin(Map<String,Object> params) throws Exception {
    	this.getBaseDao().update(PREFIX + ".updateGroupCoin", params);
    }
    
    /**
     * 用户是否已加入群
     * @param params
     * groupId 群ID
     * userId  用户ID
     * @return
     * @throws Exception
     */
    public boolean isInThisGroup(Map<String,Object> params) throws Exception {
    	int count = this.getBaseDao().get(PREFIX + ".findByGroupIdAndUserId", params);
    	return count > 0 ? true : false;
    }
    
    public static final String G_SUCCESS = "您创建的群[s]审核成功";
    public static final String G_FAIL = "您创建的群[s]审核未通过";
    
}