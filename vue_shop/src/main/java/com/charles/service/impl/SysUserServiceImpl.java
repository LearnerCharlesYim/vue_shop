package com.charles.service.impl;

import com.charles.dto.UserDto;
import com.charles.dto.UserListDto;
import com.charles.entity.Permission;
import com.charles.mapper.UserMapper;
import com.charles.entity.QSysUser;
import com.charles.entity.Role;
import com.charles.entity.SysUser;
import com.charles.repository.RoleRepository;
import com.charles.repository.SysUserRepository;
import com.charles.service.SysUserService;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Resource
    private SysUserRepository sysUserRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserListDto findByConditionAndPage(SysUser sysUser,Integer pageNum, Integer pageSize) {
        QSysUser user = QSysUser.sysUser;
        JPAQuery<SysUser> query = jpaQueryFactory.select(user).from(user);
        if (!StringUtils.isEmpty(sysUser.getUsername())) {
            query.where(user.username.like("%"+sysUser.getUsername()+"%"));
        }
        if(!StringUtils.isEmpty(sysUser.getEmail())){
            query.where(user.email.like("%"+sysUser.getEmail()+"%"));
        }
        long totalCount = query.fetchCount();//筛选后总数据
        List<SysUser> users = query.orderBy(user.createdTime.desc())
                .offset((long) (pageNum - 1) * pageSize)
                .limit(pageSize)
                .fetchResults()
                .getResults();

        List<UserDto> userDtoList = new ArrayList<>();
        for (SysUser item : users) {
            UserDto userDto = UserMapper.INSTANCES.toUserDto(item);
            userDtoList.add(userDto);
        }
        UserListDto userListDto = new UserListDto();
        userListDto.setUsers(userDtoList);
        userListDto.setPageNum(pageNum);
        userListDto.setPageSize(pageSize);

        userListDto.setTotalPage(
                (int) (totalCount % pageSize == 0 ? (totalCount / pageSize) :(totalCount / pageSize + 1 ))
        );
        userListDto.setTotalNum(totalCount);
        return userListDto;
    }

    @Override
    public UserDto save(SysUser sysUser) {
        //加密
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
        SysUser user = sysUserRepository.save(sysUser);
        return UserMapper.INSTANCES.toUserDto(user);
    }

    @Override
    @Transactional
    @Modifying
    public UserDto update(SysUser sysUser) {
        if(StringUtils.isEmpty(sysUser.getEmail()) && StringUtils.isEmpty(sysUser.getPhone())){
            SysUser one = sysUserRepository.getOne(sysUser.getId());
            return UserMapper.INSTANCES.toUserDto(one);
        }
        QSysUser user = QSysUser.sysUser;
        JPAUpdateClause update = jpaQueryFactory.update(user);
        if(!StringUtils.isEmpty(sysUser.getEmail())){
            update.set(user.email,sysUser.getEmail());
        }
        if(!StringUtils.isEmpty(sysUser.getPhone())){
            update.set(user.phone,sysUser.getPhone());
        }
        update.where(user.id.eq(sysUser.getId())).execute();
        SysUser one = sysUserRepository.getOne(sysUser.getId());
        return UserMapper.INSTANCES.toUserDto(one);
    }

    @Override
    @Transactional
    @Modifying
    public void delete(Integer id) {
        sysUserRepository.deleteById(id);
    }

    @Override
    public UserDto setRole(Integer id, Integer roleId) {
        SysUser user = sysUserRepository.getOne(id);
        Role role = roleRepository.getOne(roleId);
        user.setRole(role);
        SysUser sysUser = sysUserRepository.save(user);
        return UserMapper.INSTANCES.toUserDto(sysUser);
    }

    @Override
    public UserDto setState(Integer id , Boolean type) {
        SysUser sysUser = sysUserRepository.getOne(id);
        sysUser.setState(type);
        SysUser user = sysUserRepository.save(sysUser);
        return UserMapper.INSTANCES.toUserDto(user);
    }

    @Override
    public UserDto findOneById(Integer id) {
        SysUser sysUser = sysUserRepository.getOne(id);
        return UserMapper.INSTANCES.toUserDto(sysUser);
    }

    @Override
    public SysUser findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    @Override
    public List<Permission> getPermissionList(SysUser sysUser) {
        Role role = sysUser.getRole();
        if(role == null){
            return null;
        }
        return role.getPermissions();
    }
}
