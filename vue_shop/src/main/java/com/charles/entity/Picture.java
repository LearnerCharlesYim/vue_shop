package com.charles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer picId;
    private String picBig;
    private String picMid;
    private String picSma;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Goods goods;

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicBig() {
        return picBig;
    }

    public void setPicBig(String picBig) {
        this.picBig = picBig;
    }

    public String getPicMid() {
        return picMid;
    }

    public void setPicMid(String picMid) {
        this.picMid = picMid;
    }

    public String getPicSma() {
        return picSma;
    }

    public void setPicSma(String picSma) {
        this.picSma = picSma;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
