package com.bluemobi.pro.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bluemobi.pro.pojo.City;
import com.bluemobi.pro.pojo.Placard;
import com.bluemobi.pro.pojo.PlacardRecord;
import com.bluemobi.pro.pojo.PlacardType;
import com.bluemobi.pro.pojo.Province;
import com.bluemobi.pro.pojo.RecordVo;
import com.bluemobi.pro.pojo.Scs;
import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.pro.service.impl.PlacardServiceImpl;
import com.bluemobi.pro.service.impl.PlacardTypeServiceImpl;
import com.bluemobi.sys.bean.DataCache;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.pojo.Sysuser;
import com.bluemobi.utils.CookiesUtils;
import com.bluemobi.utils.ImageUtils;
import com.bluemobi.utils.SessionUtils;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class PlacardController {

    @Autowired
    private PlacardServiceImpl placardServiceImpl;
    
    @Autowired
    private PlacardTypeServiceImpl PlacardTypeServiceImpl;

    // ****************************框架外代码*********************************

    /***
     * 获取公告列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allplacard")
    @ResponseBody
    public Page<List<Placard>> allplacard(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Sysuser user = SessionUtils.getCurrentUser();
        map.put("userType", user.getUserType());
        map.put("schoolId", user.getSchoolId());
        map.put("cityId", user.getCityId());
        map.put("provinceId", user.getProvinceId());
        
        Page<List<Placard>> page = placardServiceImpl.list(map, pages, rows);
        return page;
    }

    @RequestMapping("/userType")
    @ResponseBody
    public String userType() {
    	Sysuser user = SessionUtils.getCurrentUser();
        return "" + user.getUserType();
    }
    
    @RequestMapping("/placardlist")
    public String list() {
        return "main/placard/placardlist";
    }

    @RequestMapping("/placardAdd")
    public String add(Model model) {
    	 Sysuser user = SessionUtils.getCurrentUser();
    	 model.addAttribute("user",user);
        return "main/placard/placardadd";
    }

    @RequestMapping("/getPlacard")
    public String getPlacard(@RequestParam String id, @RequestParam String type, HttpSession session) {
        try {
            if (type.equals("show")) {
                Map<String, Object> map = placardServiceImpl.findOneById(id);
                session.setAttribute("placard", map);
            }
            else {
                Placard placard = placardServiceImpl.getById(id);
                session.setAttribute("placard", placard);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (type.equals("show")) {
            return "main/placard/placardshow";
        }
        else {
            return "main/placard/placardupdate";
        }
    }

    /**
     * 根据条件查询省份
     * 
     * @return
     */
    @RequestMapping("findProvinceByParamsList")
    @ResponseBody
    public List<Province> findProvinceByParamsList(@RequestParam Map<String, Object> params) {
        List<Province> list = null;
        try {
            list = placardServiceImpl.findProvinceByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Province province = new Province();
        province.setId(-1);
        province.setName("所有省份");
        list.add(0, province);
        return list;
    }

    /**
     * 根据条件查询省份
     * 
     * @return
     */
    @RequestMapping("findProvinceByParams")
    @ResponseBody
    public List<Province> findProvinceByParams(@RequestParam Map<String, Object> params) {
        List<Province> list = null;
        try {
            list = placardServiceImpl.findProvinceByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据条件查询城市
     * 
     * @return
     */
    @RequestMapping("findCityByParamsList")
    @ResponseBody
    public List<City> findCityByParamsList(@RequestParam Map<String, Object> params) {
        List<City> li = null;
        try {
            li = placardServiceImpl.findCityByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        City city = new City();
        city.setId(-1);
        city.setName("所有城市");
        li.add(0, city);
        return li;
    }

    /**
     * 根据条件查询城市
     * 
     * @return
     */
    @RequestMapping("findTypeByParamsList")
    @ResponseBody
    public List<PlacardType> findTypeByParamsList(@RequestParam Map<String, Object> params) {
        List<PlacardType> li = null;
        try {
            li = PlacardTypeServiceImpl.findAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        PlacardType type = new PlacardType();
        type.setId(-1);
        type.setType("全部");
        li.add(0,type);
        return li;
    }
    
    /**
     * 根据条件查询城市
     * 
     * @return
     */
    @RequestMapping("findCityByParams")
    @ResponseBody
    public List<City> findCityByParams(@RequestParam Map<String, Object> params) {
        List<City> li = null;
        try {
            li = placardServiceImpl.findCityByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    /**
     * 根据条件查询学校
     * 
     * @return
     */
    @RequestMapping("findSchoolByParamsList")
    @ResponseBody
    public List<Scs> findSchoolByParamsList(@RequestParam Map<String, Object> params) {
        List<Scs> li = null;
        try {
            li = placardServiceImpl.findSchoolByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Scs scs = new Scs();
        scs.setId(-1);
        scs.setProName("所有学校");
        li.add(0, scs);
        return li;
    }

    /**
     * 根据条件查询学校
     * 
     * @return
     */
    @RequestMapping("findSchoolByParams")
    @ResponseBody
    public List<Scs> findSchoolByParams(@RequestParam Map<String, Object> params) {
        List<Scs> li = null;
        try {
            li = placardServiceImpl.findSchoolByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    /**
     * 根据条件查询学院
     * 
     * @return
     */
    @RequestMapping("findCollegeByParams")
    @ResponseBody
    public List<Scs> findCollegeByParams(@RequestParam Map<String, Object> params) {
        List<Scs> li = null;
        try {
            li = placardServiceImpl.findCollegeByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
    /***
     * 添加公告
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/addPlacard")
    @ResponseBody
    public String addPlacard(@RequestParam Map<String, Object> map,
            MultipartHttpServletRequest request, HttpSession session) {
        int flag = 0;

        // 判断图片是否被删除
        if (map.get("IMG").equals(null) || map.get("IMG").equals("")) {
            map.put("picture", null);
        }
        else {
            try {
                // 将图片保存在服务器
                MultipartFile imgFile = request.getFile("add_img");
                if (!imgFile.isEmpty()) {
                    String[] imgurl = ImageUtils.saveImage(imgFile, false);
                    map.put("picture", imgurl[0]);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 获取当前用户的编号
        Sysuser user = (Sysuser) session.getAttribute("loginUser");
        if (null == user) {
            Map<String, Object> params = CookiesUtils.ReadCookieMap(request);
            map.put("stuUserId", new Integer((String) params.get("id")));
        }
        else {
            map.put("stuUserId", user.getId());
        }
        try {
            flag = placardServiceImpl.add(map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return flag + "";
    }

    /***
     * 修改公告
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/updatePlacard")
    @ResponseBody
    public String updatePlacard(@RequestParam Map<String, Object> map,
            MultipartHttpServletRequest request, HttpSession session) {
        // 判断图片是否被删除
        if (map.get("IMG").equals(null) || map.get("IMG").equals("")) {
            map.put("picture", "");
        }
        else if (!((String) map.get("IMG")).substring(0, 4).equals("http")) {
            // 将图片保存在服务器
            MultipartFile imgFile = request.getFile("edit_img");
            try {
                if (!imgFile.isEmpty()) {
                    String[] imgurl = ImageUtils.saveImage(imgFile, false);
                    map.put("picture", imgurl[0]);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            map.put("picture", map.get("IMG"));
        }

        int offset = 0;
        try {
            // 获取当前用户的编号
            Sysuser user = (Sysuser) session.getAttribute("loginUser");
            if (null == user) {
                Map<String, Object> params = CookiesUtils.ReadCookieMap(request);
                map.put("stuUserId", new Integer((String) params.get("id")));
            }
            else {
                map.put("stuUserId", user.getId());
            }
            offset = placardServiceImpl.update(map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return offset + "";
    }

    /***
     * 删除公告
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/deletePlacard")
    @ResponseBody
    public String deletePlacard(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            offset = placardServiceImpl.delete(map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return offset + "";
    }

    /**
     * 删除公告--多选
     * 
     * @return
     */ 
    @RequestMapping("/deletePlacards")
    @ResponseBody
    public String deletePlacards(@RequestParam Map<String, Object> params) {
        // 要删除的数据集，以字符串的形式存在，中间用逗号隔开
        String deleteids = params.get("ary").toString();
        // 获取到要删除公告id的数组
        String[] dellist = deleteids.split(",");
        // 调用删除方法，传入要删除公告id的数组
        return placardServiceImpl.delete(dellist);
    }
    
    @RequestMapping("/placardRecordlist")
    public String placardRecordlist(Integer id, Model model) {
    	
    	model.addAttribute("placardId", id);
        return "main/placard/placardrecordlist";
    }
    
    /***
     * 获取公告阅读记录
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allplacardRecord")
    @ResponseBody
    public Page<PlacardRecord> allplacardRecord(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Object placardId = map.get("placardId");
        System.out.println("placardId:" + placardId);
//        map.put("userType", user.getUserType());
//        map.put("schoolId", user.getSchoolId());
//        map.put("cityId", user.getCityId());
//        map.put("provinceId", user.getProvinceId());
        
        Page<PlacardRecord> page = placardServiceImpl.findPlacardRecordPage(map,pages, rows);
        toAddSchool(page);
        toListRecordVo(page);
        return page;
    }
    
	@SuppressWarnings("unchecked")
	public void toAddSchool(Page<PlacardRecord> page) {
		Collection<PlacardRecord> list = page.getRows();
		for (PlacardRecord reocrd : list) {
			StuUser stuuser = reocrd.getUser();
			Object schoolObj = DataCache.getInstance().getObjectByCode("scs",
					"" + stuuser.getProvinceId() + stuuser.getCityId() + stuuser.getSchoolId());
			Object collegeObj = DataCache.getInstance().getObjectByCode("scs",
					"" + stuuser.getProvinceId() + stuuser.getCityId() + stuuser.getCollegeId());
			if (schoolObj != null) {
				String school = ((Map<String, Object>) schoolObj).get("proName").toString();
				stuuser.setSchool(school); 
			} else {
				stuuser.setSchool("");
			}
			if (collegeObj != null) {
				stuuser.setCollege(((Map<String, Object>) collegeObj).get("proName").toString());
			} else {
				stuuser.setCollege("");
			}
		}
	}
	
	public void toListRecordVo(Page<PlacardRecord> page) {
		List<PlacardRecord> recordList = (List<PlacardRecord>) page.getRows();
		for (PlacardRecord placardRecord : recordList) {
			placardRecord.setName(placardRecord.getUser().getName());
			placardRecord.setMobile(placardRecord.getUser().getMobile());
			placardRecord.setSchool(placardRecord.getUser().getSchool());
			placardRecord.setCollege(placardRecord.getUser().getCollege());
		}
	}
}
