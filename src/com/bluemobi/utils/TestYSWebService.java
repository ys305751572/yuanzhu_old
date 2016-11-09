package com.bluemobi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestYSWebService {

    public static void main(String[] args) {
        // 登录
        //testLogin();
        // 获取省份
        //  testProvince();
        // 获取城市
        // testCity();
        // 获取学校
        // testSchool();
        // 获取年份
        // testYears();
        // 获取兴趣
        // testInterest();
        // 获取公告
        // testPlacard();
        // 获取详细
        // testPlacardDetail();

        // 新增活动
        // testAdd();
        // 搜索活动
        //  testSearch();
        // 活动详情
        // testSearchById();
        // 评论活动
        // testReply();
        // 收藏活动
        // testFavorite();
        // 参与活动
        // testApply();

        // 评论列表
        // testCommentList();

        // 参与人员审核
        // testApplyUser();

        // 参与人员列表
        //testApplyList();
        // 操作爱心币
        // testCoinOperate();
    	// 查询爱心币
    	// testCoinInfo();
        // 个人资料
        //testInfo();
    	// 个人资料保存
    	// testSaveInfo();
    	
        // 获取关键词列表信息
        //testFiltrationList();

        // 添加举报信息
        //testCreateReport();
    	
    	//协助认证
    	// testAuth();
    	//查询活动类型
    	//testActtype();
    	
    	//取消收藏
    	//testUnf();
    	
    	//新增反馈
    	// testInsertFd();
    	
    	//getUUIDAndName();
    	//getStatusAndResp();
        // sendVerificationCode();
    	//coinList();
    	
    	getCode();
        // 找回密码
//         testFindPsw();
    	   // 测试注册
//        testRegister();
    	
//    	addCart();
    }

    // 找回密码
    public static void testFindPsw() {
        String urlStr = "http://localhost:8088/youzhu/account/findPsw";
        String paramStr = "mobile=13476107753&password=e10adc3949ba59abbe56e057f20f883e&code=265147";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    
    // 测试注册
    public static void testRegister() {
        String urlStr = "http://localhost:8088/youzhu/account/regist";
        String paramStr = "mobile=13476107753&password=e10adc3949ba59abbe56e057f20f883e&nickname=sye222&code=622873";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }
    
	private static void getCode() {
    	String urlStr = "http://localhost:8088/youzhu/account/getCode";
    	String mobile = "13476107753";
    	String timestamp = "" + System.currentTimeMillis() ;
    	String random = "123456";
    	String token = "789456";
    	
    	String totalStr = mobile + timestamp + random + token;
    	System.out.println("totalStr:" +totalStr);
    	String url_token = MD5.md5(totalStr);
    	
        String paramStr = "mobile="+ mobile +"&timestamp=" + timestamp + "&random="+ 123456 +"&token=" + token + "&url_token=" + url_token;
        
        
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
	}

	public static void onceHeart() {
    	
    }
    
    public static void coinList() {
    	String urlStr = "http://localhost:8080/yuanzhu/person/coinlist";
        String paramStr = "userId=556619&pageNum=1&pageSize=10";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
	}

    
    public static void testCoinInfo() {
    	String urlStr = "http://localhost:8080/youzhu/person/coin";
        String paramStr = "userId=556594";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
	}
    
    public static void sendVerificationCode() {
    	String urlStr = "http://180.169.58.224:8085/youzhu/account/getCode";
        String paramStr = "mobile=18616720450&content=您好，您的手机验证码为：659863";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
	}

	//新增反馈
    public static void getUUIDAndName() {
  	  	String urlStr = "http://localhost:8080/youzhu/alipay/getUUIDAndName";
        String paramStr = "userId=556619&coin=100";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
	}
    
    
//  //新增反馈
//    public static void getStatusAndResp() {
//  	  	String urlStr = "http://localhost:8080/youzhu/alipay/respResult";
//        String paramStr = "userId=556619&status=0&coin=100&money=1&orderId=bce45c7c333b4708978dc2d293af8bd8&orderName=20142519052546556619";
//        String str = WebserviceUtil.post(urlStr, paramStr);
//        System.out.println(str);
//	}
    
    //新增反馈
    public static void testInsertFd() {
  	  	String urlStr = "http://10.58.168.91:8888/youzhu/feedback/create";
        String paramStr = "userId=18&content=12313156465";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
	}
    
    //取消收藏
    public static void testUnf() {
  	  String urlStr = "http://localhost:8080/youzhu/activity/unfavorite";
        String paramStr = "userId=18&activityId=1";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
	}

    public static void testActtype() {
    	  String urlStr = "http://localhost:8080/youzhu/activity/acttype";
          String paramStr = "";
          String str = WebserviceUtil.post(urlStr, paramStr);
          System.out.println(str);
	}
    
    public static void testAuth() {
    	  String urlStr = "http://localhost:8080/yuanzhu/person/userAuth";
          String paramStr = "userId=558730&mobile=13476107753&name=叶松";
          String str = WebserviceUtil.post(urlStr, paramStr);
          System.out.println(str);
	}

	// 测试注册
    public static void testSaveInfo() {
        String urlStr = "http://localhost:8080/youzhu/person/saveInfo";
        String paramStr = "userId=556682&head=&name=name333&nickName=name444&mobile=15927272727&email=305751572@qq.com";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 登录
    public static void testLogin() {
        String urlStr = "http://localhost:8080/youzhu/account/login";
        String paramStr = "mobile=13122069352&password=e10adc3949ba59abbe56e057f20f883e";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 获取省份
    public static void testProvince() {
        String urlStr = "http://localhost:8080/youzhu/common/getProvinceList";
        // String paramStr = "mobile=13476107753&password=1234567";
        String str = WebserviceUtil.post(urlStr, "");
        System.out.println(str);
    }

    // 获取城市
    public static void testCity() {
        String urlStr = "http://192.168.59.128:8080/youzhu/common/getCityList";
        String paramStr = "provinceId=1";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 获取学校
    public static void testSchool() {
        String urlStr = "http://localhost:8080/youzhu/person/school";
        String paramStr = "pageSize=50&pageNum=1&cityId=176&level=1&provinceId=10";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 获取学校
    public static void testYears() {
        String urlStr = "http://192.168.59.128:8080/youzhu/person/year";
        // String paramStr = "provinceId=1&&searchName=武汉&pageSize=5&pageNum=1";
        String str = WebserviceUtil.post(urlStr, "");
        System.out.println(str);
    }

    // 获取学校
    public static void testInterest() {
        String urlStr = "http://192.168.59.128:8080/youzhu/person/interest";
        // String paramStr = "provinceId=1&&searchName=武汉&pageSize=5&pageNum=1";
        String str = WebserviceUtil.post(urlStr, "");
        System.out.println(str);
    }

    // 获取公告
    public static void testPlacard() {
        String urlStr = "http://localhost:8080/youzhu/placard/list";
        String paramStr = "pageSize=5&pageNum=1&schoolId=10593";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 获取详细公告
    public static void testPlacardDetail() {
        String urlStr = "http://192.168.59.128:8080/youzhu/placard/info";
        String paramStr = "placardId=27";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    /************************************************************/
    // 发起活动
    public static void testAdd() {
        String urlStr = "http://localhost:8080/youzhu/activity/add";
        long time = System.currentTimeMillis();
        String paramStr = "userId=556619&content=活动999&typeId=1&location=sss&startTime=" + time
                + "&endTime=" + time;
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 搜索活动
    public static void testSearch() {
        String urlStr = "http://localhost:8080/youzhu/activity/list";
        String paramStr = "pageNum=1&pageSize=10&userId=556721";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 搜索活动BY ID 
    public static void testSearchById() {
        String urlStr = "http://localhost:8080/youzhu/activity/info";
        String paramStr = "activityId=952";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 评论活动
    public static void testReply() {
        String urlStr = "http://localhost:8080/youzhu/activity/replay";
        String paramStr = "activityId=11&userId=556612&content=评论888888";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 收藏活动
    public static void testFavorite() {
        String urlStr = "http://localhost:8080/youzhu/activity/favorite";
        String paramStr = "activityId=10&userId=18";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 参与活动
    public static void testApply() {
        String urlStr = "http://180.169.58.224:8085/youzhu/activity/apply";
        String paramStr = "activityId=937&userId=556633";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 评论列表
    public static void testCommentList() {
        String urlStr = "http://localhost:8080/youzhu/activity/replayList";
        String paramStr = "userId=556620&activityId=117&pageNum=1&pageSize=10";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 参与人员列表
    public static void testApplyList() {
        String urlStr = "http://180.169.58.224:8085/youzhu/activity/applyList";
        String paramStr = "activityId=937&pageNum=1&pageSize=10&status=0";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 参与人员审核
    public static void testApplyUser() {
        String urlStr = "http://192.168.59.128:8080/youzhu/activity/applyUser";
        String paramStr = "activityId=10&status=1&userId=18";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 操作爱心币
    public static void testCoinOperate() {
        String urlStr = "http://192.168.59.128:8080/youzhu/person/coinOperate";
        String paramStr = "userId=18&operate=1&coin=10";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    // 个人资料
    public static void testInfo() {
        String urlStr = "http://localhost:8080/youzhu/person/info";
        String paramStr = "userId=556729";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }

    public static void testActDetail() {
        String urlStr = "http://192.168.59.128:8080/youzhu/account/regist";
        String paramStr = "mobile=13476107754&password=1234566";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);

    }

    // 获取过滤列表
    public static void testFiltrationList() {
        String urlStr = "http://localhost:8080/youzhu/filtration/getFiltrationList";
        String str = WebserviceUtil.post(urlStr, null);
        System.out.println(str);
    }

    // 新增举报信息
    public static void testCreateReport() {
        String urlStr = "http://localhost:8080/youzhu/report/create";
        String paramStr = "userId=1&content=sdfsdf";
        String str = WebserviceUtil.post(urlStr, paramStr);
        System.out.println(str);
    }
}
