package com.bluemobi.pro.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.BackgroudImg;
import com.bluemobi.pro.pojo.UserBgImg;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;

/**
 * 个性装扮图片
 * 
 * @author yesong
 *
 */
@Service
public class BackgroudImgSeviceImpl extends BaseService {

	private String PRIFIX = BackgroudImg.class.getName();

	/**
	 * 新增
	 * 
	 * @param backgroudImg
	 * @throws Exception
	 */
	public void add(BackgroudImg backgroudImg) throws Exception {
		this.getBaseDao().save(PRIFIX + ".insert", backgroudImg);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer id) throws Exception {
		this.getBaseDao().delete(PRIFIX + ".delete", id);
	}

	public List<BackgroudImg> all() throws Exception {
		return this.getBaseDao().getList(PRIFIX + ".findAll");
	}
	
	/**
	 * 查询
	 * 
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	public Page<List<BackgroudImg>> page(Integer pagenum, Integer pagesize) {
		return this.getBaseDao().page(PRIFIX + ".findAll", null, pagenum, pagesize);
	}

	/**
	 * 用户设置个人装扮
	 * 
	 * @param userId
	 * @param bgId
	 * @throws Exception
	 */
	public void userSet(Integer userId, String bgimgPath) throws Exception {
		// 数据为空则新增记录，不为空则更新记录
		Integer count = this.getBaseDao().get(PRIFIX + ".findUserBgImgByUserId", userId);
		UserBgImg userBgImg = new UserBgImg();
		userBgImg.setBgimgPath(bgimgPath);
		userBgImg.setUserId(userId);
		if (count == null || count == 0) {
			insertUserBgImg(userBgImg);
		} else {
			updaeUserBgImg(userBgImg);
		}
	}

	private void updaeUserBgImg(UserBgImg userBgImg) throws Exception {
		this.getBaseDao().update(PRIFIX + ".updateByUserId", userBgImg);
	}

	private void insertUserBgImg(UserBgImg userBgImg) throws Exception {
		this.getBaseDao().update(PRIFIX + ".setByUserId", userBgImg);
	}

	/**
	 * 查询用户设置的背景图片
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String findByUserId(Integer userId) throws Exception {
		return this.getBaseDao().getObject(PRIFIX + ".findByUserId", userId);
	}
}
