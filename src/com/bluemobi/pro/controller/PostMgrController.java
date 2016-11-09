package com.bluemobi.pro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Post;
import com.bluemobi.pro.pojo.PostBar;
import com.bluemobi.pro.service.impl.PostServiceImpl;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.Result;

/**
 * @author TuYiHeng
 * 
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/xiu/")
public class PostMgrController {

	private static final String XIU_LIST = "main/xiu/xiulist";
	
	private static final String XIU_EDIT = "main/xiu/xiuedit";
	private static final String XIU_DETAIL = "main/xiu/xiudetail";
	
	private static final String POST_LIST = "main/xiu/postlist";
	private static final String POST_DETAIL = "main/xiu/postdetail";
	
	private static final String USER_LIST = "main/xiu/stuuserlist";
	
	@Autowired
	private PostServiceImpl service;
	
	/**
	 * 秀吧列表页面
	 * @return
	 */
	@RequestMapping(value = "listPage", method = RequestMethod.GET)
	public String listPage() {
		return XIU_LIST;
	}

	@RequestMapping(value = "userListPage", method = RequestMethod.GET)
	public String userListPage(@RequestParam("postBarId") String postBarId, Model model) {
		
		Map<String,Object> pbMap = new HashMap<String,Object>();
		pbMap.put("id", postBarId);
		model.addAttribute("postBar", pbMap);
		return USER_LIST;
	}
	
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addPage(Model model) {
		
		try {
			Integer maxIndex = service.findMaxIndex();
			model.addAttribute("maxIndex", ++maxIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return XIU_EDIT;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String editPage(@RequestParam("postBarId") String postBarId,Model model) {
		if(StringUtils.isNotBlank(postBarId)) {
			try {
				PostBar pb = service.findById(postBarId);
				model.addAttribute("pb", pb);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return XIU_EDIT;
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detailPage(@RequestParam("postBarId") String postBarId,Model model) {
		if(StringUtils.isNotBlank(postBarId)) {
			try {
				PostBar pb = service.findById(postBarId);
				model.addAttribute("pb", pb);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return XIU_DETAIL;
	}
	
	/**
	 * 更新秀吧
	 * @return
	 */
	@RequestMapping(value = "saveOrUpadte", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpadate(PostBar pb) {
		if(pb.getId() != null) {
			edit(pb);
		}
		else {
			save(pb);
		}
		return Result.success();
	}
	
	/**
	 * 更新秀吧上/下 架状态
	 * @param pb
	 * @return
	 */
	@RequestMapping(value = "exchange", method = RequestMethod.POST)
	@ResponseBody
	public Result exchange(PostBar pb) {
		try {
			service.editPostBar(pb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.success();
	}
	
	// 新增
	private void edit(PostBar pb) {
		try {
			service.editPostBar(pb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新
	private void save(PostBar pb) {
		try {
			service.createPostBar(pb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询所有秀吧数据
	 * 分页
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "findAllXiu", method = RequestMethod.POST)
	@ResponseBody
	public Page findAllXiu(@RequestParam Map<String,Object> params) {
		Page<PostBar> page = service.findAllXiu(params);
		return page;
	}
	
	/**
	 * 查询备选用户列表
	 * 分页
	 * 去掉已选择的用户
	 * @param postBarId
	 * @return
	 */
	@RequestMapping(value = "findAllUser", method = RequestMethod.POST)
	@ResponseBody
	public Page findAllUser(@RequestParam Map<String,Object> params ,@RequestParam("postBarId") String postBarId) {
		Page userPage = service.findUserListForAllot(params,postBarId);
		return userPage;
	}
	
	/**
	 * 保存选择有权限发帖用户
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ResponseBody
	public String checkUser(@RequestParam Map<String,Object> params) {
		int offset = 0;
		offset = service.checkUser(params);
		return "" + offset;
	}
	
	// ---------------------------------------------------------------------
	// ----------------------------帖子接口---------------------------------
	// ---------------------------------------------------------------------
	
	/**
	 * 帖子列表页面
	 * @return
	 */
	@RequestMapping(value = "post/listPage", method = RequestMethod.GET)
	public String postListPage() {
		return POST_LIST;
	}
	
	@RequestMapping(value = "post/findAllPost", method = RequestMethod.POST)
	@ResponseBody
	public Page findAllPost(@RequestParam Map<String,Object> params) {
		return service.findAllPost(params);
	}
	
	/**
	 * 帖子详情
	 * @param postId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "post/detail", method = RequestMethod.GET)
	public String detailPostPage(@RequestParam("postId") String postId,Model model) {
		Post post = null;
		try {
			post = service.findByPostId(postId);
			model.addAttribute("post", post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return POST_DETAIL;
	}
	
	/**
	 * 帖子上/下 架
	 * @param post
	 * @return
	 */
	@RequestMapping(value = "post/exchange", method = RequestMethod.POST)
	@ResponseBody
	public Result postExchange(Post post) {
		service.exchange(post);
		return Result.success();
	}
	
	@RequestMapping(value = "post/findAllXiu", method = RequestMethod.POST)
	@ResponseBody
	public List<PostBar> findAllXiuSelect() {
		List<PostBar> pbList = service.findAllPostBarSelect();
		return pbList;
	}
	
	/**
	 * 帖子上/下 架
	 * @param post
	 * @return
	 */
	@RequestMapping(value = "post/top", method = RequestMethod.POST)
	@ResponseBody
	public Result postTop(Post post) {
		service.exchange(post);
		return Result.success();
	}
}
