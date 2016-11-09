package com.bluemobi.pro.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.MD5;

/**
 * <p>Title: RegisterServiceImpl.java</p> 
 * <p>Description: 注册service</p> 
 * @author yesong  
 * @date 2014-11-13 下午04:22:23
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@Service
public class RegisterServiceImpl extends BaseService{

	private static final String PREIFX = StuUser.class.getName();
	/**
	 * 检测该手机号是否被注册
	 * @param tel 注册手机号
	 * @return
	 * @throws Exception
	 */
	public boolean checkMobile (String tel) throws Exception {
		Map _user = this.getBaseDao().get(PREIFX + ".queryUserByTel",tel);
		if(_user == null) return true;
		return false;
	}
	
	/**
	 * 注册
	 * @param _user
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map registerStuUser (StuUser _user) throws Exception {
		if(_user == null || _user.getMobile() == null || _user.getPassword() == null){
			throw new Exception("手机号或密码不能为空");
		}
		try{
			this.getBaseDao().save(PREIFX + ".insertEntity", _user);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return this.getBaseDao().get(PREIFX + ".queryStuUserById", _user.getId());
	}
	
	/**
	 * 根据手机号删除用户
	 */
	public int deleteByMobile (String mobile) throws Exception {
		return this.getBaseDao().update(PREIFX + ".deleteByMobile", mobile);
	}
	
	/**
	 * 修改/找回密码
	 * @param _user
	 * @return
	 * @throws Exception
	 */
	public Map updatePsw(StuUser _user) throws Exception {
		if(_user == null || _user.getMobile() == null || _user.getPassword() == null){
			throw new Exception("用户不能为空");
		}
		try{
			this.getBaseDao().update(PREIFX + ".updatePsw", _user);
		}catch (Exception e) {
			e.printStackTrace();
		}
		Map initUser = this.getBaseDao().get(PREIFX + ".queryUserByTel", _user.getMobile());
		return initUser;
	}
}
