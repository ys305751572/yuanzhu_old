package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * province表
 * 
 * @author mew
 * 
 */
public class Province implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2685951243852276000L;
    private java.lang.Integer id; // 主键
    private java.lang.String name; // 名称
    private java.lang.String brevitycode; // 简码

    /**
     * 获取主键属性
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 设置主键属性
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取名称属性
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * 设置名称属性
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 获取简码属性
     * 
     * @return brevitycode
     */
    public java.lang.String getBrevitycode() {
        return brevitycode;
    }

    /**
     * 设置简码属性
     * 
     * @param brevitycode
     */
    public void setBrevitycode(java.lang.String brevitycode) {
        this.brevitycode = brevitycode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Province");
        sb.append("{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", brevitycode=").append(brevitycode);
        sb.append('}');
        return sb.toString();
    }
}