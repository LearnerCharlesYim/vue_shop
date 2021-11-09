package com.charles.repository;

import com.charles.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser,Integer> {
    SysUser findByUsername(String username);
}
