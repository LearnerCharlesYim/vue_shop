package com.charles.repository;

import com.charles.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SysUserRepositoryTest {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private RoleRepository roleRepository;

    @Test
    public void saveTest(){
        SysUser user = new SysUser();
        user.setId(-1);
        user.setEmail("admin2@gmail.com");
        user.setUsername("admin2");
        user.setPhone("18888888888");
        user.setPassword("111111");
        user.setState(true);
        user.setRole(roleRepository.getOne(2));
        sysUserRepository.save(user);
    }
}
