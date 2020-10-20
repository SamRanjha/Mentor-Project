package com.project.User.spring.repository;

import com.project.User.spring.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface UserRepository extends PagingAndSortingRepository<User,Long> {

}
