package com.charles.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "role")
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
