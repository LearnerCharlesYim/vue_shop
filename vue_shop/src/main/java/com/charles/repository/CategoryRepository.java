package com.charles.repository;

import com.charles.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

    @Query(value = "select * from category  where level = 1 order by created_time DESC limit ?1,?2",nativeQuery = true)
    List<Category> findCategoriesByLevelAndPage(int offset,int pageSize);

    @Query(value = "select count(*) from category where level = 1",nativeQuery = true)
    Integer getCountByLevel();
}
