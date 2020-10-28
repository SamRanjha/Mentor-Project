package com.project.User.spring.service;

import com.project.User.spring.entity.User;
import com.project.User.spring.model.UserDTO;
import com.project.User.spring.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImp implements UserService {
    private static final String REGISTRATION_URL = "http://localhost:9009/register";
    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());


    public void createUser(User user){
        UserDTO u = new UserDTO();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        System.out.println(u.getUsername());
        ResponseEntity<Boolean> r = restTemplate.postForEntity(REGISTRATION_URL,u,Boolean.class);
        if(r == null){
            logger.error("USER name already taken");
            return;
        }
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
