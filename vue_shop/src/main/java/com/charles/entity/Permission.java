package com.charles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Permission extends BaseEntity{
    private String authName;
    private Integer priority;
    private String path;
    private Integer level;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="parent_id",insertable = false,updatable = false)
    private Permission parent;

    @JsonIgnore
    @Column(name = "parent_id")
    private Integer parentId;

    @OneToMany(mappedBy = "parent")
    private List<Permission> children;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Permission getParent() {
        return parent;
    }

    public void setParent(Permission parent) {
        this.parent = parent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", deleted=" + deleted +
                ", authName='" + authName + '\'' +
                ", priority=" + priority +
                ", path='" + path + '\'' +
                ", level=" + level +
                ", parentId=" + parentId +

                '}';
    }
}
