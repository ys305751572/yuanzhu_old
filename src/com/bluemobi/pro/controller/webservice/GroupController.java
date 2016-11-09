package com.bluemobi.pro.controller.webservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.EasemobGroupForm;
import com.bluemobi.pro.pojo.Groupinfo;
import com.bluemobi.pro.pojo.Grouptype;
import com.bluemobi.pro.service.impl.CoinlogServiceImpl;
import com.bluemobi.pro.service.impl.FriendServiceImpl;
import com.bluemobi.pro.service.impl.GroupServiceImpl;
import com.bluemobi.pro.service.impl.GroupmemberServiceImpl;
import com.bluemobi.pro.service.impl.StuUserServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.EasemobUtil;
import com.bluemobi.utils.ImageUtils;
import com.bluemobi.utils.MapUtil;
import com.bluemobi.utils.QrCodeUtil;
/**
 * 群 controller
 * 
 * @author gaolei
 */
@Controller
@RequestMapping(value = "/api")
public class GroupController extends BaseController {

    @Autowired
    public GroupServiceImpl groupServiceImpl;

    @Autowired
    public StuUserServiceImpl stuUserServiceImpl;

    @Autowired
    public CoinlogServiceImpl coinlogServiceImpl;

    @Autowired
    public FriendServiceImpl friendServiceImpl;

    @Autowired
    public GroupmemberServiceImpl groupmemberServiceImpl;

    // 15号发送福利
    public static final Integer DAY = 28;
    /**
     * 创建群
     * 
     * @param map
     *            请求参数,参数如下： groupName 群组名称, 此属性为必须的 maxusers 群组成员最大数(包括群主),
     *            值为数值类型,默认值200,此属性为可选的 owner 群组的管理员, 此属性为必须的 userId 后台用户id head
     *            群头像 provinceId 省份编号 cityId 城市编号 groupType 群类型 scaleId 群规模
     *            message 群寄语
     * @author gaolei
     */
    @SuppressWarnings( { "rawtypes", "unchecked" })
    @RequestMapping(value = "/group/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> create(@RequestParam Map<String, Object> params,
            @RequestParam("head") MultipartFile file) {
        try {
        	
            params = MapUtil._decode(params);
            // 群名
            String groupName = (String) params.get("groupName");
            groupName = groupName.replaceAll("[^\\u0000-\\uFFFF]", ""); 
            
            // 群规模，如果没有传，默认是200，环信的默认值是200
            int maxusers = params.get("maxusers") != null ? Integer.parseInt((String) params
                    .get("maxusers")) : 200;
            String owner = (String) params.get("owner");
            int userId = Integer.parseInt((String) params.get("userId"));
            String headUrl = ImageUtils.saveImage(file, false)[0];

            Map m = new HashMap();
            m.put("id", userId);
            int schoolId = (Integer) stuUserServiceImpl.findOneById(m).get("schoolId");

            int provinceId = params.get("provinceId") != null ? Integer.parseInt((String) params
                    .get("provinceId")) : 0;
            int cityId = params.get("cityId") != null ? Integer.parseInt((String) params
                    .get("cityId")) : 0;
            int groupType = params.get("groupType") != null ? Integer.parseInt((String) params
                    .get("groupType")) : 0;
            int scaleId = params.get("scaleId") != null ? Integer.parseInt((String) params
                    .get("scaleId")) : 2;
            String message = (String) params.get("message");
            message = message.replaceAll("[^\\u0000-\\uFFFF]", "");
            // 去环信创建群
            EasemobGroupForm form = new EasemobGroupForm();
            form.setGroupname(groupName);
            form.setMaxusers(scaleId * 100);
            form.setOwner(owner);
            String groupId = EasemobUtil.createGroup(form);

            // 环信群创建成功后，相应群信息存本地
            if (StringUtils.isNotEmpty(groupId)) {
                Groupinfo groupinfo = new Groupinfo();
                groupinfo.setId(groupId);
                groupinfo.setName(groupName);
                groupinfo.setStuUserId(userId);
                groupinfo.setHead(headUrl);
                groupinfo.setSchoolId(schoolId);
                groupinfo.setProvinceId(provinceId);
                groupinfo.setCityId(cityId);
                groupinfo.setGroupType(groupType);
                groupinfo.setScaleId(scaleId);
                groupinfo.setMessage(message);
                groupinfo.setCreateTime(System.currentTimeMillis());
                groupinfo.setCoin(Constant.GROUP_DEFAULT_COIN);
                
                groupServiceImpl.save(groupinfo);
                //更新二维码
                QrCodeUtil.generateQrCode(Constant.QRCODE_GROUP, groupId, "groupinfo",false,0);

                Map<String, Object> gm = new HashMap<String, Object>();
                gm.put("groupId", groupId);
                gm.put("userId", userId);
                groupmemberServiceImpl.addMember(gm);
                return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
            }
            else {
                return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 查询群详情
     * 
     * @param map
     *            请求参数,参数如下： groupId 群id（必填） userId 用户id 用来查询群内好友人数（必填）
     * @author gaolei
     */
    @RequestMapping(value = "/group/getDetail")
    @ResponseBody
    public Map<String, Object> getDetail(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> groupinfo = groupServiceImpl.getDetail(params);

            List<Map<String, Object>> memberList = groupServiceImpl.getMembers(params);
            // 群人数
            groupinfo.put("peopleNumber", memberList.size());

            // 群内好友人数
            Map<String, Object> fParams = new HashMap<String, Object>();
            fParams.put("stuUserId", params.get("userId"));
            List<String> friendIdList = friendServiceImpl.getFriendIdList(fParams);
            List<String> memberIdList = new ArrayList<String>();
            int friendNumber = 0;
            for (int i = 0; i < memberList.size(); i++) {
                if (memberList.get(i) != null) {
                    int id = (Integer) (memberList.get(i).get("id"));
                    memberIdList.add(id + "");
                }
            }
            for (int i = 0; i < memberIdList.size(); i++) {
                for (int j = 0; j < friendIdList.size(); j++) {
                    if (memberIdList.get(i) != null
                            && memberIdList.get(i).equals(friendIdList.get(j))) {
                        friendNumber = friendNumber + 1;
                    }
                }
            }
            groupinfo.put("friendNumber", friendNumber);
            return MapUtil.parse(groupinfo, "groupinfo", Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 查询群列表信息 如果参数里面有userId 则是查询我的群列表，否则是查询所有群列表
     * 
     * @param map
     *            请求参数,参数如下： name 群名 groupType 群类型 userId 用户id pageNum 查询页码
     *            pageSize 每页显示条数
     * @author gaolei
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/group/getList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryGroupList(@RequestParam Map<String, Object> params) {
        try {
            params.put("status", 1);
            String operate = ( (params.get("operate") == null) ?  "" : params.get("operate").toString());
            // 我的群
            if ( (StringUtils.isBlank(operate) && params.keySet().contains("userId")) ||  operate.equals("1")) {
                return queryMyGroupList(params);
            }
            // 所有群
            else {
                int current = (null != params.get("pageNum")) ? Integer.parseInt((String) params
                        .get("pageNum")) : 0;
                int pagesize = (null != params.get("pageSize")) ? Integer.parseInt((String) params
                        .get("pageSize")) : 10;
                Page<List<Groupinfo>> groupinfoPage = groupServiceImpl.getGroupList(params,
                        current, pagesize);
                this.initPage(params, groupinfoPage);
                this.doResp(groupinfoPage.getRows(), "list", this.page, STATUS_SUCCESS, MSG_NULL);
                return this.jsonString;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 查询我的群
     * 
     * @author gaolei
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> queryMyGroupList(Map<String, Object> params) {
        try {
            List<?> list = groupServiceImpl.getMyGroupList(params);
            // this.initPage(params, page);
            // return MapUtil.parse(list, "list", Constant.STATUS_OK,
            // Constant.MSG_OK);
            this.doResp(list, "list", null, STATUS_SUCCESS, MSG_NULL);
            return this.getJsonString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 群添加成员
     * 
     * @param map
     *            请求参数,参数如下： groupId 群id（必填） userId 用户id（必填）
     * @author gaolei
     */
    @RequestMapping("/group/addMember")
    @ResponseBody
    public Map<String, Object> addMember(@RequestParam Map<String, Object> params) {
        try {
            groupmemberServiceImpl.addMember(params);
            return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 群减少成员
     * 
     * @param map
     *            请求参数,参数如下： groupId 群id（必填） userId 用户id（必填）
     * @author gaolei
     */
    @RequestMapping("/group/removeMember")
    @ResponseBody
    public Map<String, Object> removeMember(@RequestParam Map<String, Object> params) {
        try {
            String groupId = (String) params.get("groupId");
            String stuUserId = (String) params.get("userId");
            // 环信删除成员
            String resp = EasemobUtil.groupRemoveMember(groupId, Constant.EASEMOB_ + stuUserId);
            // 信删除成员成功，则删除本地群和用户的关系
            if ("true".equals(resp)) {
            	groupmemberServiceImpl.removeMember(params);
                return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
            }
            else {
                return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 查询群成员
     * 
     * @param map
     *            请求参数,参数如下： groupId 群id（必填）
     * @author gaolei
     */
    @RequestMapping("/group/getMembers")
    @ResponseBody
    public Map<String, Object> getMembers(@RequestParam Map<String, Object> params) {

        try {
            List<Map<String, Object>> list = groupServiceImpl.getMembers(params);
            return MapUtil.parse(list, "list", Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 解散群
     * 
     * @param map
     *            请求参数,参数如下： groupId 群id（必填）
     * @author gaolei
     */
    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Map<String, Object> map) {
        try {
            String groupId = (String) map.get("groupId");
            // 去环信删除群组
            String success = EasemobUtil.deleteGroup(groupId);
            // 删除本地群信息
            if ("true".equals(success)) {
                groupServiceImpl.delete(map);
                return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
            }
            else {
                return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 修改群公告
     * 
     * @param map
     *            请求参数,参数如下： groupId 群id（必填） notice 公告（必填）
     * @author gaolei
     */
    @RequestMapping(value = "/group/editNotice", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editNotice(@RequestParam Map<String, Object> map) {
        try {
            map.put("id", map.get("groupId"));
            map.put("noticeTime", System.currentTimeMillis());
            groupServiceImpl.update(map);
            return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 修改群信息
     * 
     * @param map
     *            请求参数,参数如下： groupId 群id（必填）
     * @author gaolei
     */
    @RequestMapping(value = "/group/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@RequestParam Map<String, Object> map) {
        try {
            map.put("id", map.get("groupId"));
            groupServiceImpl.update(map);
            return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 发放爱心币
     * 
     * @param params
     *            请求参数,参数如下： groupId 群id（必填） coin 爱心币数量（必填）
     * @author gaolei
     */
    @RequestMapping(value = "/group/sendMoney", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sendMoney(@RequestParam Map<String, Object> params) {
        try {
        	if(!isToday()){
        		return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_13);
        	}
        	
            String flag = groupServiceImpl.sendMoney(params);
            if (Constant.ERROR_11.equals(flag)) {
                return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_11);
            }
            else if (Constant.ERROR_12.equals(flag)) {
                return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_12);
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
     * 判断当天是否是发放福利的日期
     * @return
     */
    private boolean isToday() {
    	if(DAY ==  Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
    		return true;
    	}
    	else{
    		return false;
    	}
		
	}

    public static void main(String[] args) {
		System.out.println();
	} 
    
	/**
     * 贡献coin到群
     * 
     * @param params
     *            请求参数,参数如下： groupId 群id（必填） coin 爱心币数量（必填） userId 用户id
     * @author gaolei
     */
    @RequestMapping(value = "/group/sendCoinToGroup", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sendCoinToGroup(@RequestParam Map<String, Object> params) {
        try {
            groupServiceImpl.sendCoinToGroup(params);
            groupServiceImpl.updateMsgCount(params);
            return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 获取群类型
     * 
     * @author gaolei
     */
    @RequestMapping(value = "/common/getGroupTypeList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getGroupTypeList() {
        List<Grouptype> list = new ArrayList<Grouptype>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            list = groupServiceImpl.findTypeByParams(params);
            return MapUtil.parse(list, "list", Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }

    /**
     * 查询群规模
     * 
     * @author gaolei
     */
    @RequestMapping(value = "/group/queryGroupscale", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryGroupscale() {
        try {
            List<Map<String, Object>> list = groupServiceImpl.queryGroupscale();
            return MapUtil.parse(list, "list", Constant.STATUS_OK, Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
        }
    }
    
    /**
     * 群查询首页-获取推荐群
     * @param params
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value="/group/hotGroup" ,method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> getHotGroupList(@RequestParam Map<String,Object> params) {
    	try {
			Page page = groupServiceImpl.getHotGroupList(params);
			this.initPage(params, page);
			this.doResp(page.getRows(), "list", this.page, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
    
    /**
     * 查询用户在该群 是否已签到 
     * @param params
     * @return
     */
    @RequestMapping(value="/group/signtype",method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
	public Map<String,Object> getSignType(@RequestParam Map<String,Object> params) {
    	
    	 try {
    		 Map<String,Object> map = groupServiceImpl.getDaySign(params);
    		 this.doResp(map,"data", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
    
    /**
     * 群签到
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/group/sign", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> groupSign(@RequestParam Map<String,Object> params) {
    	try {
			groupServiceImpl.groupSign(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    } 
    
    /**
     * 获取群二维码
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/group/qrcode")
    @ResponseBody
	public Map<String,Object> groupQrCode(@RequestParam Map<String,Object> params) {
    	try {
			Map<String,Object> qrCode = groupServiceImpl.groupQrcode(params);
			this.doResp(qrCode, "data", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/group/editMessage" , method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> editMessage(@RequestParam Map<String,Object> params) {
    	try {
			groupServiceImpl.editMessage(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
}
