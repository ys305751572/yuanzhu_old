package com.bluemobi.pro.controller.webservice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bluemobi.pro.pojo.Post;
import com.bluemobi.pro.pojo.PostBar;
import com.bluemobi.pro.service.impl.PostServiceImpl;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.DecodeUtils;
import com.bluemobi.utils.Result;

/**
 * 秀吧
 * @author yesong
 *
 */
@RequestMapping("/api/activity/xiu/")
@Controller
public class PostController {

	@Autowired
	private PostServiceImpl service;
	
	/**
	 * 秀吧列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Result listXiuPage(@RequestParam Map<String,Object> params) {
		
		try {
			Page page = service.listPostBarPage(params);
			return Result.success(page);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
	}
	
	/**
	 * 发表帖子
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "post/release", method = RequestMethod.POST)
	@ResponseBody
	public Result releasePost(@RequestParam Map<String,Object> params,MultipartHttpServletRequest request) {
		
		try {
			String linkUrl = params.get("linkUrl") == null ? "" : DecodeUtils.decode(params.get("linkUrl").toString());
			_decode(params);
			params.put("linkUrl", linkUrl);
			service.releasePost(params,request);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success();
	}
	
	
	/**
	 * 中文解码
	 * @param map
	 * @throws UnsupportedEncodingException 
	 */
	private void _decode(Map<String, Object> map) throws UnsupportedEncodingException {
		String title = (String) map.get("title");
		String content = (String) map.get("content");
		 
	    map.put("title", DecodeUtils.decode(title));
		map.put("content",DecodeUtils.decode(content));
		
	}
	
	/**
	 * 帖子列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "post/list", method = RequestMethod.POST)
	@ResponseBody
	public Result listPostPage(@RequestParam Map<String,Object> params) {
		
		Page page = null;
		try {
			page = service.listPostPage(params);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success(page);
	}
	
	/**
	 * 帖子详情
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "post/detail", method = RequestMethod.POST)
	@ResponseBody
	public Result postDetail(@RequestParam Map<String,Object> params) {
		
		Post post = null;
		try {
			post = service.postDetail(params);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success(post);
	}
	
	/**
	 * 评论列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "post/comment/list", method = RequestMethod.POST)
	@ResponseBody
	public Result listCommentPage(@RequestParam Map<String,Object> params) {
		
		Page page = null;
		try {
			page = service.listCommentPage(params);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success(page);
	}
	
	/**
	 * 评论帖子
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "post/comment", method = RequestMethod.POST)
	@ResponseBody
	public Result postComment(@RequestParam Map<String,Object> params) {
		try {
			service.comment(params);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success();
	}
	
	/**
	 * 帖子点赞
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "post/favorite", method = RequestMethod.POST)
	@ResponseBody
	public Result postPraise(@RequestParam Map<String,Object> params) {
		
		try {
			service.praise(params);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success();
	}
	
	/**
	 * 帖子取消点赞
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "post/unfavorite", method = RequestMethod.POST)
	@ResponseBody
	public Result postUnPraise(@RequestParam Map<String,Object> params) {
		
		try {
			service.unPraise(params);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success();
	}
	
	/**
	 * 查询贴吧首页banner图列表
	 * @return
	 */
	@RequestMapping(value = "banner", method = RequestMethod.POST)
	@ResponseBody
	public Result postBarBananer() {
		
		List<PostBar> list = null;
		try {
			list = service.bannar();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success(list);
	}
}
