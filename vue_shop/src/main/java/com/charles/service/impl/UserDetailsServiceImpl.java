package com.charles.service.impl;

import com.charles.entity.Permission;
import com.charles.entity.SysUser;
import com.charles.service.SysUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //需要构造出 org.springframework.security.core.userdetails.User 对象并返回
        /*
         *         String username：用户名
         *         String password： 密码
         *         boolean enabled： 账号是否可用
         *         boolean accountNonExpired：账号是否过期
         *         boolean credentialsNonExpired：密码是否过期
         *         boolean accountNonLocked：账号是否锁定
         *         Collection<? extends GrantedAuthority> authorities) ：用户权限列表
         */

        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        SysUser sysUser = sysUserService.findByUsername(username);
        if (sysUser == null) {
            throw new RuntimeException("用户名不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Permission> permissions = sysUserService.getPermissionList(sysUser);
        if (permissions != null) {
            permissions.forEach(permission -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getAuthName());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        System.out.println("用户名: " + sysUser.getUsername() + " \n拥有权限:" + grantedAuthorities);
        return new User(sysUser.getUsername()
                , sysUser.getPassword()
                , grantedAuthorities);
    }
}
