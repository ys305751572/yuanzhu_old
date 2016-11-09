package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * grouptype表
 * 
 * @author mew
 * 
 */
public class Grouptype implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4396591818910519003L;
    private java.lang.Integer id; // 群类型编号
    private java.lang.String name; // 群类型名称

    /**
     * 获取群类型编号属性
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 设置群类型编号属性
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取群类型名称属性
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * 设置群类型名称属性
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Grouptype");
        sb.append("{id=").append(id);
        sb.append(", name=").append(name);
        sb.append('}');
        return sb.toString();
    }
}