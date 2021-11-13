package com.charles.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CategoryParam extends BaseEntity {
    private String param;
    private Boolean isStatic;
    private String tag;

    @ManyToOne
    @JoinColumn
    private Category cate;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Boolean getIsStatic() {
        return isStatic;
    }

    public void setIsStatic(Boolean IsStatic) {
        isStatic = IsStatic;
    }

    public Category getCate() {
        return cate;
    }

    public void setCate(Category cate) {
        this.cate = cate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "CategoryParam{" +
                "id=" + id +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", deleted=" + deleted +
                ", param='" + param + '\'' +
                ", isStatic=" + isStatic +
                ", tag='" + tag + '\'' +
                '}';
    }
}
