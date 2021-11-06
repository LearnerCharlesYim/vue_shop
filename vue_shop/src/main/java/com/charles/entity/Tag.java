package com.charles.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Tag extends BaseEntity {
    private String value;
    @ManyToOne
    @JoinColumn(name = "param_id")
    private CategoryParam param;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CategoryParam getParam() {
        return param;
    }

    public void setParam(CategoryParam param) {
        this.param = param;
    }
}
