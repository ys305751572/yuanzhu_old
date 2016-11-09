package com.bluemobi.pro.controller.webservice;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.controller.UsersController;
import com.bluemobi.pro.pojo.Activities;
import com.bluemobi.pro.pojo.Post;
import com.bluemobi.pro.service.impl.ActivitiesServiceImpl;
import com.bluemobi.pro.service.impl.PostServiceImpl;
import com.bluemobi.pro.service.impl.StuUserServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.DecodeUtils;
import com.bluemobi.utils.ImageUtils;
import com.bluemobi.utils.ParamUtils;
import com.bluemobi.utils.Result;

/**
 * 
 * <p>Title: ActivitiesController.java</p> 
 * <p>Description: 活动controller</p> 
 * @author yesong 
 * @date 2014-11-14 下午02:31:45
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@Controller
@RequestMapping(value = "/api")
public class ActivitiesController extends BaseController{

	@Autowired
	private ActivitiesServiceImpl activitiesService;
	
	@Autowired
	private StuUserServiceImpl userService;
	
	@Autowired
	private PostServiceImpl postService;
	
	/**
	 * 新增活动
	 * @param map
	 * @returnf
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/activity/add" ,method=RequestMethod.POST)
	@ResponseBody
	public Map insertActivities(@RequestParam Map<String, Object> map,@RequestParam("picture") MultipartFile file){
		Activities activities = null;
		try {
			String[] path = ImageUtils.saveImage(file, false);
			if(StringUtils.isNotBlank(path[0])){
				//更新头像地址 
				map.put("picture",path[0]);
			}
			
			//保存图片长，宽 yesong
			int[] _size = ImageUtils.getSize(path[0]);
			map.put("pwidth",String.valueOf(_size[0]));
			map.put("pheight",String.valueOf(_size[1]));
			
			_decode(map);//中文解码
			
			activities = activitiesService.insertActivities(map);
			activities.setPicture(StringUtils.isNotBlank(path[0])?path[0]:"");
			this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return getJsonString();
	}
	
	/**
	 * 中文解码
	 * @param map
	 * @throws UnsupportedEncodingException 
	 */
	private void _decode(Map<String, Object> map) throws UnsupportedEncodingException {
		String title = (String) map.get("title");
		String content = (String) map.get("content");
		String location = (String) map.get("location");
		
	    map.put("title", DecodeUtils.decode(title));
		map.put("content",DecodeUtils.decode(content));
		map.put("location",DecodeUtils.decode(location));
		
	}

	/**
	 * 获取
	 * @param string
	 * @return int[] 0:宽 1：高
	 * @throws IOException 
	 */
	public int[] getSize(String path) throws IOException { 
		int [] size = new int[2];
		if(StringUtils.isNotBlank(path)){
			int _index = com.bluemobi.utils.StringUtils.findIndexFromPos(path, "/", 4);
			String _newPath = path.substring(_index+1);
			String webpath = (UsersController.class.getResource("/").getPath().substring(0,
	                 UsersController.class.getResource("/").getPath().indexOf("WEB-INF")));
			File file = new File(webpath + _newPath);
			BufferedImage image = ImageIO.read(file);
			if(image != null){
				size[0] = image.getWidth();
				size[1] = image.getHeight();
			}
		}
		return size;
	}

	/**
	 * 活动类型列表
	 * @author yesong
	 * @param map
	 * @returnf
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/activity/acttype" ,method=RequestMethod.POST)
	@ResponseBody
	public Map queryActtype(@RequestParam Map<String, Object> map){
		List list  = null;
		try {
			list = activitiesService.queryActTypeList();
			this.doResp(list, "list", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return getJsonString();
	}
	
	/**
	 * 搜索/显示 活动列表
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/activity/list",method=RequestMethod.POST)
	@ResponseBody
	public Map queryActList(@RequestParam Map<String, Object> map) {
		Page page = null;
		try {
			page = activitiesService.queryActListPage(map);
			this.initPage(map, page);
			this.doResp(page.getRows(), "list", this.page, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 活动详情
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/activity/info" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> detailAct(@RequestParam Map<String, Object> map) {
		Map<?, ?> activities = null; 
		try {
			activities = activitiesService.detailAct(map);
			this.doResp(activities, "activity", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 
	 * 评论活动
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/activity/replay",method=RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> commentAct(@RequestParam Map<String, Object> map) {
		try {
			activitiesService.commentAct(map);
			this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	/**
	 * 收藏活动
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/activity/favorite" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> favoriteAct(@RequestParam Map<String, Object> map) {
		try {
			activitiesService.favoriteAct(map);
			this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 取消收藏活动
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/activity/unfavorite" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> unFavoriteAct(@RequestParam Map<String, Object> map) {
		try {
			activitiesService.unfavoriteAct(map);
			this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 参与活动
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/activity/apply" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> applyAct(@RequestParam Map<String, Object> map) {
		int flag = 0;
		try {
			flag = activitiesService.applyAct(map);
			if(flag == -1) {
				this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_17);
			}
			else if(flag == -2) {
				this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_18);
			}
			else{
				this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 评论列表
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/activity/replayList" ,method=RequestMethod.POST)
	@ResponseBody
	public Map queryCommentList(@RequestParam Map<String, Object> map) {
		try {
			Page page = activitiesService.queryCommentList(map);
			
			this.initPage(map, page);
			this.doResp(page.getRows(), "list", this.page, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 参与人员列表
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/activity/applyList" ,method=RequestMethod.POST)
	@ResponseBody
	public Map queryApplyList(@RequestParam Map<String, Object> map) {
		try {
			Page page = activitiesService.queryApplyList(map);
			this.initPage(map, page);
			this.doResp(page.getRows(), "list", this.page, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 参与人员审核
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/activity/applyUser" ,method=RequestMethod.POST)
	@ResponseBody
	public Map applyUser(@RequestParam Map<String, Object> map) {
		try {
			activitiesService.applyUser(map);
			this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 评论追加回复
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/activity/recomment")
	@ResponseBody
	public Map<String,Object> reComment(@RequestParam Map<String,Object> params) {
		try {
			activitiesService.reComment(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 活动点赞
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/activity/praise" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> praise(@RequestParam Map<String,Object> params) {
		try {
			activitiesService.praise(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 活动取消点赞
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/activity/unpraise" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> unPraise(@RequestParam Map<String,Object> params) {
		try {
			activitiesService.unPraise(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取活动二维码
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/activity/qrcode" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getQrcode(@RequestParam Map<String,Object> params) {
		try {
			Map<String,Object> reParams = activitiesService.getQrCode(params);
			this.doResp(reParams, "data", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	// ========================================================================
	// ============================三期接口======================================
	// ========================================================================
	/**
	 * 新增活动
	 * @param map
	 * @returnf
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/activity/addV3" ,method=RequestMethod.POST)
	@ResponseBody
	public Map insertActivitiesV3(@RequestParam Map<String, Object> map,@RequestParam("picture") MultipartFile file){
		Activities activities = null;
		try {
			String[] path = ImageUtils.saveImage(file, false);
			if(StringUtils.isNotBlank(path[0])){
				//更新头像地址 
				map.put("picture",path[0]);
			}
			
			//保存图片长，宽 yesong
			int[] _size = ImageUtils.getSize(path[0]);
			map.put("pwidth",String.valueOf(_size[0]));
			map.put("pheight",String.valueOf(_size[1]));
			
			_decode(map);//中文解码
			
			activities = activitiesService.insertActivities(map);
			activities.setPicture(StringUtils.isNotBlank(path[0])?path[0]:"");
			this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return getJsonString();
	}
	
	/**
	 * 打赏
	 * @param map
	 * userId : 当前用户ID
	 * postId : 帖子ID
	 * coin   : 打赏爱佑币数量
	 * @return
	 */
	@RequestMapping(value = "/activity/xiu/reward", method = RequestMethod.POST)
	@ResponseBody
	public Result reward(@RequestParam Map<String,Object> map) {
		
		if(ParamUtils.existEmpty(map, "userId","postId","coin")) {
			return Result.failure(Constant.ERROR_01);
		}
		String postId = map.get("postId").toString();
		try {
			Post post = postService.findByPostId(postId);
			Integer friendId = post.getUserId();
			map.put("friendId", friendId);
			String result = userService.sendCoinToFriend(map);
			
			if(result.equals(Constant.ERROR_12)) {
				return Result.failure(Constant.ERROR_12);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success();
	}
}

