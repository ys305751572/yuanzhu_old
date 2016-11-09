package com.bluemobi.pro.controller.webservice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Task;
import com.bluemobi.pro.pojo.TaskType;
import com.bluemobi.pro.service.impl.FriendServiceImpl;
import com.bluemobi.pro.service.impl.TaskServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.DateUtils;
import com.bluemobi.utils.DecodeUtils;
import com.bluemobi.utils.JsonUtils;

@Controller
@RequestMapping(value= "/api/task")
@SuppressWarnings("unchecked")
public class TaskWBController extends BaseController{
	
	@Autowired
	private TaskServiceImpl taskService;
	
	@Autowired
	private FriendServiceImpl friendService;

	/**
	 * 任务首页
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/index",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> taskIndex(@RequestParam Map<String,Object> params) {
		Page page = null;
		try {
			page = taskService.getTaskIndex(params);
			this.initPage(params, page);
			this.doResp(page.getRows(), "list",this.page, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	} 
	
	/**
	 * 中文解码
	 * @param map
	 * @throws UnsupportedEncodingException 
	 */
	private void _decode(Map<String, Object> map) throws UnsupportedEncodingException {
		String task_title = (String) map.get("task_title");
		String task_address = (String) map.get("task_address");
		String task_desc = (String) map.get("task_desc");
		map.put("task_title",DecodeUtils.decode(task_title));
		map.put("task_address", DecodeUtils.decode(task_address));
		map.put("task_desc",DecodeUtils.decode(task_desc));
	}
	
	/**
	 * 发布任务
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/release",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> releaseTask(@RequestParam Map<String, Object> params,MultipartHttpServletRequest request) {
		try {
			_decode(params);
			int _offest = taskService.releaseTask(params, request);
			if (_offest > 0){
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
			}
			else{
				this.doError();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取任务列表
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/list" , method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getTaskList(@RequestParam Map<String, Object> params) {
		try {
			Page taskList = taskService.getTaskList(params);
			initPage(params, taskList);
			this.doResp(taskList.getRows(), "task_list", page, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取附近任务
	 * @author yes
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/near", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getNearTaskList(@RequestParam Map<String,Object> params) {
		try {
			List<?> nearTaskList = taskService.getNearTaskList(params);
			this.doResp(nearTaskList, "task_list", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	public static void main(String[] args) {
		Integer a = 1;
		Integer b = 1;
		
		System.out.println(a.equals(b) );
	}
	
	/**
	 * 获取任务详情
	 * @author yes
	 * @param paramss
	 * @return
	 */
	@RequestMapping(value="/detail" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getTaskDetail(@RequestParam Map<String,Object> params) {
		Map<String,Object> returnParams = null;
		Integer currentUserId = Integer.parseInt(params.get("userId").toString());
		Integer releaseUserId = null;
		Integer acceptUserId = null;
		try {
			Task taskDetail = taskService.getTaskDetail(params);
			if(taskDetail == null) return null;
			
			releaseUserId = taskDetail.getRelease_user_id();
			acceptUserId = taskDetail.getAccept_id();
			
			if((!currentUserId.equals(acceptUserId)) && (!currentUserId.equals(releaseUserId))) {
				taskDetail.setRelease_user_mobile("-1");
				taskDetail.setAccept_mobile("-1");
			}
			if(currentUserId.equals(acceptUserId)) {
				taskDetail.setAccept_id(0);
			}
			
			returnParams = JsonUtils.getMapFromPojo(taskDetail);
			this.doResp(returnParams, "task_detail", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 接受任务
	 * @author yes
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/accept" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> acceptTask(@RequestParam Map<String,Object> params) {
		try {
			int offest = taskService.acceptTask(params);
			if( offest > 0){
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
			}
			else if( offest == -1) {
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, Constant.ERROR_16);
			}
			else{
				this.doError();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	//=========================================================================================
	//=====================================3期版本接口============================================
	//=========================================================================================
	
	/**
	 * 任务列表
	 * 只显示待接单的任务
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "listV3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> taskList(@RequestParam Map<String,Object> params) {
		
		Integer currentPage = params.get("pageNum") == null ? 1 : Integer.parseInt(params.get("pageNum").toString());
		Integer pageSize = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
		
		Page page = null;
		try {
			page = taskService.taskListV3(params, currentPage, pageSize);
			this.initPage(params, page);
			this.doResp(page.getRows(),"list", this.page, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 查询用户是否只有一个发布任务
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "isOneReleaseTask", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> isOneReleaseTask(@RequestParam Map<String,Object> params) {
		try {
			String taskId = "";
			String task_status = "-1";
			Map map = taskService.isOneReleaseTask(params);
			String taskId1 = map.get("task_id") == null ? "" : map.get("task_id").toString();
			String task_status1 = map.get("task_status") == null ? "-1" : map.get("task_status").toString();
			Map map2 = taskService.isOneAcceptTask(params);
			
			String taskId2 = map2.get("task_id") == null ? "" : map2.get("task_id").toString();
			String task_status2 = map2.get("task_status") == null ? "-1" : map2.get("task_status").toString();
			if(StringUtils.isNotBlank(taskId1)) {
				taskId = taskId1;
				task_status = task_status1;
			}
			else if(StringUtils.isNotBlank(taskId2)) {
				taskId = taskId2;
				task_status = task_status2;
			}
			Map<String,Object> taskmap = new HashMap<String,Object>();
			taskmap.put("task_id", taskId);
			taskmap.put("task_status", task_status);
			this.doResp(taskmap, "oneTask", null, STATUS_SUCCESS, MSG_NULL);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 查询用户是否只有一个发布任务
	 * @param params
	 * @return
	@RequestMapping(value = "isOneAcceptTask", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> isOneAcceptTask(@RequestParam Map<String,Object> params) {
		try {
			this.doResp(taskService.isOneAcceptTask(params), "task_id", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	 */
	
	
	/**
	 * 发布任务V3
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "releaseV3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> releaseV3(@RequestParam Map<String,Object> params, MultipartHttpServletRequest request) {
		
		try {
			_decode(params);
			// 判断用户爱佑币是否足够
			if(taskService.isEnough(params)) {
				params.put("task_end_time", DateUtils.add(Calendar.DATE, new Date(), 5));
				taskService.releaseTask(params, request);
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
			}
			else {
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_FAIL, Constant.ERROR_12);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取任务详情
	 * @author yes
	 * @param paramss
	 * @return
	 */
	@RequestMapping(value="/detailV3" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getTaskDetailV3(@RequestParam Map<String,Object> params) {
		Map<String,Object> returnParams = null;
		try {
			Task taskDetail = taskService.getTaskDetail(params);
			
			int userId = Integer.parseInt(params.get("userId").toString());
			
			int releaseUserId = taskDetail.getRelease_user_id();
			int acceptUserId = taskDetail.getAccept_id();
			
			Map<String,Object> friendMap = new HashMap<String,Object>();
			friendMap.put("stuUserId", userId);
			if(userId == acceptUserId || acceptUserId == 0) {
				// 接单人查看
				friendMap.put("friendId", releaseUserId);
			}
			
			if (userId == releaseUserId) {
				// 发单人查看
				friendMap.put("friendId", String.valueOf(acceptUserId));
			}
			Map<String,Object> fMap = friendService.findOne(friendMap);
			if(fMap == null || fMap.get("stuUserId") == null || Integer.parseInt(fMap.get("stuUserId").toString()) == 0) {
				taskDetail.setIs_friend(0);
			}
			else {
				taskDetail.setIs_friend(1);
			}
			
			if(taskDetail == null) returnParams = new HashMap<String, Object>();
			returnParams = JsonUtils.getMapFromPojo(taskDetail);
			this.doResp(returnParams, "task_detail", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 接收任务
	 * 接收任务方法必须是同步方法，保证一个任务只能有一个人抢单
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "acceptV3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> accept(@RequestParam Map<String,Object> params) {
		try {
			params.put("status",Constant.TASK_ACCEPT);
			int flag = taskService.acceptTask(params);
			if(flag == -1) {
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_FAIL, Constant.ERROR_19);
			}
			else if(flag == -2) {
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_FAIL, Constant.ERROR_20);
			}
			else {
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 完成任务
	 * 1.平台线下支付奖励给接单人
	 * 2.发起任务的奖励爱佑币转给平台
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "finishV3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> finish(@RequestParam Map<String,Object> params) {
		try {
			params.put("status",Constant.TASK_WAIT_COMMET);
			taskService.finishTask(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 任务发起人:取消任务
	 * 任务接单人:取消接单
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "cancelV3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> cancel(@RequestParam Map<String,Object> params) {
		try {
			taskService.deductIntegral(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 评价任务
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "evaluationV3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> evaluation(@RequestParam Map<String,Object> params) {
		try {
			params.put("status",Constant.TASK_FINISH);
			taskService.evaluation(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
}
