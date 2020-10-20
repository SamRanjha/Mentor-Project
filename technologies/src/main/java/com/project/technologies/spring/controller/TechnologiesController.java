package com.project.technologies.spring.controller;


import com.project.technologies.spring.entity.Technologies;
import com.project.technologies.spring.service.TechnologiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

class Status{
    long id;

    boolean status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

@RestController
@RequestMapping
public class TechnologiesController {
    @Autowired
    TechnologiesService technologiesService;


    @GetMapping(value = "/getSkill/{id}")
    public ResponseEntity<Technologies> getSkill(@PathVariable("id") long id) {
        System.out.println("Fetching technology with id " + id);
        Technologies tech = technologiesService.findById(id);
        if (tech == null) {
            return new ResponseEntity<Technologies>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Technologies>(tech, HttpStatus.OK);
    }

    @GetMapping(value = "/searchSkill/{skillName}")
    public ResponseEntity<List<Technologies>> searchSkill(@PathVariable("skillName") String skillName) {
        System.out.println("Fetching technology with name " + skillName);
        List<Technologies> tech = technologiesService.findByName(skillName);
        if (tech == null) {
            return new ResponseEntity<List<Technologies>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Technologies>>(tech, HttpStatus.OK);
    }



    @PostMapping(value="/create")
    public String createUser(@RequestBody Technologies tech){
        System.out.println("Creating Tech: "+tech.getName());
        technologiesService.createTechnology(tech);
        return "Succesfully created " + tech.getName();
    }


    @GetMapping(value="/get", headers="Accept=application/json")
    public List<Technologies> getAllUser() {
        List<Technologies> technologies = technologiesService.getTech();
        List<Technologies> newTech = new LinkedList<>();
        for(Technologies tech : technologies){
            if(tech.isActive()){
                newTech.add(tech);
            }
        }
        return newTech;
    }

    @GetMapping
    public ResponseEntity<List<Technologies>> getAllTechs(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Technologies> list = technologiesService.getAllTechs(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<Technologies>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @PutMapping(value="/update")
    public ResponseEntity<String> updateTech(@RequestBody Technologies ctech)
    {
        Technologies tech = technologiesService.findById(ctech.getId());
        if (tech == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        technologiesService.update(ctech);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(value="/updateStatus")
    public ResponseEntity<String> changeStatus(@RequestBody Status s)
    {
        Technologies tech = technologiesService.findById(s.getId());
        if (tech == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        technologiesService.update(s.getId(),s.isStatus());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<Technologies> deleteUser(@PathVariable("id") long id){
        System.out.println("Fetching technology with id " + id);
        Technologies tech = technologiesService.findById(id);
        if (tech == null) {
            return new ResponseEntity<Technologies>(HttpStatus.NOT_FOUND);
        }
        technologiesService.delete(id);
        System.out.println("Successfully deleted technology with id " + id);
        return new ResponseEntity<Technologies>(HttpStatus.NO_CONTENT);
    }


}
