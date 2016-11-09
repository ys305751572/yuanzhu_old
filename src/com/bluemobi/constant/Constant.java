package com.bluemobi.constant;

import java.io.File;

public class Constant {

    // 环信username前缀
    public final static String EASEMOB_ = "easemob_";
    //群默认爱心币数量
    public final static int GROUP_DEFAULT_COIN = 0;
    /** 当前用户 */
    public final static String CURRENT_USER = "currentUser";
    /** 网站当前用户 */
    public final static String WEB_CURRENT_USER = "webcurrentUser";
    /** 当前验证码 */
    public final static String CURRENT_USER_VALIDATE_CODE_KEY = "CURRENT_USER_VALIDATE_CODE_KEY";
    
    public final static String ACCESS_KEY = "gNG6A6WSrom-vGQtQQiCZQ1uDhQyt9Ode7Z9ewn_";
    /**
     * 在线状态
     */
    public final static int ONLINE = 0; //在线
    public final static int OFFLINE = 1;//离线
    
    /**
     * 删除标记
     */
    public final static String NOTCUT = "0";// 未删除
    public final static String CUT = "1";// 删除
    /**
     * 性别
     */
    public final static String MAN = "0";// 男
    public final static String WOMAN = "1";// 女
    /**
     * 审核状态
     */
    public final static String PASS = "0";// 待审核
    public final static String WAIT = "2";// 审核通过
    public final static String NOTPASS = "1";// 审核未通过\
    public final static String ING = "3";//正在审核
    /**
     * 用户类型
     */
    public final static String CLERK = "0";// 店员、
    public final static String ADMIN = "1";// 管理员
    public final static String INSIDER = "2";// 会员
    public final static String CUSTOMER = "3";// 用户
    
    /**
     * 爱心币发放
     */
    public final static int COIN200 = 200;// 学生证认证发放的爱心币
    public final static int COIN500 = 500;// 他人协助人认证发放的爱心币
    
    /**
     * 服务器URL路劲
     */
    public final static String SERVER_URL = "http://192.168.1.150:8080/luolai/";

    /** 接口请求状态 */
    // 成功
    public final static String STATUS_OK = "0";
    // 失败
    public final static String STATUS_KO = "1";

    /** 请求成功 */
    public final static String MSG_OK = "";
    /** **********************错误信息 ************************/
    /** 系统错误 */
    public final static String ERROR_01 = "error_01";
    /** 注册账号已存在 */
    public final static String ERROR_02 = "error_02";
    /** 用户名不存在 */
    public final static String ERROR_03 = "error_03";
    /** 账号名或密码错误 */
    public final static String ERROR_04 = "error_04";
    /** 数据不存在 */
    public final static String ERROR_05 = "error_05";
    /** 更新失败 */
    public final static String ERROR_06 = "error_06";
    /** 没有查询到数据 */
    public final static String ERROR_07 = "error_07";
    /** 新增失败 */
    public final static String ERROR_08 = "error_08";
    /** 手机号重复 */
    public final static String ERROR_09 = "error_09";
    /** 用户已收藏 */
    public final static String ERROR_10 = "error_10";
    /** 没有活跃用户 */
    public final static String ERROR_11 = "error_11";
    /** 爱心币不足 */
    public final static String ERROR_12 = "error_12";
    /** 未到发放福利日期*/
    public final static String ERROR_13 = "error_13";
    /** 注册次数太多，请稍后再试 */
    public final static String ERROR_14 = "error_14";
    /** 验证码错误  */
    public final static String ERROR_15 = "error_15";
    /** 任务已被接单 */
    public final static String ERROR_16 = "error_16";
    /** 活动人数已达上限 */
    public final static String ERROR_17 = "error_17";
    /** 您已参加过此活动 */
    public final static String ERROR_18 = "error_18";
    /** 该任务已被抢单 */
    public final static String ERROR_19 = "error_19";
    /** 该任务已失效 */
    public final static String ERROR_20 = "error_20";
    
	/** 默认头像路径 */
	public final static String DEFUALT_HEAD = "";
	
	/** 默认活动路径 */
	public final static String DEFUALT_ACT_HEAD = "";
	
	/**  */
	public final static Integer PERFECTINFO_SUCCESS = 1;
	public final static Integer PERFECTINFO_ERROR = 0;
	/** 配置文件地址 */
	public final static String COIN_PROP = Constant.class.getResource("/").getPath() + "resource" + File.separator + "coin.properties";
	public final static String SMS_PROP = Constant.class.getResource("/").getPath() + "resource" + File.separator + "sms.properties";
	public final static String HUANXIN_PROP = Constant.class.getResource("/").getPath() + "resource" + File.separator + "huanxin.properties";
	
	public final static String JDBC_PROP = Constant.class.getResource("/").getPath() + "resource" + File.separator + "jdbc.properties";
	
	
	//***************** cron 表达式 ******************/
	public final static String CLEAR_SIGN_CRON = "0 59 23 28 * ?"; // 清除每月签到  每月最后一天晚上12点
	public final static String DAY_CLEAR_SIGN_CRON = "0 59 23 * * ?"; //清除每天签到 每天凌晨
//	public final static String ISSUE_SIGN_REWARD = "0 59 23 14,27 * ?"; //发放福利 每月15号 
	
	//********************充值记录类型*************************/
	public final static String TOPUP = "充值"; // 充值
	public final static String REGISTER = "注册"; // 注册
	public final static String COMPLETE = "完善个人资料"; // 完善资料
	public final static String CARD = "完善实名认证"; // 个人认证
	public final static String ASSIST= "帮助完成a实名认证"; // 协助认证
	
	//******************二维码类型*******************/ 
	public final static int QRCODE_PERSON = 0; // 个人二维码
	public final static int QRCODE_GROUP = 1;  // 群二维码
	public final static int QRCODE_ACT = 2; // 活动二维码
	public final static int QRCODE_GIFT =3; // 礼品二维码
	
	//=========================任务状态=============================
	/** 待接单 */
	public final static int TASK_NO_ACCEPT = 0;
	/** 已接单  */
	public final static int TASK_ACCEPT = 1;
	/** 待评价  */
	public final static int TASK_WAIT_COMMET = 2;
	/** 已评价  */
	public final static int TASK_FINISH = 3;
	/** 已过期  */
	public final static int TASK_END = 4;
	/** 已失效   */
	public final static int TASK_FAILURE = 5;
	
	// ===========================积分值==========================
	
	/**  发起人获得的积分 */
	public final static int INTEGRAL_RELEASE = 1;
	
	/** 接单人 获得的积分  */
	public final static int INTEGRAL_ACCEPT = 1;
	
	/**  发起人获得的积分 */
	public final static int INTEGRAL_RELEASE_DEDUCT = 1;
	
	/** 接单人 获得的积分  */
	public final static int INTEGRAL_ACCEPT_DEDUCT = 1;
}
