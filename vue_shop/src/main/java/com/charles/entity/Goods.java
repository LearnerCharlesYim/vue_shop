package com.charles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Goods extends BaseEntity{
    private String name;
    private Double price;
    private Double weight;
    private Boolean state;
    private Integer num;
    @Lob
    private String introduction;

    //@OneToMany(mappedBy = "goods",cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="goods_id")
    private List<Picture> pictures;

    //@OneToMany(mappedBy = "_goods",cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="_goods_id")
    private List<GoodsAttrs> goodsAttrs;


    @JsonIgnore
    @ManyToMany(targetEntity = Category.class,cascade = CascadeType.PERSIST)
    @JoinTable(name="goods_category"
            , joinColumns = @JoinColumn(name="goods_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name="category_id",referencedColumnName = "id"))
    private List<Category> categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<GoodsAttrs> getGoodsAttrs() {
        return goodsAttrs;
    }

    public void setGoodsAttrs(List<GoodsAttrs> goodsAttrs) {
        this.goodsAttrs = goodsAttrs;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


}
