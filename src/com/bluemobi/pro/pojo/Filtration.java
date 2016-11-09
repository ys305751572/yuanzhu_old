package com.bluemobi.pro.pojo;

import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 
 * filtration表
 * 
 * @author tutu
 * 
 */
public class Filtration extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 5751526827480606415L;
    private java.lang.Integer id; // 关键词编号
    private java.lang.String content; // 关键词内容
    private java.lang.Integer userid; // 创建人编号
    private java.lang.String time; // 创建时间

    /**
     * 获取关键词编号属性
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 设置关键词编号属性
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取关键词内容属性
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }

    /**
     * 设置关键词内容属性
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }

    /**
     * 获取创建人编号属性
     * 
     * @return userid
     */
    public java.lang.Integer getUserid() {
        return userid;
    }

    /**
     * 设置创建人编号属性
     * 
     * @param userid
     */
    public void setUserid(java.lang.Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取创建时间属性
     * 
     * @return time
     */
    public java.lang.String getTime() {
        return time;
    }

    /**
     * 设置创建时间属性
     * 
     * @param time
     */
    public void setTime(java.lang.String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Filtration");
        sb.append("{id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", userid=").append(userid);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}