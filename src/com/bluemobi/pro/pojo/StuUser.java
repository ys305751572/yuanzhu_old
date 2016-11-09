package com.bluemobi.pro.pojo;

import org.apache.commons.lang.StringUtils;

import com.bluemobi.constant.Constant;
import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 
 * <p>Title: StuUser.java</p> 
 * <p>Description: 移动端用户POJO</p> 
 * @author yesong 
 * @date 2014-11-13 下午03:24:02
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class StuUser extends BaseEntity{

	private static final long serialVersionUID = -3191318952462044921L;
	
	private int id; //ID
	private String nickname; //昵称
	private String name;//姓名
	private String password ;//密码
	private String mobile;//手机
	private String email;//邮箱
	private String birthday;//生日
	private String sex;//性别
	private String startYear;//入学年份
	private int provinceId;
	private int cityId;
	private int schoolId;//学校编号
	private int collegeId;//学院编号
	private int specialtyId;//专业编号
	private String status;//认证状态，0=未认证，1=认证未通过，2=认证通过，默认为0
	private int recUserId;//推荐人编号
	private String head;//头像url
	private String word;//签名
	private String coverPic;//学生证封面照片
	private String contentPic;//学生证内容照片
	private int coin;//爱心币
	private String isOnline;//用户在线状态，0=在线，1=离线
	
	private String school;
	
	private String college;
	
	public StuUser(){}
	
	public StuUser(String mobile,String password) {
		this.mobile = mobile;
		this.password = password;
	}
	
	public StuUser(String mobile,String password,String nickname){
		this.mobile = mobile;
		this.password = password;
		this.nickname = nickname;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHead() {
		head = StringUtils.isNotBlank(this.head) ? this.head : Constant.DEFUALT_HEAD;
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getCoverPic() {
		return coverPic;
	}
	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}
	public String getContentPic() {
		return contentPic;
	}
	public void setContentPic(String contentPic) {
		this.contentPic = contentPic;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}

	public int getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(int specialtyId) {
		this.specialtyId = specialtyId;
	}

	public int getRecUserId() {
		return recUserId;
	}

	public void setRecUserId(int recUserId) {
		this.recUserId = recUserId;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	
	
}
