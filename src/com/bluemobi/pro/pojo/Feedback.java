package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * feedback表
 * 
 * @author mew
 * 
 */
public class Feedback implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6002139185220086432L;
    private java.lang.Integer id; // 反馈编号
    private java.lang.String content; // 反馈内容
    private java.lang.String backtime; // 反馈时间
    private java.lang.Integer stuuserid; // 移动端用户编号
    private java.lang.String status; // 审核状态，1=未处理，2=已处理

    /**
     * 获取反馈编号属性
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 设置反馈编号属性
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取反馈内容属性
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }

    /**
     * 设置反馈内容属性
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }

    /**
     * 获取反馈时间属性
     * 
     * @return backtime
     */
    public java.lang.String getBacktime() {
        return backtime;
    }

    /**
     * 设置反馈时间属性
     * 
     * @param backtime
     */
    public void setBacktime(java.lang.String backtime) {
        this.backtime = backtime;
    }

    /**
     * 获取移动端用户编号属性
     * 
     * @return stuuserid
     */
    public java.lang.Integer getStuuserid() {
        return stuuserid;
    }

    /**
     * 设置移动端用户编号属性
     * 
     * @param stuuserid
     */
    public void setStuuserid(java.lang.Integer stuuserid) {
        this.stuuserid = stuuserid;
    }

    /**
     * 获取审核状态，1=未处理，2=已处理属性
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }

    /**
     * 设置审核状态，1=未处理，2=已处理属性
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Feedback");
        sb.append("{id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", backtime=").append(backtime);
        sb.append(", stuuserid=").append(stuuserid);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}