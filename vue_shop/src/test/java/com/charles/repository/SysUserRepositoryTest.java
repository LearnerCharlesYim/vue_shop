package com.charles.repository;

import com.charles.entity.QSysUser;
import com.charles.entity.Role;
import com.charles.entity.SysUser;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class SysUserRepositoryTest {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Test
    public void saveTest(){
        SysUser user = new SysUser();
        user.setId(-1);
        user.setEmail("admin3@gmail.com");
        user.setUsername("admin3");
        user.setPhone("18888888888");
        user.setPassword("111111");
        user.setState(true);
        user.setRole(roleRepository.getOne(3));
        SysUser sysUser = sysUserRepository.save(user);
        System.out.println(sysUser);
    }

    @Test
    public void test(){
        int pageNum = 1;
        int pageSize = 4;
        SysUser sysUser = new SysUser();
        QSysUser user = QSysUser.sysUser;
        JPAQuery<SysUser> query = jpaQueryFactory.select(user).from(user);
        if (!StringUtils.isEmpty(sysUser.getUsername())) {
            query.where(user.username.like("%"+sysUser.getUsername()+"%"));
        }
        if(!StringUtils.isEmpty(sysUser.getEmail())){
            query.where(user.email.like("%"+sysUser.getEmail()+"%"));
        }
        QueryResults<SysUser> results = query.orderBy(user.createdTime.desc())
                .offset((pageNum - 1) * pageSize)
                .limit(pageSize)
                .fetchResults();
//        System.out.println(results.getResults());
        System.out.println(query.fetchCount());
    }

    @Test
    public void test11(){
        SysUser user1 = new SysUser();
        SysUser user2 = new SysUser();
        //user.setId(-1);
        user1.setUsername("翻江倒海777");
        user2.setUsername("龙卷雨击7777");

        Role role = new Role();
        //role.setId(-1);
        role.setRoleName("龙宫777");
        role.setUsers(Arrays.asList(user1,user2));

        roleRepository.save(role);

    }
}
