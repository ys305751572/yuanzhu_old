package com.bluemobi.pro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Task;
import com.bluemobi.pro.pojo.TaskType;
import com.bluemobi.pro.service.impl.GroupServiceImpl;
import com.bluemobi.pro.service.impl.TaskServiceImpl;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.Result;

@Controller
public class TaskController {

	@Autowired
	private TaskServiceImpl service;
	
	@Autowired
	private GroupServiceImpl groupService;

	/**
	 * 跳转任务列表页面
	 * 
	 * @param map
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping("/totasklist")
	public String toList(@RequestParam Map<String, Object> map, Model model) {
		model.addAttribute("pages", map.get("pages"));
		return "main/task/tasklist";
	}

	/**
	 * 获取任务列表
	 * 
	 * @param params
	 * @return
	 * @Description:
	 */
	@RequestMapping("/getTaskPage")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public Page getPage(@RequestParam Map<String, Object> params) {
		Page page = null;
		try {
			page = service.getTeskPage(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/taskTypeList")
	@ResponseBody
	public List getTaskTypeList() {
		List list = null;
		try {
			list = service.getTaskTypList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 修改任务审核状态
	 * 
	 * @param params
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/exchangeTask", method = RequestMethod.POST)
	@ResponseBody
	public String exchangeTask(@RequestParam Map<String, Object> params) {
		int flag = -1;
		try {
			flag = service.exchangeTask(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "" + flag;
	}

	@RequestMapping(value = "/reword", method = RequestMethod.POST)
	@ResponseBody
	public Result reword(@RequestParam Map<String, Object> params) {
		int status = service.getStatus(params);
		if (status != 3)
			return Result.failure("该任务尚未完成");

		service.rewordModifyTaskStatus(params);
		return Result.success();
	}

	/**
	 * 
	 * @param params
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/toTaskShow")
	public String getTaskDetail(@RequestParam Map<String, Object> params, Model model) {
		Task task = null;
		try {
			task = service.getTaskDetailServer(params);
			model.addAttribute("task", task);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main/task/taskshow";
	}

	@RequestMapping("/grouplist1")
	public String grouplist(@RequestParam Map<String, Object> map, Model model) {
		model.addAttribute("pages", map.get("pages"));
		model.addAttribute("type", map.get("type"));
		return "main/task/grouplist";
	}
	
	/**
	 * 绑定群页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/toBindGroupPage")
	public String toBindGroupPage(@RequestParam Map<String, Object> map,Model model) {
		try {
			List<TaskType> list = service.findAllTaskType();
			for (TaskType taskType : list) {
				
				if(taskType.getGroupId() != null && !taskType.getGroupId().equals("0")) {
					String grouoId = taskType.getGroupId();
					Map<String,Object> map2 = new HashMap<String,Object>();
					map2.put("id", grouoId);
					Map<String,Object> group = groupService.findOneById(map2);
					taskType.setGroupName(group.get("name").toString());
				}
				else {
					taskType.setGroupName("");
				}
			}
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main/task/bindGroup";
	}
	
	/**
	 * 绑定群
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/bindGroup")
	@ResponseBody
	public Result bindGroup(Integer groupType,String groupId) {
		
		String[] groupIds = groupId.split(",");
		if(groupIds.length > 0) {
			groupId = groupIds[0];
		}
		else {
			return Result.failure();
		}
		
		TaskType taskType = new TaskType();
		taskType.setId(groupType);
		taskType.setGroupId(groupId);
		
		try {
			service.updateTaskTypeGroupId(taskType);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success();
	}
}
