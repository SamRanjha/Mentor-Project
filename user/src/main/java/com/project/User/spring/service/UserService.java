package com.project.User.spring.service;


import com.project.User.spring.entity.User;

import java.util.List;


public interface UserService {
    public void createUser(User user);
    public User findById(long id);
    public User update(User tech);
    public void delete(long id);
    public List<User> getUsers();
    public User update(long id, boolean status);

}

