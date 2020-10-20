package com.project.technologies.spring.service;

import com.project.technologies.spring.entity.Technologies;
import com.project.technologies.spring.repository.TechnologiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TechnologiesServiceImp implements TechnologiesService {
    @Autowired
    TechnologiesRepository technologiesRepository;

    public void createTechnology(Technologies tech){
        technologiesRepository.save(tech);
    }

    public List<Technologies> getTech(){
        return (List<Technologies>)technologiesRepository.findAll();

    }

    public Technologies findById(long id){
        return technologiesRepository.findById(id).get();
    }

    public Technologies update(Technologies tech){
        return technologiesRepository.save(tech);
    }

    public void delete(long id){
        technologiesRepository.deleteById(id);
    }

    public Technologies update(long id, boolean status){
        Technologies tech = technologiesRepository.findById(id).get();
        tech.setActive(status);
        return technologiesRepository.save(tech);
    }

    public List<Technologies> getAllTechs(Integer pageNo, Integer pageSize, String sortBy)
    {
        Sort s = Sort.by(sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize,s);

        Page<Technologies> pagedResult = technologiesRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Technologies>();
        }
    }

    public List<Technologies> findByName(String name){
        return technologiesRepository.findByName(name);
    }


}
