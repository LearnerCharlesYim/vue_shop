package com.charles.repository;

import com.charles.entity.QSysUser;
import com.charles.entity.Role;
import com.charles.entity.SysUser;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Arrays;

@SpringBootTest
public class SysUserRepositoryTest {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void saveTest(){
        for (int i = 0; i < 60; i++) {
            SysUser user = new SysUser();
            user.setEmail("admin3"+i+"@gmail.com");
            user.setPassword(bCryptPasswordEncoder.encode("111111"));
            user.setPhone("18888888888");
            user.setState(true);
            user.setUsername("用户"+i);
            sysUserRepository.save(user);
        }

    }

    @Test
    @Transactional
    @Modifying
    @Rollback(value = false)
    public void test222(){
        QSysUser u = QSysUser.sysUser;
        long rows = jpaQueryFactory.update(u)
                .set(u.password, bCryptPasswordEncoder.encode("111111"))
                .where(u.id.eq(5))
                .execute();
        System.out.println(rows);
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
