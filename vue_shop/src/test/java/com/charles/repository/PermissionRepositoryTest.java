package com.charles.repository;

import com.charles.dto.MenuDto;
import com.charles.mapper.MenuMapper;
import com.charles.entity.Permission;
import com.charles.entity.QPermission;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;


@SpringBootTest
public class PermissionRepositoryTest {

    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Resource
    private EntityManager em;

    @Test
    public void saveTest() {
        Permission p = new Permission();
        p.setAuthName("查看数据");
        p.setLevel(3);
        p.setPath("reports");
        p.setParentId(38);
        permissionRepository.save(p);
    }

    @Test
    public void test() {
        QPermission permission = QPermission.permission;
        List<Permission> permissions = jpaQueryFactory.selectFrom(permission)
                .where(
                        permission.level.eq(1)
                )
                .fetch();
        System.out.println(permissions);
    }

    @Test
    public void test3() {
        QPermission p1 = new QPermission("p1");
        QPermission p2 = new QPermission("p2");

        List<Tuple> fetch = jpaQueryFactory.select(p1.authName, p1.path, p1.priority, p2.authName, p2.path, p2.priority)
                .from(p1)
                .leftJoin(p2)
                .on(p1.id.eq(p2.parentId))
                .where(p1.level.eq(1))
                .fetch();

    }

    @Test
    public void test4(){
        List<Permission> permissions = permissionRepository.findByLevelOrderByPriorityAsc(1);
        Permission permission = permissions.get(1);
        MenuDto menuDto = MenuMapper.INSTANCES.toMenuDto(permission);
        System.out.println(menuDto);

    }
}
