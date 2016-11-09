package com.bluemobi.pro.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.bluemobi.utils.DateUtils;

/**
 * 
 * coinlog表
 * 
 * @author gaolei
 * 
 */
public class Coinlog implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6555244723382136643L;
    private java.lang.Integer id; // id
    private java.lang.String groupId; // 群编号
    private int stuUserId; // 移动端用户编号
    private java.lang.Double money; // 金额
    private java.lang.Integer coin; // 爱心币
    private long createTime = System.currentTimeMillis(); // 创建时间
    private java.lang.String time;
    
    private String typeStr;//类型0:充值 1.注册 2.完善资料 3.认证 4.协助认证
    private String content ; // 记录内容
    
    private int status; //充值记录状态0:为成功,1:已成功

    public Coinlog() {}
    
    public Coinlog(int stuUserId,int coin,String typeStr){
    	this.stuUserId = stuUserId;
    	this.coin = coin;
    	this.typeStr = typeStr;
    }
    
    public Coinlog(int stuUserId,int coin,String typeStr,String content){
    	this.stuUserId = stuUserId;
    	this.coin = coin;
    	this.typeStr = typeStr;
    	this.content = content;
    }
    
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public String getContent() {
		if(StringUtils.isNotBlank(this.content)) {
			return this.content;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("您于")
			  .append(DateUtils.parse(createTime, "yyyy-MM-dd hh:mm:ss"))
			  .append(typeStr)
			  .append("获得平台奖励")
			  .append(coin)
			  .append("爱佑币");
		content = buffer.toString();
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.lang.String getTime() {
        return time;
    }

    public void setTime(java.lang.String time) {
        this.time = time;
    }

    public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    public java.lang.String getGroupId() {
        return groupId;
    }

    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
    }

    public int getStuUserId() {
        return stuUserId;
    }

    public void setStuUserId(int stuUserId) {
        this.stuUserId = stuUserId;
    }

    public java.lang.Double getMoney() {
		return money;
	}

	public void setMoney(java.lang.Double money) {
		this.money = money;
	}

	public java.lang.Integer getCoin() {
        return coin;
    }

    public void setCoin(java.lang.Integer coin) {
        this.coin = coin;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}