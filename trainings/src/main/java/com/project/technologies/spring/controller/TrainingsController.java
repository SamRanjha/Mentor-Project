package com.project.technologies.spring.controller;

import com.project.technologies.spring.entity.Trainings;
import com.project.technologies.spring.service.TrainingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping
public class TrainingsController {
    @Autowired
    TrainingsService trainingsService;


    @PostMapping(value="/proposeTraining")
    public Trainings createTraining(
            @RequestParam Long mid,
            @RequestParam Long uid,
            @RequestParam Long sid){
        return trainingsService.proposeTraining(mid,uid,sid);

    }

    @PostMapping(value="/approveTraining")
    public Trainings approveTraining(@RequestParam Long id){
        return trainingsService.approveTraining(id);
    }


    @GetMapping(value="/get", headers="Accept=application/json")
    public List<Trainings> getAllUser() {
        List<Trainings> trainings = trainingsService.getTraining();
        return trainings;
    }

    @GetMapping(value="/getInprogressTrainings")
    public List<Trainings> getInprogressTrainings() {
        List<Trainings> trainings = trainingsService.getInprogressTrainings();
        return trainings;
    }

    @GetMapping(value="/getInprogressTrainings/user/id")
    public List<Trainings> getInprogressTrainingsUser(@PathVariable("id") long id) {
        List<Trainings> trainings = trainingsService.getInprogressTrainingsUser(id);
        return trainings;
    }

    @GetMapping(value="/getInprogressTrainings/mentor/id")
    public List<Trainings> getInprogressTrainingsMentor(@PathVariable("id") long id) {
        List<Trainings> trainings = trainingsService.getInprogressTrainingsMentor(id);
        return trainings;
    }

    @GetMapping(value="/getCompleteTrainings")
    public List<Trainings> getCompleteTrainings() {
        List<Trainings> trainings = trainingsService.getCompleteTrainings();
        return trainings;
    }

    @GetMapping(value="/getCompleteTrainings/user/{id}")
    public List<Trainings> getCompleteTrainingsUser(@PathVariable("id") long id) {
        List<Trainings> trainings = trainingsService.getCompleteTrainingsUser(id);
        return trainings;
    }

    @GetMapping(value="/getCompleteTrainings/mentor/{id}")
    public List<Trainings> getCompleteTrainingsMentor(@PathVariable("id") long id) {
        List<Trainings> trainings = trainingsService.getCompleteTrainingsMentor(id);
        return trainings;
    }

    @GetMapping(value = "/getTrainingDetails/{id}")
    public ResponseEntity<Trainings> getTechById(@PathVariable("id") long id) {
        System.out.println("Fetching training with id " + id);
        Trainings tech = trainingsService.findById(id);
        if (tech == null) {
            return new ResponseEntity<Trainings>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Trainings>(tech, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Trainings>> getAllTechs(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Trainings> list = trainingsService.getAllTrainings(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<Trainings>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @PutMapping(value="/update")
    public ResponseEntity<String> updateTraining(@RequestBody Trainings ctraining)
    {
        Trainings training = trainingsService.findById(ctraining.getId());
        if (training == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        trainingsService.update(ctraining);
        return new ResponseEntity<String>(HttpStatus.OK);
    }



    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<Trainings> deleteUser(@PathVariable("id") long id){
        System.out.println("Fetching technology with id " + id);
        Trainings tech = trainingsService.findById(id);
        if (tech == null) {
            return new ResponseEntity<Trainings>(HttpStatus.NOT_FOUND);
        }
        trainingsService.delete(id);
        System.out.println("Successfully deleted technology with id " + id);
        return new ResponseEntity<Trainings>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/updateAmt/{id}")
    public ResponseEntity<String> updateAmount(@PathVariable("id") long id, @RequestParam float amt){
        Trainings training = trainingsService.findById(id);
        if (training == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        trainingsService.updateAmount(id,amt);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(value="/updateEnddate/{id}")
    public ResponseEntity<String> updateEndDate(@PathVariable("id") long id, @RequestParam Date endDate){
        Trainings training = trainingsService.findById(id);
        if (training == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        trainingsService.updateEndDate(id,endDate);
        return new ResponseEntity<String>(HttpStatus.OK);
    }


}
