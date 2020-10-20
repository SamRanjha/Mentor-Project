package com.project.technologies.spring.service;


import com.project.technologies.spring.entity.Technologies;

import java.util.List;


public interface TechnologiesService {
    public void createTechnology(Technologies tech);
    public List<Technologies> getTech();
    public Technologies findById(long id);
    public List<Technologies> findByName(String name);
    public Technologies update(Technologies tech);
    public void delete(long id);
    public Technologies update(long id, boolean status);
    public List<Technologies> getAllTechs(Integer pageNo, Integer pageSize, String sortBy);

}

