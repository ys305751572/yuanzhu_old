package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * groupscale表
 * 
 * @author mew
 * 
 */
public class Groupscale implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8780105791182861058L;
    private java.lang.Integer id; // 群规模编号
    private java.lang.String scale; // 群规模，默认为500

    /**
     * 获取群规模编号属性
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 设置群规模编号属性
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取群规模，默认为500属性
     * 
     * @return scale
     */
    public java.lang.String getScale() {
        return scale;
    }

    /**
     * 设置群规模，默认为500属性
     * 
     * @param scale
     */
    public void setScale(java.lang.String scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Groupscale");
        sb.append("{id=").append(id);
        sb.append(", scale=").append(scale);
        sb.append('}');
        return sb.toString();
    }
}