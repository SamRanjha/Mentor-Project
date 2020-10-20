package com.project.User.spring.service;

import com.project.User.spring.entity.User;
import com.project.User.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    public void createUser(User user){
        userRepository.save(user);
    }

    public List<User> getTech(){
        return (List<User>) userRepository.findAll();

    }

    public User findById(long id){
        return userRepository.findById(id).get();
    }

    public User update(User tech){
        return userRepository.save(tech);
    }

    public void delete(long id){
        userRepository.deleteById(id);
    }

    public User update(long id, boolean status){
        User tech = userRepository.findById(id).get();
        tech.setActive(status);
        return userRepository.save(tech);
    }

    public List<User> getAllTechs(Integer pageNo, Integer pageSize, String sortBy)
    {
        Sort s = Sort.by(sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize,s);

        Page<User> pagedResult = userRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<User>();
        }
    }

   }
