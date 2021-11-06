package com.charles.repository;

import com.charles.dto.MenuDto;
import com.charles.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Integer>, JpaSpecificationExecutor<Permission>{
     List<Permission> findByLevelOrderByPriorityAsc(Integer level);

//    @Query(value = "select * from permission p1,permission p2 where p1.level=1 and p1.id = p2.parent_id",nativeQuery = true)
//    List<Permission> getMemuList();


}
