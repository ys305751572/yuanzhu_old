package com.bluemobi.pro.controller.webservice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.pro.service.impl.StuUserServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.CoinLogUtil;
import com.bluemobi.utils.DecodeUtils;
import com.bluemobi.utils.EasemobUtil;
import com.bluemobi.utils.ImageUtils;
import com.bluemobi.utils.MapUtil;
import com.bluemobi.utils.PropertiesUtils;

/**
 * 前端用户 controller
 * 
 * @author gaolei
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping(value = "/api")
public class StuUserController extends BaseController {

    @Autowired
    StuUserServiceImpl stuUserServiceImpl;

    /**
     * 2015-6-23 yesong
     * @param params
     * @return
     * @Description: 获取个人中心活动消息数/任务消息数
     */
    @SuppressWarnings("unchecked")
    @RequestMapping( value = "/person/index", method = RequestMethod.POST )
    @ResponseBody
	public Map<String,Object> getIndex(@RequestParam Map<String,Object> params) {
    	Map<String,Object> map = null;
    	try {
    		map = stuUserServiceImpl.getIndex(params);
    		this.doResp(map, "count", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
    
    /**
     * 查询个人详细信息
     * 
     * @param map
     *            请求参数,参数如下： userId 用户id（必填）
     * @return Map<String, Object>
     * @author gaolei
     */
    @RequestMapping(value = "/person/getDetail")
    @ResponseBody
    public Map<String, Object> getUserDetail(@RequestParam Map<String, Object> params) {
        Map<String, Object> map = null;
        try {
            params.put("id", params.get("userId"));
            map = stuUserServiceImpl.findUserDetailById(params);
            return MapUtil.parse(map, "userInfo", Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 查询用户列表
     * 
     * @param map
     *            请求参数,参数如下： username 好友姓名 sex 性别 provinceId 省份id cityId 城市id
     *            schoolId 学校id departmentId 院系id interestId 兴趣id，可以有多个，中间用逗号隔开
     *            enterSchoolDate 入学时间 pageNum 请求去往页的页码（必填） pageSize 每页显示条数（必填）
     * @return Map<String, Object>
     * @author gaolei
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/user/getList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserList(@RequestParam Map<String, Object> params) {
        try {
            int current = (null != params.get("pageNum")) ? Integer.parseInt((String) params
                    .get("pageNum")) : 0;
            int pagesize = (null != params.get("pageSize")) ? Integer.parseInt((String) params
                    .get("pageSize")) : 10;
            Page suListPage = stuUserServiceImpl.queryAll(params, current, pagesize);
            List<?> suList = (List<?>) suListPage.getRows();
            for (int i = 0; i < suList.size(); i++) {
                Map<String, Object> m = (Map<String, Object>) suList.get(i);
                if (StringUtils.isBlank((String) m.get("nickname"))) {
                    m.put("nickname", m.get("mobile"));
                }
            }
            this.initPage(params, suListPage);
            this.doResp(suListPage.getRows(), "list", this.page, STATUS_SUCCESS, MSG_NULL);
            return this.jsonString;
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * @Description 个人中心模块，通过本接口获取个人资料信息
     * @param 参数列表
     *            map: String userId 用户id ;
     * @return
     * @date 2014-11-18 14:48:04
     * @author 龙哲
     */
    @RequestMapping(value = "/person/info", method = RequestMethod.POST)
    @ResponseBody
    public Map info(@RequestParam Map<String, Object> params) {
        Map stuUser = null;
        try {
            stuUser = stuUserServiceImpl.info(params);
            this.doResp(stuUser, "userInfo", null, STATUS_SUCCESS, MSG_NULL);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 保存个人信息
     * 
     * @author yesong
     * @param params
     * @return
     */
    @RequestMapping(value = "/person/saveInfo", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Map saveInfo(@RequestParam Map<String, Object> params) {
        try {
            String username = Constant.EASEMOB_ + (String) params.get("userId");
            String nickname = (String) params.get("nickname");
            // 去环信修改nickname
            String resp = EasemobUtil.modifyNickname(username, nickname);
            // 如果去环信修改失败 则不修改nickname
            if (StringUtils.isBlank(resp)) {
                params.remove("nickname");
            }
            int perfectInfo = stuUserServiceImpl.findPerfectInfo((String) params.get("userId"));
            if (perfectInfo == Constant.PERFECTINFO_ERROR) {
            	//第一次完善信息加100爱心币
                coinRegist(params);
                params.put("perfectInfo", Constant.PERFECTINFO_SUCCESS);
            }
            int offest = stuUserServiceImpl.saveInfo(params);
            if (offest > 0) {
                this.doResp(DATA_NULL, "userInfo", null, STATUS_SUCCESS, MSG_NULL);
            }
            else {
                this.doError();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 注册爱心币奖励
     * @param params
     * @throws Exception
     */
    private void coinRegist(Map<String, Object> params) throws Exception {
    	  String coin = PropertiesUtils.getPropertiesValues("coin_persion", Constant.COIN_PROP);
          stuUserServiceImpl.addCoin(Integer.parseInt(coin), (String)params.get("userId"));
          // 记录完善信息增加爱友币记录
          CoinLogUtil.insertCoinLog(new Coinlog(Integer.parseInt(params.get("userId").toString()),Integer.parseInt(coin), Constant.COMPLETE));
	}

    /**
     * 修改手机
     * 
     * @author yesong
     * @param params
     * @return
     */
    @RequestMapping(value = "/person/updateMobile", method = RequestMethod.POST)
    @ResponseBody
    public Map updateMobile(@RequestParam Map<String, Object> params) {
        int offset = 0;
        try {
        	String code = params.get("code").toString();
        	if( !this.validateCode(code, params.get("mobile").toString()).booleanValue()) {
        		this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_15);
        		return this.getJsonString();
        	}
            offset = stuUserServiceImpl.updateMobile(params);
            if (offset < 0) {
                this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_09);
            }
            else {
                this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 修改头像
     * 
     * @author yesong
     * @param params
     * @return
     */
    @RequestMapping(value = "/person/updateHead", method = RequestMethod.POST)
    @ResponseBody
    public Map updateHead(@RequestParam Map<String, Object> params,
            @RequestParam("head") MultipartFile file) {
        try {
            String[] path = ImageUtils.saveImage(file, false);
            if (StringUtils.isNotBlank(path[0])) {
                // 更新头像地址
                params.put("head", path[0]);

                stuUserServiceImpl.updateHead(params);
                this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 学生认证
     * 
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/person/cardAuth", method = RequestMethod.POST)
    @ResponseBody
    public Map stuUserAuth(@RequestParam Map<String, Object> params,
            @RequestParam("coverPic") MultipartFile coverPicFile,
            @RequestParam("contentPic") MultipartFile contentPicFile) {
        try {
            String coverPic_path[] = ImageUtils.saveImage(coverPicFile, false);
            String contentPic_pathp[] = ImageUtils.saveImage(contentPicFile, false);
            if (StringUtils.isNotBlank(coverPic_path[0])) {
                params.put("coverPic", coverPic_path[0]);
            }
            if (StringUtils.isNotBlank(contentPic_pathp[0])) {
                params.put("contentPic", contentPic_pathp[0]);
            }
            Map _newParams = _decode(params);// 解码
            _newParams.put("status", "3");
            stuUserServiceImpl.cardAuth(_newParams);

            this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 数据解码
     * 
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    private Map _decode(Map<String, Object> params) throws UnsupportedEncodingException {
        Map _new = new HashMap();
        for (Map.Entry<String, Object> obj : params.entrySet()) {
            String _value = (String) obj.getValue();
            String _key = (String) obj.getKey();
            if (StringUtils.isNotBlank(_value)) {
                _value = DecodeUtils.decode(_value);
            }
            _new.put(_key, _value);
        }
        return _new;
    }

    /**
     * 协助认证
     * 
     * @author yesong
     * @param params
     * @return
     */
    @RequestMapping(value = "/person/userAuth", method = RequestMethod.POST)
    @ResponseBody
    public Map otherAuth(@RequestParam Map<String, Object> params) {
        int offset = 0;
        try {
            offset = stuUserServiceImpl.userAuth(params);
            if(offset == -2){
            	 this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_09);
            } else if (offset == -1) {
                this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_03);
            }
            else {
                this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 查询签名
     * 
     * @author yesong
     * @param params
     * @return
     */
    @RequestMapping(value = "/person/myWord", method = RequestMethod.POST)
    @ResponseBody
    public Map myWord(@RequestParam Map<String, Object> params) {
        try {
            Map map = stuUserServiceImpl.myWord(params);
            this.doResp(map, "word", null, STATUS_SUCCESS, MSG_NULL);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 更新签名
     * 
     * @author yesong
     * @param params
     * @return
     */
    @RequestMapping(value = "/person/saveWord", method = RequestMethod.POST)
    @ResponseBody
    public Map saveWord(@RequestParam Map<String, Object> params) {
        try {
        	String word = params.get("word").toString();
        	//String newWord = URLDecoder.decode(word, "UTF-8");
//        	int len = word.length();
//        	for (int i = 0; i < len; i++) {
//        		char w = word.charAt(i);
//        		System.out.println(Integer.toHexString(w));
//			}
        	word = word.replaceAll("[^\\u0000-\\uFFFF]", ""); 
        	params.put("word", word);
            stuUserServiceImpl.saveWord(params);
            this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 查询爱心币
     * 
     * @author yesong
     * @param params
     * @return
     */
    @RequestMapping(value = "/person/coin", method = RequestMethod.POST)
    @ResponseBody
    public Map coin(@RequestParam Map<String, Object> params) {
        Map map = null;
        try {
            map = stuUserServiceImpl.coin(params);
            this.doResp(map, "coin", null, STATUS_SUCCESS, MSG_NULL);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 操作爱心币
     * 
     * @author yesong
     * @param params
     * @return
     */
    @RequestMapping(value = "/person/coinOperate", method = RequestMethod.POST)
    @ResponseBody
    public Map coinOperate(@RequestParam Map<String, Object> params) {
        try {
            int offset = stuUserServiceImpl.coinOperate(params);
            if (offset < 0) {
                this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_12);
            }
            else {
                this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
        }
        return this.getJsonString();
    }

    /**
     * 返回充值记录 yesong 2015-1-5
     * 
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/person/coinlist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> coinList(@RequestParam Map<String, Object> params) {
        Page<?> page = null;
        try {
            page = stuUserServiceImpl.coinList(params);
            this.initPage(params, page);
            this.doResp(page.getRows(), "list", this.page, STATUS_SUCCESS, MSG_NULL);
        }
        catch (Exception e) {
            e.printStackTrace();
            doError();
        }
        return this.getJsonString();
    }

    /**
     * 贡献爱心币给好友
     * 
     * @param params
     *            请求参数,参数如下： userId 当前用户id（必填） coin 爱心币数量（必填） friendId 好友id（必填）
     * @author gaolei
     */
    @RequestMapping(value = "/person/sendCoinToFriend", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sendCoinToFriend(@RequestParam Map<String, Object> params) {
        try {
            String flag = stuUserServiceImpl.sendCoinToFriend(params);
            if (flag.equals(Constant.ERROR_12)) { // 爱心币不足
                return MapUtil.parse(Constant.STATUS_OK, Constant.ERROR_12);
            }
            else {
                return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 更新用户在线状态
     * 
     * @param map
     *            请求参数,参数如下： userId 用户id（必填） isOnline 用户在线状态（必填）
     * @return Map<String, Object>
     * @author gaolei
     */
    @RequestMapping(value = "/person/updateOnlineStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateOnlineStatus(@RequestParam Map<String, Object> params) {
        try {
            int i = stuUserServiceImpl.updateOnlineStatus(params);
            if (i == 0) {
                return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
            }
            return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 获取个人二维码
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/person/qrcode" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getQrcode(@RequestParam Map<String,Object> params) {
    	try {
			Map<String,Object> reMap = stuUserServiceImpl.getQrCode(params);
			this.doResp(reMap, "data",null,STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
    
    // ***********************************************************************
    // *****************************三期接口*************************************
    // ***********************************************************************
    
    /**
     * 绑定支付宝账号
     * @param params
     * @return
    */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "person/bindAlipay", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bindAlipay(@RequestParam Map<String,Object> params) {
    	
    	try {
			stuUserServiceImpl.bindAlipay(params);
			 this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
     
    /**
     * userId : 用户ID
     * type : 类型 0.支付宝 1.积分
     * @param params
     * @return
      */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "person/wallet", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> wallet(@RequestParam Map<String,Object> params) {
    	
    	try {
			Map<String,Object> stuUser = stuUserServiceImpl.wallet(params);
			List recordList = stuUserServiceImpl.recordList(params);
			stuUser.put("recordList", recordList);
			this.doResp(stuUser, "userInfo", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
   
}
