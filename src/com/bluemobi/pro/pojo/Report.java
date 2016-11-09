package com.bluemobi.pro.pojo;

import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 
 * report表
 * 
 * @author tutu
 * 
 */
public class Report extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -1725683540949454016L;
    private java.lang.Integer id; // 举报编号
    private java.lang.String content; // 举报内容
    private java.lang.Integer userid; // 举报人编号
    private java.lang.String time; // 举报时间

    /**
     * 获取举报编号属性
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 设置举报编号属性
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取举报内容属性
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }

    /**
     * 设置举报内容属性
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }

    /**
     * 获取举报人编号属性
     * 
     * @return userid
     */
    public java.lang.Integer getUserid() {
        return userid;
    }

    /**
     * 设置举报人编号属性
     * 
     * @param userid
     */
    public void setUserid(java.lang.Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取举报时间属性
     * 
     * @return time
     */
    public java.lang.String getTime() {
        return time;
    }

    /**
     * 设置举报时间属性
     * 
     * @param time
     */
    public void setTime(java.lang.String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Report");
        sb.append("{id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", userid=").append(userid);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}