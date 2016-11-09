package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * acttype表
 * 
 * @author mew
 * 
 */
public class Acttype implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1648298196109318267L;
    private java.lang.Integer id; // 活动类型编号
    private java.lang.String type; // 活动类型名称

    /**
     * 获取活动类型编号属性
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 设置活动类型编号属性
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取活动类型名称属性
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }

    /**
     * 设置活动类型名称属性
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Acttype");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}