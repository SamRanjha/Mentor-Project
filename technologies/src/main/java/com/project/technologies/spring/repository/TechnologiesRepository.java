package com.project.technologies.spring.repository;

import com.project.technologies.spring.entity.Technologies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface TechnologiesRepository extends PagingAndSortingRepository<Technologies,Long> {

    public List<Technologies> findByName(String name);
}
