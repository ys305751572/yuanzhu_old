package com.bluemobi.pro.service.impl;

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

import com.bluemobi.pro.pojo.Post;
import com.bluemobi.pro.pojo.PostBar;
import com.bluemobi.pro.pojo.PostComment;
import com.bluemobi.sys.bean.DataCache;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.ImageUtils;

/**
 * @author TuYiHeng
 * 
 */
@Service
@SuppressWarnings("rawtypes")
public class PostServiceImpl extends BaseService {

    public static final String POST_BAR_PRIFIX = PostBar.class.getName();
    
    public static final String POST_PRIFIX = Post.class.getName();
    
    public static final String POST_COMMENT_PRIFIX  = PostComment.class.getName();
    
    @Autowired
    private GroupServiceImpl groupService;
    
    /**
     * 查询最大排序数
     * @return
     * @throws Exception
     */
    public Integer findMaxIndex() throws Exception {
    	return this.getBaseDao().get(POST_BAR_PRIFIX + ".findMaxIndex",null);
    }
    
    /**
     * 秀吧首页
     * @return
     * @throws Exception
     */
	public Page listPostBarPage(Map<String,Object> params) throws Exception {
    	Integer pageNum = params.get("pageNum") == null ? 1 : Integer.parseInt(params.get("pageNum").toString());
    	Integer pageSize = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
    	return this.getBaseDao().page(POST_BAR_PRIFIX + ".listPage", params, pageNum, pageSize);
    }
	
	@Transactional
	public void releasePost(Map<String,Object> params,MultipartHttpServletRequest request) throws Exception {
		
		params.put("id", "0");
		if(params.get("groupId") == null ) {
			params.put("groupId", 0);
		}
		if(params.get("linkUrl") == null ) {
			params.put("linkUrl", "");
		}
		this.getBaseDao().save(POST_PRIFIX + ".insertPost", params);
		toSavePic(Integer.parseInt(params.get("id").toString()), request);
	}
    
	@SuppressWarnings("unchecked")
	public void toSavePic(int postId, MultipartHttpServletRequest request) throws Exception {
		
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
				picParams.put("postId", postId);
				picParams.put("small_image_url", path[1]);
				paramList.add(picParams);
			}
		}
		if(paramList != null && paramList.size() > 0) {
			this.getBaseDao().save(POST_PRIFIX + ".insertPostPic", paramList);
		}
	}
	
    /**
     * 根据秀把查询帖子列表
     * @param params
     * @return
     * @throws Exception
     */
    public Page listPostPage(Map<String,Object> params) throws Exception {
    	Integer pageNum = params.get("pageNum") == null ? 1 : Integer.parseInt(params.get("pageNum").toString());
    	Integer pageSize = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
    	
    	Integer type =  (params.get("type") == null ? 0 : Integer.parseInt(params.get("type").toString()));
    	params.put("type", type);
    	return this.getBaseDao().page(POST_PRIFIX + ".listPage", params, pageNum, pageSize);
    }
    
    /**
     * 根据ID查询帖子详情
     * @param params
     * @return
     * @throws Exception
     */
    public Post postDetail(Map<String,Object> params) throws Exception {
    	Post post = this.getBaseDao().get(POST_PRIFIX + ".postDetail", params);
    	if(StringUtils.isNotBlank(post.getGroupId()) && !"0".equals(post.getGroupId())) {
    		params.put("groupId", post.getGroupId());
    		post.setIsJoin(groupService.isInThisGroup(params) ? 1 : 0);
    	}
    	return post;
    }
    
    /**
     * 根据帖子ID查询
     * @param params
     * @return
     * @throws Exception
     */
    public Page listCommentPage(Map<String,Object> params) throws Exception {
    	Integer pageNum = params.get("pageNum") == null ? 1 : Integer.parseInt(params.get("pageNum").toString());
    	Integer pageSize = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
    	return this.getBaseDao().page(POST_COMMENT_PRIFIX + ".listPage", params, pageNum, pageSize);
    }
    
    /**
     * 评论帖子
     * @param params
     * @throws Exception
     */
    public void comment(Map<String,Object> params) throws Exception {
    	String content = params.get("content").toString();
    	content = content.replaceAll("[^\\u0000-\\uFFFF]", "");
    	params.put("content", content);
    	this.getBaseDao().save(POST_COMMENT_PRIFIX + ".postComment", params);
    }
    
    /**
     * 点赞帖子
     * @param params
     * @throws Exception
     */
    public void praise(Map<String,Object> params) throws Exception {
    	this.getBaseDao().save(POST_PRIFIX + ".insertPraise", params);
    }
    
    /**
     * 取消点赞帖子
     * @param params
     * @throws Exception
     */
    public void unPraise(Map<String,Object> params) throws Exception {
    	this.getBaseDao().delete(POST_PRIFIX + ".delPraise", params);
    }
    
    /**
     * 首页bannar图
     * @return
     * @throws Exception
     */
    public List<PostBar> bannar() throws Exception {
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("type", "0");
    	paramMap.put("userId", "0");
    	return this.getBaseDao().getList(POST_PRIFIX + ".listBanner", paramMap);
    }
    
    
    // ====================================================================================
    // ======================================后台管理业务======================================
    // ====================================================================================
    
    public Page<PostBar> findAllXiu(Map<String,Object> params ) {
	    Integer pageSize = new Integer((String) params.get("rows"));
        Integer currnetPage = new Integer((String) params.get("page"));
    	return this.getBaseDao().page(POST_BAR_PRIFIX + ".listPageMgr", params, currnetPage, pageSize);
    }
    
    /**
     * 
     * @param userId
     * @return
     */
    public Page findUserListForAllot(Map<String,Object> params,String postBarId) {
    	
    	params.put("postBarId", postBarId);
    	Integer pageSize = new Integer((String) params.get("rows"));
        Integer currnetPage = new Integer((String) params.get("page"));
        Page page =  this.getBaseDao().page(POST_BAR_PRIFIX + ".findUserListForAllot", params, currnetPage, pageSize);
        toAddSchool(page);
    	return page;
    }
    
    @SuppressWarnings({ "unchecked" })
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
    
    @SuppressWarnings("unchecked")
	public int checkUser(Map<String,Object> params ) {
    	List list = new ArrayList();
    	String idsStr = params.get("ary").toString();
    	String postBarId = params.get("postBarId").toString();
    	if(idsStr != null) {
    		String[] ids = idsStr.split(",");
    		for (String id : ids) {
				Map<String,Object> pMap = new HashMap<String,Object>();
				pMap.put("userId",id);
				pMap.put("postBarId", postBarId);
				list.add(pMap);
			}
    	}
    	try {
			return this.getBaseDao().save(POST_BAR_PRIFIX + ".insertPermissions", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return 0;
    }
    
    /**
     * 创建秀吧
     * @param pb
     * @return
     * @throws Exception
     */
    public int createPostBar(PostBar pb) throws Exception {
    	return this.getBaseDao().save(POST_BAR_PRIFIX + ".insert", pb);
    }
    
    /**
     * 更新秀吧
     * @param pb
     * @return
     * @throws Exception
     */
    public int editPostBar(PostBar pb) throws Exception {
    	return this.getBaseDao().update(POST_BAR_PRIFIX + ".update", pb);
    }
    
    /**
     * 根据秀吧ID查询秀吧详情
     * @param postBarId
     * @return
     * @throws Exception
     */
    public PostBar findById(String postBarId) throws Exception {

    	Map<String,Object> paramsMap = new HashMap<String,Object>();
    	paramsMap.put("postBarId", postBarId);
    	return this.getBaseDao().getObject(POST_BAR_PRIFIX + ".findById", paramsMap);
    }
    
    public int exchange(PostBar pb) throws Exception {
    	return this.getBaseDao().update(POST_BAR_PRIFIX + ".exchange", pb);
    }
    
    /**
     * 判断用户是否拥有发帖权限
     * @return
     */
    public boolean isPermissions(Map<String,Object> params) {
    	try {
			int counts = this.getBaseDao().getObject(POST_BAR_PRIFIX + ".findPostBarPermissions", params);
			if(counts > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
    
	// ---------------------------------------------------------------------
	// ----------------------------帖子后台业务-----------------------------
	// ---------------------------------------------------------------------

    /**
     * 查询所有帖子
     * 分页
     * @param params
     * @return
     */
    public Page<Post> findAllPost(Map<String,Object> params ) {
    	Integer pageSize = new Integer((String) params.get("rows"));
        Integer currnetPage = new Integer((String) params.get("page"));
        return this.getBaseDao().page(POST_PRIFIX + ".findAllPost", params, currnetPage, pageSize);
    }
    
    /**
     * 根据帖子ID查询帖子详细信息
     * @param postId
     * @return
     * @throws Exception
     */
    public Post findByPostId(String postId) throws Exception {
    	Map<String,Object> pMap = new HashMap<String,Object>();
    	pMap.put("id", postId);
    	return this.getBaseDao().getObject(POST_PRIFIX + ".findByPostId", pMap);
    }
    
    /**
     * 更新帖子上 / 下 架
     * @param post
     * @return
     */
    public int exchange(Post post) {
    	try {
			return this.getBaseDao().update(POST_PRIFIX + ".update", post);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return -1;
    }
    
    public List<PostBar> findAllPostBarSelect() {
    	try {
			return this.getBaseDao().getList(POST_BAR_PRIFIX + ".findAllPostBarSelect");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return Collections.emptyList();
    }
}