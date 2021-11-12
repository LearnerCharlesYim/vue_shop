package com.charles.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category extends BaseEntity{
    private String name;
    private Integer level;
    private Boolean state;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="parent_id",insertable = false,updatable = false)
    private Category parent;

    @Column(name = "parent_id")
    private Integer parentId;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;

    @ManyToMany(mappedBy = "categories")
    private List<Goods> goodsList;

    @OneToMany(mappedBy = "cate")
    private List<CategoryParam> params;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public List<CategoryParam> getParams() {
        return params;
    }

    public void setParams(List<CategoryParam> params) {
        this.params = params;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
