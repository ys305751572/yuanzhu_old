package com.bluemobi.utils;

/**
 * 接口测试类（交流模块）
 * @author gaolei
 */
public class TestWebservice {
	// localhost		http://localhost:8080/youzhu
	private static String LOCALHOST = "http://localhost:8080/yuanzhu";
	// 上海服务器地址	http://180.169.58.224:8085/youzhu
	private static String SH_LINUX = "http://180.169.58.224:8085/youzhu";
	//武汉linux服务器 	http://192.168.59.128:8080/youzhu
	private static String WUHAN_LINUX = "http://10.58.168.169:8080/youzhu";
	private static String SERVER_URL = LOCALHOST;
	
	public static void main(String[] args) {
		//6.1查询个人详细信息
//		getPersonDetail(111111);
		//6.2查询好友列表
//		getFriendList(556715,1,10);
//		6.3查询用户列表
//		getUserList(1,10);
		//6.4查询群列表
//		getGroupList();
		//6.5创建群
//		createGroup();
		//6.6解散群
		deleteGroup("1432190063659362");
		//6.7查询群详细信息
//		getGroupDetail("1421492112568694","123456");
		//6.8查询群成员信息
//		getGroupMembers("141636150431513",1,10);
		//6.9群公告编辑
//		editNotice("141636150431513","gggggggggggg");
		//6.10爱心币发放
//		sendMoney("141636150431513","111111",300);
		//6.11添加好友
//		addFriend("888","999");
		//6.12删除好友
//		deleteFriend("888","999");
		//6.16群类型查询
//		getGroupTypeList();
//		sendCoinToGroup();
		
		
	}
	
	/**
	 * 发放爱心币
	 * @author gaolei
	 */
	public static void sendMoney2Friend() {
		String regURL = LOCALHOST + "/person/sendCoinToFriend?userId=556817&friendId=556816&coin=100";
		String result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	
	private static void sendCoinToGroup() {
		// TODO Auto-generated method stub
		 String result = "";
	        String regURL = LOCALHOST + "/group/sendCoinToGroup?userId=556816&groupId=142341150191843&coin=500";
	        result = WebserviceUtil.post(regURL);
	        System.out.println(result);
	}

	/**
	 * 
	 * @author gaolei
	 */
	public static void getPersonDetail(int userId) {
        String result = "";
        String regURL = SERVER_URL + "/person/getDetail?userId=" + userId;
        result = WebserviceUtil.post(regURL);
        System.out.println(result);
    }
	
	/**
	 * 查询好友信息
	 * @param id
	 * @author 高磊磊
	 */
	public static void getFriendList(int userId,int pageNum,int pageSize) {
		String result = "";
		String regURL = SH_LINUX + "/friend/getList?userId=" + userId 
		+ "&pageNum=" + pageNum + "&pageSize=" + pageSize;
		result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 查用户列表
	 * @author gaolei
	 */
	public static void getUserList(int pageNum,int pageSize) {
		String result = "";
		String regURL = SERVER_URL+"/user/getList?pageNum=1";
		result = WebserviceUtil.post(regURL);
		
		System.out.println(result);
	}
	
	/**
	 *
	 * @param map 请求参数,参数如下：
	 *		pageNum
	 *		pageSize
	 * 调通：y
	 * @return json格式信息
	 * @author gaolei
	 */
	public static void getGroupList() {
		String result = "";
		String regURL = LOCALHOST+"/group/getList?pageNum=1&pageSize=1000000&userId=556817";
		result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 创建群
	 * @param id
	 * @author 高磊磊
	 */
	public static void createGroup() {
		//成功例子
		String regURL = "http://localhost:8080/yuanzhu/group/create?groupName=ddddddd&desc=groupdesc&__public=true&maxusers=200&approval=true&owner=easemob_123456&userId=123456"; 
//		String regURL = "http://localhost:8080/youzhu" + "/group/create"; 
//		String param = "groupName=测试测试&maxusers=300&owner=easemob_556613&userId=556613";
		String result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 解散群
	 * @author 高磊磊
	 */
	public static void deleteGroup(String groupId) {
		String result = "";
		String regURL = SERVER_URL +
			"/group/delete?groupId="+groupId;
		result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 查询群列表
	 * @param id
	 * @author 高磊磊
	 */
	public static void getGroupList(int userId,int pageNum,int pageSize) {
		String result = "";
		String regURL = SERVER_URL + "/group/getList?userId=" + userId 
		+ "&pageNum=" + pageNum + "&pageSize=" + pageSize;
		result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 查询群成员
	 * @param id
	 * @author 高磊磊
	 */
	public static void getGroupMembers(String groupId,int pageNum,int pageSize) {
		String result = "";
		String regURL = SERVER_URL + "/group/getMembers?groupId=" + groupId 
		+ "&pageNum=" + pageNum + "&pageSize=" + pageSize;
		result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 发放爱心币
	 * @author gaolei
	 */
	public static void sendMoney(String groupId,String userId,int coin) {
		String regURL = SERVER_URL + "/group/sendMoney?groupId=" + groupId 
		+ "&userId=" + userId + "&coin=" + coin;
		String result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 添加好友
	 * @author gaolei
	 */
	public static void addFriend(String owner,String friend) {
		String regURL = SERVER_URL + "/friend/add?owner=" + owner 
		+ "&friend=" + friend;
		String result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 删除好友
	 * @author gaolei
	 */
	public static void deleteFriend(String owner,String friend) {
		String regURL = SERVER_URL + "/friend/delete?owner=" + owner 
		+ "&friend=" + friend;
		String result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	/**
	 * 查看群详情
	 * @author gaolei
	 */
	public static void getGroupDetail(String groupId,String userId) {
		String regURL = LOCALHOST + "/group/getDetail?groupId=" + groupId + "&userId=" + userId;
		String result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	/**
	 * 编辑群公告
	 * @author gaolei
	 */
	public static void editNotice(String groupId,String notice) {
		String regURL = SERVER_URL + "/group/editNotice";
		String params = "groupId=" + groupId + "&notice=" + notice;
		String result = WebserviceUtil.post(regURL,params);
		System.out.println(result);
	}
	/**
	 * 获取群类型
	 * @author gaolei
	 */
	public static void getGroupTypeList() {
		String regURL = SERVER_URL + "/common/getGroupTypeList";
		String result = WebserviceUtil.post(regURL);
		System.out.println(result);
	}
	
	
	
	
}
