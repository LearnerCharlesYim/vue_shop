package com.charles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class GoodsAttrs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attrId;
    private String attrValue;
    private Integer addPrice;
    private String attrName;
    private Boolean attrStatic;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Goods _goods;

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public Integer getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(Integer addPrice) {
        this.addPrice = addPrice;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Boolean getAttrStatic() {
        return attrStatic;
    }

    public void setAttrStatic(Boolean attrStatic) {
        this.attrStatic = attrStatic;
    }

    public Goods get_goods() {
        return _goods;
    }

    public void set_goods(Goods _goods) {
        this._goods = _goods;
    }

    @Override
    public String toString() {
        return "GoodsAttrs{" +
                "attrId=" + attrId +
                ", attrValue='" + attrValue + '\'' +
                ", addPrice=" + addPrice +
                ", attrName='" + attrName + '\'' +
                ", attrStatic=" + attrStatic +
                '}';
    }
}


