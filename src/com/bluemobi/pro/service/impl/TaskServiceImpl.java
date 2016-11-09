package com.bluemobi.pro.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.pro.pojo.Task;
import com.bluemobi.pro.pojo.TaskExcel;
import com.bluemobi.pro.pojo.TaskType;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.CoinLogUtil;
import com.bluemobi.utils.DateUtils;
import com.bluemobi.utils.EasemobUtil;
import com.bluemobi.utils.ImageUtils;
import com.bluemobi.utils.MapUtil;
import com.bluemobi.utils.PropertiesUtils;
import com.bluemobi.utils.YuanzhuUtils;

/**
 * 任务业务类
 * @author 叶松
 *
 */
@Service
public class TaskServiceImpl extends BaseService{

	private static String PRIFIX = TaskServiceImpl.class.getName();
	private static String PRIFIX_STUUSER = StuUserServiceImpl.class.getName();
	
	@Autowired
	private GroupServiceImpl groupService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page getTeskPage(Map<String,Object> params) throws Exception {
		Integer rows = new Integer((String) params.get("rows"));
	    Integer pages = new Integer((String) params.get("page"));
	    Page page = this.getBaseDao().page(PRIFIX + ".page", params, pages, rows);
	    Collection col = page.getRows();
	    if(col != null && col.size() > 0) {
	    	for (Object object : col) {
				if(object != null) {
					Map<String,Object> map = (Map<String,Object>) object;
					int taskCoin = Integer.parseInt(map.get("task_coin").toString());
					map.put("money", YuanzhuUtils.generateRewardMoney(taskCoin));
				}
			}
	    }
	    return page;
	}
	
	/**
	 * 获取所有任务类型列表
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTaskTypList() throws Exception {
		List list = this.getBaseDao().getList(PRIFIX + ".getTaskTypList");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id","");
		map.put("type","所有状态");
		list.add(0, map);
		return list;
	}
	
	/**
	 * 修改任务审核状态
	 * @param params
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@Transactional
	public int exchangeTask(Map<String,Object> params) throws Exception {
		
		int status = Integer.parseInt(params.get("t_status").toString());
		Map<String,Object> rMap = this.getBaseDao().get(PRIFIX + ".findUserByTaskId", params);
		String userId = rMap.get("release_user_id").toString();
		String taskCoin = rMap.get("task_coin").toString();
		
		if(status == 1) {
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN, EasemobUtil.PREFIX + userId, "你的任务已通过审核", "{'attr':'sys'}");
		}
		else if(status == 2){
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN, EasemobUtil.PREFIX + userId, "你的任务已被拒绝", "{'attr':'sys'}");
			Map<String,Object> pMap = new HashMap<String,Object>();
			pMap.put("task_coin", taskCoin);
			pMap.put("userId", userId);
			pMap.put("type", "1");
			modifyUserCoin(pMap);
		}
		return this.getBaseDao().update(PRIFIX + ".exchangeTask", params);
	}
	
	public Task getTaskDetailServer(Map<String,Object> params) throws Exception {
		return (Task) this.getBaseDao().get(PRIFIX + ".getTaskDetailServer", params);
	}
	
	public int getStatus(Map<String,Object> params) {
		try {
			return this.getBaseDao().getObject(PRIFIX + ".getStatus", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 
	 * @param taskId
	 * @return
	 */
	public int rewordModifyTaskStatus(Map<String,Object> params){
		try {
			return this.getBaseDao().update(PRIFIX + ".rewordModifyTaskStatus", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/*********************************接口*********************************/
	
	/**
	 * 任务首页
	 * @param params
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@SuppressWarnings("rawtypes")
	public Page getTaskIndex(Map<String,Object> params) throws Exception {
		// 获取任务类别
//		List<?> typeList = getTypeList();
		//获取最近任务
		Page newTask = getNewTask(params);
		return newTask;
	}
	
	/**
	 * 获取最近任务
	 * @author yes
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Page getNewTask(Map<String,Object> params) throws Exception {
		int current = Integer.parseInt(params.get("pageNum").toString());
		int pagesize = Integer.parseInt(params.get("pageSize").toString());
		
		Page page = this.getBaseDao().page(PRIFIX + ".getTaskList", params, current, pagesize);
		Collection list = page.getRows();
		for (Object object : list) {
			Map<String,Object> task = (Map<String,Object>) object;
			mathDistance(params, task);
		}
		return page;
	}

	/**
	 * 获取任务列表集合
	 * @author yes
	 * @return
	 * @throws Exception
	 */
	public List<?> getTypeList(Map<String,Object> params) throws Exception {
		return this.getBaseDao().getList(PRIFIX +".getTypelist");
	}

	/**
	 * 判断爱佑币是否足够
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean isEnough(Map<String,Object> params) throws Exception {
		
		int money = Integer.parseInt(params.get("task_coin").toString());
		Map<String,Object> stuUserMap = this.getBaseDao().getObject(StuUser.class.getName() + ".queryStuUserById", params.get("release_user_id").toString());
		if(stuUserMap != null && stuUserMap.get("coin") != null) {
			int coin = Integer.parseInt(stuUserMap.get("coin").toString());
			if(money <= coin) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 确认是否只有一个发布任务
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map isOneReleaseTask(Map<String,Object> params) throws Exception {
		
		Map<String,Object> taskMap =  new HashMap<String,Object>();
		taskMap.put("userId", params.get("userId"));
		taskMap.put("user_type", "0");
		Page page = taskListV3(taskMap, 1, 10);
		if(page != null && page.getRows() != null && page.getRows().size() > 0) {
		   Map map = ((Map)((List)page.getRows()).get(0));
		   return map == null ? Collections.emptyMap() : map;
		}
		return Collections.emptyMap();
	}
	
	/**
	 * 确认是否只有一个接收任务
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map isOneAcceptTask(Map<String,Object> params) throws Exception {
		Map<String,Object> taskMap =  new HashMap<String,Object>();
		taskMap.put("accept_id", params.get("userId"));
		taskMap.put("user_type", "0");
		Page page = taskListV3(taskMap, 1, 10);
		if(page != null && page.getRows() != null && page.getRows().size() > 0) {
		   Map map = ((Map)((List)page.getRows()).get(0));
		   return map == null ?  Collections.emptyMap(): map;
		}
		return Collections.emptyMap();
	}
	
	/**
	 * 发布任务
	 * @author yes
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int releaseTask(Map<String,Object> params,MultipartHttpServletRequest request) throws Exception{
		params.put("id",0); 
		params.put("task_createTime", DateUtils.currentYourDate("yyyy-MM-dd HH:mm:ss"));
		this.getBaseDao().save(PRIFIX + ".insert", params);
		params.put("userId", params.get("release_user_id"));
		deductUserCoin(params);
		toSavePic(Integer.parseInt(params.get("id").toString()),request);
		
		// 保存爱佑币操作记录
		 CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(params.get("release_user_id").toString()), Integer.parseInt(params.get("task_coin").toString()), ""
	        		,"您于"+ DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") +"您发布任务成功，扣除" + Integer.parseInt(params.get("task_coin").toString()) +"爱佑币"));
		return Integer.parseInt(params.get("id").toString());
	}
	
	/**
	 * 扣除发起人爱佑币
	 * @param params
	 * @throws Exception 
	 */
	private void deductUserCoin(Map<String,Object> params) throws Exception {
		params.put("type", 0); // 设置操作类型为扣除
		this.modifyUserCoin(params);
	}

	
	/**
	 * 更新用户爱佑币数量
	 * 0:减少
	 * 1:增加
	 * @param params
	 */
	private void modifyUserCoin(Map<String, Object> params) throws Exception {
		this.getBaseDao().update("com.bluemobi.pro.pojo.StuUser.modifyUserCoin", params);
		 CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(params.get("userId").toString()), Integer.parseInt(params.get("task_coin").toString()), ""
	        		,"您于"+ DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") +"您已完成任务" + Integer.parseInt(params.get("task_coin").toString()) +"爱佑币"));
	}

	/**
	 * 保存任务图片集合
	 * @author yes
	 * @param taskId
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void toSavePic(int taskId, MultipartHttpServletRequest request) throws Exception {
		Iterator<?> iterator = request.getFileNames();
		Map<String, Object> picParams = null;
		List paramList = new ArrayList();
		String picName = null;
		MultipartFile file = null;
		while(iterator.hasNext()) {
			picName = iterator.next().toString();
			file = request.getFile(picName);
			if( file != null) {
				picParams = new HashMap<String, Object>();
				String[] path = ImageUtils.saveImage(file, true); // 保存图片且获取图片路径
				int[] widthAndHeight = ImageUtils.getSize(path[0]);
				picParams.put("width", widthAndHeight[0]);
				picParams.put("height", widthAndHeight[1]);
				picParams.put("path", path[0]);
				picParams.put("taskId", taskId);
				
				picParams.put("small_image_url", path[1]);
				
				paramList.add(picParams);
			}
		}
		if(paramList != null && paramList.size() > 0) {
			this.getBaseDao().save(PRIFIX + ".insertTaskPic", paramList);
		}
	}
	
	/**
	 * 获取任务列表
	 * @author yes
	 * @param params 根据类别
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page getTaskList(Map<String,Object> params) throws Exception {
		int current = Integer.parseInt(params.get("pageNum").toString()) ;
		int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
		
		Page page = this.getBaseDao().page(PRIFIX + ".getTaskList", params, current, pageSize);
		Collection list = page.getRows();
		for (Object object : list) {
			Map<String,Object> task = (Map<String,Object>) object;
			mathDistance(params, task);
		}
		return page;
	}
	
	public void mathDistance(Map<String,Object> params,Map<String,Object> task) {
		Object task_lon = task.get("task_lon");
		Object task_lat = task.get("task_lat");
		if(task_lon == null || task_lat == null) return;
		
		double lon2 = Double.parseDouble(task_lon.toString());
		double lat2 = Double.parseDouble(task_lat.toString());
		
		double lat1 = Double.parseDouble(params.get("task_lat") == null || params.get("task_lat").toString().equals("") ? "0" : params.get("task_lat").toString());
		double lon1 = Double.parseDouble(params.get("task_lon") == null || params.get("task_lon").toString().equals("") ? "0" : params.get("task_lon").toString());
		
		if(lat1 == 0 && lon1 == 0) {
			task.put("distance", "-1");
		}
		
		double distance = MapUtil.getDistance(lon1, lat1, lon2, lat2);
		task.put("distance", distance < 1000 ?  "距离" + (int) distance + "米" : "距离" + new DecimalFormat("############.0").format(distance/1000.0) + "公里");
	}
	
	/**
	 * 获取任务详情
	 * @param params
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public Task getTaskDetail(Map<String,Object> params) throws Exception{
		Task taskDetail = this.getBaseDao().get(PRIFIX + ".getTaskDetailById", params);
		
		Integer taskCoin = taskDetail.getTask_coin() == null ? 0 : taskDetail.getTask_coin().intValue();
		int rewardMoney = YuanzhuUtils.generateRewardMoney(taskCoin);
		taskDetail.setTask_reward(rewardMoney);
		double task_lon = taskDetail.getTask_lon() == null ? 0 : taskDetail.getTask_lon();
		double task_lat = taskDetail.getTask_lat() == null ? 0 : taskDetail.getTask_lat();
		
		double lat1 = Double.parseDouble(params.get("task_lat").toString().equals("") ? "0" : params.get("task_lat").toString());
		double lon1 = Double.parseDouble(params.get("task_lon").toString().equals("") ? "0" : params.get("task_lon").toString());
		
		double distance = MapUtil.getDistance(lon1, lat1, task_lon, task_lat);
		taskDetail.setDistance(distance < 1000 ? "距离" + (int) distance + "米" : "距离" + new DecimalFormat("############.0").format(distance/1000.0) + "公里");
		
		int taskType = taskDetail.getTask_type_id();
		TaskType taskType2 =  findTaskType(taskType);
		
		taskDetail.setGroupId(taskType2.getGroupId());
		
		if(StringUtils.isNotBlank(taskDetail.getGroupId()) && !"0".equals(taskDetail.getGroupId())) {
			params.put("groupId", taskDetail.getGroupId());
			taskDetail.setIsJoin(groupService.isInThisGroup(params) ? 1 : 0);
    	}
		
		return taskDetail;
	}
	
	/**
	 * 获取附近任务列表
	 * @author yes
	 * @param params 默认搜索附近1公里范围
	 * @return
	 * @throws Exception
	 */
	public List<?> getNearTaskList(Map<String,Object> params) throws Exception {
		return this.getBaseDao().getList(PRIFIX + ".getNearTaskLit", params);
	}
	
	/**
	 * 接受任务
	 * @param params
	 * @throws Exception 
	 */
	@Transactional
	public synchronized int acceptTask(Map<String,Object> params) throws Exception {
		
		Map<String,Object> taskMap = this.getBaseDao().get(PRIFIX + ".getAcceptId", params);
		int acceptId = Integer.parseInt(taskMap.get("accept_id").toString());
		int status = Integer.parseInt(taskMap.get("status").toString());
		
		if(acceptId != 0) return -1;
		if(status == 4 || status == 5) return -2;
		int userId = Integer.parseInt(taskMap.get("release_user_id").toString());
		// 发送通知消息
		EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + userId, "您的任务已被抢单","{'attr':'sys'}");
		EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + params.get("userId"), "您已接收该任务","{'attr':'sys'}");
		this.modifyTaskStatus(params);
		return this.getBaseDao().update(PRIFIX + ".acceptTask", params);
	}
	
	/**
	 * 完成任务
	 * @param params
	 * @throws Exception
	 */
	@Transactional
	public void finishTask(Map<String,Object> params) throws Exception {
		//TODO  发起任务的奖励爱佑币转给平台
		int userId = 0;
		Map<String,Object> taskMap = this.getBaseDao().getObject(PRIFIX + ".getTaskCoinById", params);
		int coin = ( taskMap == null ? 0 : Integer.parseInt(taskMap.get("task_coin").toString()));
		params.put("task_coin", taskMap.get("task_coin"));
		params.put("userId", taskMap.get("release_user_id"));
		
		Map<String,Object> platformMap = new HashMap<String,Object>();
//		platformMap.put("userId", userId);
		platformMap.put("userId", taskMap.get("accept_id"));
		platformMap.put("coin",coin);
		this.modifyTaskStatus(params);
		this.getBaseDao().update(PRIFIX + ".coinToPlatForm", platformMap);
		
		this.modifyUserIntegral(taskMap);
		
		// 新增记录
		this.insertAliPayTaskRecord(params);
		this.insertIntegralTaskRecords(taskMap);
		
		// 发送系统消息
		EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + taskMap.get("release_user_id"), "您的任务已完成","{'attr':'sys'}");
		EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + taskMap.get("accept_id"), "您的任务已完成","{'attr':'sys'}");
		
		// 保存爱佑币操作记录
	    CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(taskMap.get("accept_id").toString()), Integer.parseInt(taskMap.get("task_coin").toString()), ""
        		,"您于"+ DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") +"您已完成任务" + Integer.parseInt(taskMap.get("task_coin").toString()) +"爱佑币"));
		
	}
	
	@Transactional
	private void modifyUserIntegral(Map<String,Object> taskMap) throws Exception {
		Map<String,Object> releaseMap = new HashMap<String,Object>();
		Map<String,Object> acceptMap = new HashMap<String,Object>();
		releaseMap.put("userId", taskMap.get("release_user_id"));
		releaseMap.put("integral",PropertiesUtils.getPropertiesValues("INTEGRAL_RELEASE", Constant.COIN_PROP));
		acceptMap.put("userId", taskMap.get("accept_id"));
		acceptMap.put("integral",PropertiesUtils.getPropertiesValues("INTEGRAL_ACCEPT", Constant.COIN_PROP));
		this.getBaseDao().save(PRIFIX + ".modifyUserIntegral", releaseMap);
		this.getBaseDao().save(PRIFIX + ".modifyUserIntegral", acceptMap);
	}

	/**
	 * 扣除积分
	 * @param params
	 * @throws Exception
	 */
	@Transactional
	public void deductIntegral(Map<String,Object> params) throws Exception {
		
		Map<String,Object> taskMap = this.getBaseDao().getObject(PRIFIX + ".getTaskCoinById", params);
		int type = Integer.parseInt(params.get("type").toString());
		int integral = 0;
		if(type == 0) {
			// 发起人取消
			params.put("status",Constant.TASK_FAILURE);
			integral = Integer.parseInt(PropertiesUtils.getPropertiesValues("INTEGRAL_RELEASE_DEDUCT", Constant.COIN_PROP));
		}
		else if(type == 1){
			// 接单人取消
			params.put("status","" + Constant.TASK_NO_ACCEPT);
			params.put("acceptId", "0");
			integral = Integer.parseInt(PropertiesUtils.getPropertiesValues("INTEGRAL_ACCEPT_DEDUCT", Constant.COIN_PROP)) ;
		}
		
		Map<String,Object> _integralMap = new HashMap<String,Object>();
		_integralMap.put("userId", params.get("userId"));
		_integralMap.put("integral", integral);
		this.getBaseDao().update(PRIFIX + ".deductIntegral", _integralMap);
		// 将积分返还给用户
		returnCoinToUser(params);
		modifyTaskStatus(params);
		
		params.put("integral", integral);
		// 新增任务积分变化记录
		params.put("content", "撤销订单");
		insertIntegralTaskRecord(params);
		
		// 发送系统消息
		if (type == 0) {
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + taskMap.get("accept_id"), "您的任务已被取消","{'attr':'sys'}");
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + taskMap.get("release_user_id"), "您已取消该任务","{'attr':'sys'}");
		}
		else if (type == 1) {
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + taskMap.get("accept_id"), "您已取消该任务","{'attr':'sys'}");
			EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + taskMap.get("release_user_id"), "您的任务已被取消","{'attr':'sys'}");
		}
		
		// 保存爱佑币操作记录
	    CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(taskMap.get("release_user_id").toString()), Integer.parseInt(taskMap.get("task_coin").toString()), ""
        		,"您于"+ DateUtils.parse(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") +"您已取消任务，返还" + Integer.parseInt(taskMap.get("task_coin").toString()) +"爱佑币"));
	}
	
	/**
	 * 将爱佑币返还给用户
	 * @param params
	 * @throws Exception 
	 */
	private void returnCoinToUser(Map<String,Object> params) throws Exception {
		
		Task task = this.getBaseDao().getObject(PRIFIX + ".getTaskDetailById", params);
		params.put("task_coin", task.getTask_coin()); 
		params.put("type", "1");
		params.put(PRIFIX_STUUSER + ".modifyUserCoin", params);
		this.modifyUserCoin(params);
	}

	/**
	 * 评价
	 * @param params
	 * @throws Exception
	 */
	@Transactional
	public void evaluation(Map<String,Object> params) throws Exception {
		
		Map<String,Object> taskMap = this.getBaseDao().getObject(PRIFIX + ".getTaskCoinById", params);
		
		this.getBaseDao().update(PRIFIX + ".evaluation", params);
		this.modifyTaskStatus(params);
		
		// 发送系统消息
		EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + taskMap.get("accept_id"), "您的任务已被评价","{'attr':'sys'}");
		EasemobUtil.sendMsg2Friend(EasemobUtil.ADMIN,EasemobUtil.PREFIX + taskMap.get("release_user_id"), "您已评价该任务","{'attr':'sys'}");
	}
	
	/**
	 * 修改任务状态
	 * @param taskId
	 * @param status
	 * @throws Exception
	 */
	public void modifyTaskStatus(Map<String,Object> taskStatusMap) throws Exception {
		this.getBaseDao().update(PRIFIX + ".modifyTaskStatus", taskStatusMap);
	}
	
	/**
	 * 任务列表
	 * @param taskStatusMap
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page taskListV3(Map<String,Object> taskStatusMap,Integer currentPage, Integer pageSize) throws Exception {
		Page page = this.getBaseDao().page(PRIFIX +".typelistV3",taskStatusMap,currentPage,pageSize);
		Collection list = page.getRows();
		for (Object object : list) {
			Map<String,Object> task = (Map<String,Object>) object;
			mathDistance(taskStatusMap, task);
		}
		return page;
	}
	
	/**
	 * 新增任务记录--支付宝
	 * @param params
	 * @throws Exception
	 */
	public void insertAliPayTaskRecord(Map<String,Object> params) throws Exception {
		
		int taskCoin = params.get("task_coin") == null ? 0 : Integer.parseInt(params.get("task_coin").toString());
		
		Map<String,Object> recordMap = new HashMap<String,Object>();
		recordMap.put("taskId", params.get("task_id"));
		recordMap.put("userId", params.get("userId"));
		recordMap.put("type", 0);
		recordMap.put("status", 0);
		recordMap.put("content", "任务完成");
		recordMap.put("result", taskCoin/100);
		recordMap.put("createTime", DateUtils.currentStringDate());
		this.getBaseDao().save(PRIFIX + ".insertTaskRecord", recordMap);
	}
	
	/**
	 * 新增任务记录--积分
	 * @param params
	 * @throws Exception
	 */
	public void insertIntegralTaskRecord(Map<String,Object> params) throws Exception {
		
		Map<String,Object> recordMap = new HashMap<String,Object>();
		recordMap.put("taskId", params.get("task_id"));
		recordMap.put("userId", params.get("userId"));
		recordMap.put("type", 1);
		recordMap.put("status", 0);
		recordMap.put("result", params.get("integral"));
		recordMap.put("content", params.get("content"));
		recordMap.put("createTime", DateUtils.currentStringDate());
		this.getBaseDao().save(PRIFIX + ".insertTaskRecord", recordMap);
	}
	
	/**
	 * 批量新增任务记录
	 * @param params
	 * @throws Exception
	 */
	private void insertIntegralTaskRecords(Map<String, Object> params) throws Exception {
		List list = new ArrayList();
		Map<String,Object> releaseRecordMap = new HashMap<String,Object>();
		Map<String,Object> acceptRecordMap = new HashMap<String,Object>();
		releaseRecordMap.put("userId", params.get("release_user_id"));
		releaseRecordMap.put("taskId", params.get("task_id"));
		releaseRecordMap.put("userId", params.get("release_user_id"));
		releaseRecordMap.put("type", 1);
		releaseRecordMap.put("status", 0);
		releaseRecordMap.put("content", "确认订单完成");
		
		releaseRecordMap.put("result", PropertiesUtils.getPropertiesValues("INTEGRAL_RELEASE", Constant.COIN_PROP));
		releaseRecordMap.put("createTime", DateUtils.currentStringDate());
		
		acceptRecordMap.put("userId", params.get("accept_id"));
		acceptRecordMap.put("taskId", params.get("task_id"));
		acceptRecordMap.put("type", 1);
		acceptRecordMap.put("status", 0);
		acceptRecordMap.put("result", PropertiesUtils.getPropertiesValues("INTEGRAL_ACCEPT", Constant.COIN_PROP));
		acceptRecordMap.put("content", "确认订单完成");
		acceptRecordMap.put("createTime", DateUtils.currentStringDate());
	
		list.add(releaseRecordMap);
		list.add(acceptRecordMap);
		
		this.getBaseDao().save(PRIFIX + ".insertTaskRecords", list);
	}
	
	/**
	 * 查询任务导出数据
	 * @return
	 */
	public List<TaskExcel> findTaskExcel() {
		try {
			return this.getBaseDao().getList(PRIFIX + ".findTaskForExcel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询所有任务类型
	 * @return
	 * @throws Exception
	 */
	public List<TaskType> findAllTaskType() throws Exception {
		return this.getBaseDao().getList(PRIFIX + ".findAllTaskType");
	}
	
	public TaskType findTaskType(int taskType) {
		try {
			return this.getBaseDao().getObject(PRIFIX + ".findTaskTypeDetail", taskType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更改任务类型绑定群ID
	 * @param taskType
	 * @throws Exception 
	 */
	public void updateTaskTypeGroupId(TaskType taskType) throws Exception {
		this.getBaseDao().save(PRIFIX + ".updateTaskTypeGroupId", taskType);
	}
}
