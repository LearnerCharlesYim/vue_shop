package com.charles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

//@JsonIgnoreProperties(value = "hibernateLazyInitializer")
@Entity
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roleName;
    private String roleDesc;

    @ManyToMany(targetEntity = Permission.class,cascade = CascadeType.PERSIST)
    @JoinTable(name="role_permission"
            , joinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name="permission_id",referencedColumnName = "id"))
    private List<Permission> permissions;

    //@OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="role_id")
    @JsonIgnore
    private List<SysUser> users;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SysUser> getUsers() {
        return users;
    }

    public void setUsers(List<SysUser> users) {
        this.users = users;
    }
}
