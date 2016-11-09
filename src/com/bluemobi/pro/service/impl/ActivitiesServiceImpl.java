package com.bluemobi.pro.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.ActComment;
import com.bluemobi.pro.pojo.Activities;
import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.EasemobUtil;
import com.bluemobi.utils.QrCodeUtil;

/**
 * 
 *  
 * <p>
 * Title: ActivitiesServiceImpl.java
 * </p>
 *    
 * <p>
 * Description: 活动service
 * </p>
 *    @author yesong    @date 2014-11-14 下午02:35:31  @version V1.0 
 * ------------------------------------ 历史版本
 * 
 */
@Service
public class ActivitiesServiceImpl extends BaseService {

    public static final String PREFIX = Activities.class.getName();
    public static final String PREFIX_STU = StuUser.class.getName();

    public static final Integer DEFAULT_STATUS = 1;
    
    @Autowired
    public StuUserServiceImpl stuUserServiceImpl;
  
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    
    public static final String G_SUCCESS = "您创建的活动审核通过";
    public static final String G_FAIL = "您创建的活动审核未通过";
    
    public static final String A_SUCCESS = "您申请的活动审核通过";
    public static final String A_FAIL = "您申请的活动审核未通过";
    /**
     * 新增活动
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public Activities insertActivities(Map<String, Object> map) throws Exception {
        Activities activities = null;
        String userIdSting = (String) map.get("userId");
        if (StringUtils.isBlank(userIdSting)) {
            throw new Exception("用户ID不能为空");
        }
        int userId = Integer.parseInt(userIdSting);
        
        getSchoolId(map); //获取学校Id
        
        int schoolId = (Integer) map.get("schoolId");
        int provinceId = (Integer) map.get("provinceId");
        int cityId = (Integer) map.get("cityId");
        
        String picture = (String) map.get("picture");
        String content = (String) map.get("content");
        String location = (String) map.get("location");

        String typeIdStr = (String) map.get("typeId");
        int typeId = StringUtils.isNotBlank(typeIdStr) ? Integer.parseInt(typeIdStr) : 0;

        String startTimeStr = (String) map.get("startTime");
        long startTime = StringUtils.isNotBlank(startTimeStr) ? Long.parseLong(startTimeStr) : 0;
        String endTimeStr = (String) map.get("endTime");
        long endTime = StringUtils.isNotBlank(endTimeStr) ? Long.parseLong(endTimeStr) : 0;

        String maxCountStr = (String) map.get("nums");
        int maxCount = StringUtils.isNotBlank(maxCountStr) ? Integer.parseInt(maxCountStr) : 100;
        
        String pwidthStr =(String) map.get("pwidth");
        int pwidth = StringUtils.isNotBlank(pwidthStr)? Integer.parseInt(pwidthStr) : 0;
        String pheightStr = (String) map.get("pheight");
        int pheight = StringUtils.isNotBlank(pheightStr)?Integer.parseInt(pheightStr):0;
        
        double lon = map.get("lon") == null ? 0 : Double.parseDouble(map.get("lon").toString());
        double lat = map.get("lat") == null ? 0 : Double.parseDouble(map.get("lat").toString());
        
        String title = map.get("title") == null ? "" : map.get("title").toString();
        
        int permission = map.get("permission") == null ? 0 : Integer.parseInt(map.get("permission").toString());
        activities = new Activities(provinceId,cityId,schoolId,content, System.currentTimeMillis(), typeId, userId, startTime,
                endTime, picture, maxCount, location,pwidth,pheight,lon,lat,title,permission);
        int offset = this.getBaseDao().save(PREFIX + ".insertEntity", activities);
        if (offset > 0) {
            activities.setStatus(0);
            QrCodeUtil.generateQrCode(Constant.QRCODE_ACT, "" + activities.getId(), "activities",false , 0);
            return activities;
        }
        return null;
    }
    
    /**
     * 获取用户学校ID
     * @param userId
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void getSchoolId(Map params) throws Exception {
    	Map map = new HashMap();
    	map.put("id", params.get("userId"));
    	
    	Map gisMap = this.getBaseDao().get(PREFIX + ".queryGisByUserId", map);
    	params.put("provinceId", gisMap.get("provinceId"));
    	params.put("cityId", gisMap.get("cityId"));
    	params.put("schoolId", gisMap.get("schoolId"));
	}

	/**
     * 搜索活动类型
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public List<?> queryActTypeList() throws Exception {
       return this.getBaseDao().getList("queryActtype");
    }
    
    /**
     * 搜索活动
     * 
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public Page queryActListPage(Map<String, Object> map) throws Exception {
        String currentPage = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        Object status = map.get("status");
        Object orderBy = map.get("orderBy");
        if(orderBy == null) {
        	map.put("orderBy", 0);
        }
        
        if(status == null) {
        	map.put("status",DEFAULT_STATUS);
        }
        Page page = this.getBaseDao().page(PREFIX + ".page", map,
                Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        return page;
    }

    /**
     * 活动详情
     * 
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map detailAct(Map<String, Object> map) throws Exception {
    	map.put("currtime", System.currentTimeMillis());
        Map act = this.getBaseDao().getObject(PREFIX + ".queryDetailAct", map);
        Long startTime = Long.parseLong((String) act.get("startTime"));
        Long endTime = Long.parseLong((String) act.get("endTime"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.M.d H:mm");
        String startTimeStr = sdf.format(startTime);
        String endTimeStr = sdf.format(endTime);
        String activityTime = startTimeStr + "~" + endTimeStr;
        act.put("activityTime", activityTime);
        
        // 加入最后一条评论
        pubLastComment(act,map);
        if(map.get("userId") != null && act.get("userId") != null && map.get("userId").toString().equals(act.get("userId").toString())){
        	clear(map);
        }
        return act;
    }
    
    // 加入最后一条评论
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void pubLastComment(Map act,Map params) {
    	try {
			Map reParams = this.getBaseDao().get(PREFIX + ".getLastComment", params);
			act.put("actcomment", reParams == null ? "0" : reParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}

	// 清除新消息提醒标示
    private void clear(Map<String, Object> map) {
		try {
			this.getBaseDao().update(PREFIX + ".clearNew", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.M.d h:mm");
    	  System.out.println(sdf.format(System.currentTimeMillis()));
	}
    
    /**
     * 评论活动
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public int commentAct(Map<String, Object> map) throws Exception {
        String userIdStr = (String) map.get("userId");
        String activityIdStr = (String) map.get("activityId");
        String contentStr = (String) map.get("content");
        contentStr = contentStr.replaceAll("[^\\u0000-\\uFFFF]", "");
        int offest = 0;

        if (StringUtils.isNotBlank(userIdStr) && StringUtils.isNotBlank(activityIdStr)
                && StringUtils.isNotBlank(contentStr)) {
            offest = this.getBaseDao().save(
                    PREFIX + ".insertComment",
                    new ActComment(Integer.parseInt(activityIdStr), Integer.parseInt(userIdStr),
                            contentStr));
        }
        else {
            throw new Exception("评论活动异常...");
        }
        return offest;
    }

    /**
     * 查询活动评论
     * 
     * @param actId
     * @return
     * @throws Exception
     */
    public List<?> queryCommentByActId(String actId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("activityId", actId);
        return this.getBaseDao().getList(PREFIX + ".queryComment", map);
    }

    /**
     * 收藏活动
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public int favoriteAct(Map<String, Object> map) throws Exception {
        String userIdStr = (String) map.get("userId");
        String activityIdStr = (String) map.get("activityId");

        int offset = 0;
        if (StringUtils.isNotBlank(userIdStr) && StringUtils.isNotBlank(activityIdStr)) {
            // 判断是否已收藏
            int favoriteCount = this.getBaseDao().getObject(PREFIX + ".isFavorite", map);
            if (favoriteCount == 0) {
                offset = this.getBaseDao().save(PREFIX + ".insertFavorite", map);
            }
        }
        return offset;
    }

    /**
     * 取消收藏活动
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public int unfavoriteAct(Map<String, Object> map) throws Exception {
        String userIdStr = (String) map.get("userId");
        String activityIdStr = (String) map.get("activityId");

        int offset = 0;
        if (StringUtils.isNotBlank(userIdStr) && StringUtils.isNotBlank(activityIdStr)) {
            // 判断是否已收藏
            offset = this.getBaseDao().delete(PREFIX + ".deleteFavorite", map);
        }
        return offset;
    }

    
    /**
     * 参与活动
     * 
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public int applyAct(Map<String, Object> map) throws Exception {
        String userIdStr = (String) map.get("userId");
        String activityIdStr = (String) map.get("activityId");

        int offset = 0;
        if (StringUtils.isNotBlank(userIdStr) && StringUtils.isNotBlank(activityIdStr)) {
            // 判断是否已加入
        	Map actIdMap = new HashMap();
            actIdMap.put("id", activityIdStr);
            Map rs = this.getBaseDao().get(PREFIX + ".findJoinCountAndActCount", actIdMap);
        	int joinedCount = Integer.parseInt(rs.get("joinCount").toString());
        	int maxCount = Integer.parseInt(rs.get("maxCount").toString());
            if(maxCount <= joinedCount) {
            	return -1;
            }
            int joinCount = this.getBaseDao().getObject(PREFIX + ".isJoin", map);
            if (joinCount == 0) {
                map.put("createTime", System.currentTimeMillis());
                offset = this.getBaseDao().save(PREFIX + ".insertApply", map);
                // 改变群是否有新消息状态
                this.getBaseDao().update(PREFIX + ".updateisnew", map);
                
                // 发送系统消息
                EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + rs.get("stuUserId"), "有人报名了活动" ,"{'attr':'sys'}");
            }
            else {
            	return -2;
            }
        }
        return offset;
    }

    /**
     * 评论列表
     * 
     * @param map
     * @return
     * @throws Exception
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<?> queryCommentList(Map<String, Object> map) throws Exception {
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        Page<?> page = this.getBaseDao().page(PREFIX + ".pageConmment", map,
                Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        List<?> list = (List<?>) page.getRows();
        for (int i = 0; i < list.size(); i++) {
        	long createTime =  Long.parseLong(((Map)((List)page.getRows()).get(i)).get("createTime").toString()) ;
            ((Map)((List)page.getRows()).get(i)).put("createTime", com.bluemobi.utils.StringUtils.friendly_time(createTime));
		}
        return page;
    }

    /**
     * 参与人员列表 列表
     * 
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public Page queryApplyList(Map<String, Object> map) throws Exception {
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        return this.getBaseDao().page(PREFIX + ".pageApply", map, Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
    }

    /**
     * 参与人员审核
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public int applyUser(Map<String, Object> map) throws Exception {
    	int offset = this.getBaseDao().update(PREFIX + ".updateStatus", map);
    	if(offset <= 0) return -1;
    	sendToUser(map.get("status").toString(), map.get("userId").toString(),map.get("activityId").toString());
        return offset;
    }
    
    /**
     * 通知用户参与的活动是否审核成功
     * @param object
     * @param object2
     */
	private void sendToUser(String flag, String id,String activitiesId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Map<String, Object> reParams;
		try {
			reParams = stuUserServiceImpl.findUserDetailById(params);
			params.clear();
			params.put("activityId", activitiesId);
			@SuppressWarnings("unchecked")
			Map<String,Object> actParams = detailAct(params);
			String ext = "{'attr':'sys','userId' :'" + id +"','avater':'" + reParams.get("head") + "','nickname':'" + reParams.get("nickname")+ "','actname':'" + actParams.get("content") +"','status':'" + flag +"'}";
			
			if("1".equals(flag)) {
				EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + id, "您参与的活动" + (actParams.get("title")!=null && org.apache.commons.lang3.StringUtils.isNotBlank(actParams.get("title").toString()) ? "【"+ actParams.get("title").toString() + "】" : "")  + "已审核通过",ext);
			}
			else if("2".equals(flag)){
				EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + id, "您参与的活动" + (actParams.get("title")!=null && org.apache.commons.lang3.StringUtils.isNotBlank(actParams.get("title").toString()) ? "【"+ actParams.get("title").toString() + "】" : "") + "已审核拒绝",ext);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ------------------------------- 后台功能代码
    // ------------------------------------//

    /**
     * 查询所有活动信息
     * 
     * @param params
     * @param current
     * @param pagesize
     * @return
     */
    public Page<List<Activities>> list(Map<String, Object> params, int current, int pagesize) {
        if (params.get("provinceId") == null || params.get("provinceId").toString().length() == 0
                || params.get("provinceId").toString().equals("")
                || params.get("provinceId").toString().equals("-1")) {// 查询所有省份，城市就不用判断
            params.put("provinceId", null);
            params.put("cityId", null);
        }
        else if (params.get("cityId") == null || params.get("cityId").toString().length() == 0
                || params.get("cityId").toString().equals("")
                || params.get("cityId").toString().equals("-1")) {// 省份确定，城市未知，查询该省份下的所有城市的学校
            params.put("cityId", null);
        }
        return this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
    }

    /**
     * 查询所有参加活动的用户信息
     * 
     * @param params
     * @param current
     * @param pagesize
     * @return
     */
    public Page<List<StuUser>> personlist(Map<String, Object> params, int current, int pagesize) {
        return this.getBaseDao().page(PREFIX + ".findJoinPerson", params, current, pagesize);
    }

    /**
     * 根据条件查询活动详细信息
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> findOneById(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".findOne", params);
    }

    /**
     * 更新单个活动状态
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public int update(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".update", params);
    }

    /**
     * 更新多个活动状态
     * 
     * @param params
     * @return
     */
    public String updates(Map<String, Object> params) {
        // 要处理的数据集，以字符串的形式存在，中间用逗号隔开
        String ids = params.get("ary").toString();
        // 获取到要处理的id的数组
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
                //2015-1-28 yesong
                toSend(flag,list[i]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return type + "";
    }

    //2015-1-28 yesong
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void toSend(String flag, String id) {
		int gId = Integer.parseInt(id);
		Map map = new HashMap();
		map.put("id", gId);
		Map actMap = null;
		try {
			actMap = this.getBaseDao().get(PREFIX + ".findOne", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(Integer.parseInt(flag) == SUCCESS){
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX +String.valueOf(actMap.get("stuUserId")), G_SUCCESS.replace("s", actMap.get("name").toString()),"{'attr':'sys'}");
		}
		else{
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN, EasemobUtil.PREFIX +String.valueOf(actMap.get("stuUserId")),G_FAIL.replace("s", actMap.get("name").toString()),"{'attr':'sys'}");
		}
	}
	
	/**
	 * 活动评论回复
	 * @param params
	 * @throws Exception
	 */
	public void reComment(Map<String,Object> params) throws Exception {
		params.put("reTime", System.currentTimeMillis());
		this.getBaseDao().update(PREFIX + ".reComment", params);
	}
	
	/**
	 * 活动点赞
	 * @param params
	 * @throws Exception
	 */
	public void praise(Map<String,Object> params) throws Exception {
		Map<String,Object> reParams = this.getBaseDao().get(PREFIX + ".getPraise", params);
		if(reParams == null || reParams.size() == 0) {
			this.getBaseDao().save(PREFIX + ".praise", params);
		}
	}
	
	/**
	 * 活动取消点赞
	 * @param params
	 * @throws Exception
	 */
	public void unPraise(Map<String,Object> params) throws Exception {
		this.getBaseDao().delete(PREFIX + ".unpraise", params);
	}
	
	/**
	 * 获取活动二维码
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getQrCode(Map<String,Object> params) throws Exception {
		return this.getBaseDao().get(PREFIX + ".getQrCode", params);
	}
	
	// =========================================================================
	// ========================三期接口===========================================
	// =========================================================================
	/**
     * 新增活动
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public Activities insertActivitiesV3(Map<String, Object> map) throws Exception {
        Activities activities = null;
        String userIdSting = (String) map.get("userId");
        if (StringUtils.isBlank(userIdSting)) {
            throw new Exception("用户ID不能为空");
        }
        int userId = Integer.parseInt(userIdSting);
        
        getSchoolId(map); //获取学校Id
        
        int schoolId = (Integer) map.get("schoolId");
        int provinceId = (Integer) map.get("provinceId");
        int cityId = (Integer) map.get("cityId");
        
        String picture = (String) map.get("picture");
        String content = (String) map.get("content");
        String location = (String) map.get("location");

        String typeIdStr = (String) map.get("typeId");
        int typeId = StringUtils.isNotBlank(typeIdStr) ? Integer.parseInt(typeIdStr) : 0;

        String startTimeStr = (String) map.get("startTime");
        long startTime = StringUtils.isNotBlank(startTimeStr) ? Long.parseLong(startTimeStr) : 0;
        String endTimeStr = (String) map.get("endTime");
        long endTime = StringUtils.isNotBlank(endTimeStr) ? Long.parseLong(endTimeStr) : 0;

        String maxCountStr = (String) map.get("nums");
        int maxCount = StringUtils.isNotBlank(maxCountStr) ? Integer.parseInt(maxCountStr) : 100;
        
        String pwidthStr =(String) map.get("pwidth");
        int pwidth = StringUtils.isNotBlank(pwidthStr)? Integer.parseInt(pwidthStr) : 0;
        String pheightStr = (String) map.get("pheight");
        int pheight = StringUtils.isNotBlank(pheightStr)?Integer.parseInt(pheightStr):0;
        
        String title = map.get("title") != null ? map.get("title").toString() : "";
        
        double lon = map.get("lon") == null ? 0 : Double.parseDouble(map.get("lon").toString());
        double lat = map.get("lat") == null ? 0 : Double.parseDouble(map.get("lat").toString());
        
        activities = new Activities(provinceId,cityId,schoolId,content, System.currentTimeMillis(), typeId, userId, startTime,
                endTime, picture, maxCount, location,pwidth,pheight,lon,lat,title);
        int offset = this.getBaseDao().save(PREFIX + ".insertEntity", activities);
        if (offset > 0) {
            activities.setStatus(0);
            QrCodeUtil.generateQrCode(Constant.QRCODE_ACT, "" + activities.getId(), "activities",false , 0);
            return activities;
        }
        return null;
    }
	
}
