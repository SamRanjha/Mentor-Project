package com.project.search.spring.repository;

import com.project.search.spring.entity.Mentor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface MentorRepository extends PagingAndSortingRepository<Mentor,Long> {

}
