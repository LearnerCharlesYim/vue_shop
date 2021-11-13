package com.charles.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


public class MenuDto {
    private Integer id;
    private String authName;
    private String path;
    private Integer priority;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuDto> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<MenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDto> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuDto{" +
                "authName='" + authName + '\'' +
                ", path='" + path + '\'' +
                ", priority=" + priority +
                ", children=" + children +
                '}';
    }
}
