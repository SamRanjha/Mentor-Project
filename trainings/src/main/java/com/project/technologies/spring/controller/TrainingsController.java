package com.project.technologies.spring.controller;

import com.project.technologies.spring.entity.Trainings;
import com.project.technologies.spring.service.TrainingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

class Token{
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

class Response{
    private String username;
    private boolean tokenValid;

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isTokenStatus() {
        return tokenValid;
    }

    public void setTokenStatus(boolean tokenStatus) {
        this.tokenValid = tokenStatus;
    }
}

@RestController
@RequestMapping
public class TrainingsController {
    @Autowired
    TrainingsService trainingsService;

    @Autowired
    RestTemplate restTemplate;

    private final Logger log = LoggerFactory.getLogger(getClass());


    private static final String AUTHENTICATION_URL = "http://localhost:9009/valid";
    private static final String ROLE = "http://localhost:9010/getRole/";
    public boolean validateMentor(Token token, long mid){
        ResponseEntity<Response> r = restTemplate.postForEntity(AUTHENTICATION_URL,token,Response.class);
        Response response = r.getBody();
        boolean valid = response.isTokenStatus();
        String url = "http://localhost:9010/getRole/" + mid;
        ResponseEntity<String> r2 = restTemplate.getForEntity(url,String.class);
        String role = r2.getBody();
        url = "http://localhost:9010/getUserName/" + mid;
        r2 = restTemplate.getForEntity(url,String.class);
        String username = r2.getBody();
        System.out.println(role);
        return valid && (role.equals("Mentor")) && (username.equals(response.getUsername()));
    }

    public boolean validateUser(Token token, long uid){
        ResponseEntity<Response> r = restTemplate.postForEntity(AUTHENTICATION_URL,token,Response.class);
        Response response = r.getBody();
        boolean valid = response.isTokenStatus();
        String url = "http://localhost:9010/getRole/" + uid;
        ResponseEntity<String> r2 = restTemplate.getForEntity(url,String.class);
        String role = r2.getBody();
        url = "http://localhost:9010/getUserName/" + uid;
        r2 = restTemplate.getForEntity(url,String.class);
        String username = r2.getBody();
        System.out.println(role);
        return valid && (role.equals("User")) && (username.equals(response.getUsername()));
    }

    @PostMapping(value="/proposeTraining")
    public Trainings createTraining(@RequestHeader("Authorization") String jwttoken,
            @RequestParam Long mid,
            @RequestParam Long uid,
            @RequestParam Long sid){
        Token t = new Token();
        t.token = jwttoken;
        boolean isUser = validateUser(t,uid);
        if(!isUser) {
            log.info("User is invalid");
            return null;
        }
        log.info("Creating training for user id: " +  uid + " and mentor id: " + mid +" for skill id:" + sid);
        return trainingsService.proposeTraining(mid,uid,sid);

    }

    @PostMapping(value="/approveTraining")
    public Trainings approveTraining(@RequestHeader("Authorization") String jwttoken,@RequestParam Long id){
        log.info("Approving training for training_id " + id );
        log.info("Fetching training with id " + id);
        Trainings training = trainingsService.findById(id);
        long mid = training.getMid();
        Token t = new Token();
        t.token = jwttoken;
        boolean isMentor = validateMentor(t,mid);
        if(!isMentor) {
            log.info("Mentor is invalid");
            return null;
        }

        if (training == null) {
            log.error("No training with id: " + id +" exists");
        }
        return trainingsService.approveTraining(id);
    }


    @PostMapping(value="/finalizeTraining")
    public Trainings finalizeTraining(@RequestHeader("Authorization") String jwttoken,@RequestParam Long id){
        log.info("Approving training for training_id " + id );
        log.info("Fetching training with id " + id);
        Trainings training = trainingsService.findById(id);
        long uid = training.getUid();
        Token t = new Token();
        t.token = jwttoken;
        boolean isUser = validateUser(t,uid);
        if(!isUser) {
            log.info("User is invalid");
            return null;
        }
        if (training == null) {
            log.error("No training with id: " + id +" exists");
        }
        return trainingsService.finalizeTraining(id);
    }


    @GetMapping(value="/get", headers="Accept=application/json")
    public List<Trainings> getAllTrainings() {
        List<Trainings> trainings = trainingsService.getTraining();
        return trainings;
    }

    @GetMapping(value="/getInprogressTrainings")
    public List<Trainings> getInprogressTrainings() {
        List<Trainings> trainings = trainingsService.getInprogressTrainings();
        return trainings;
    }

    @GetMapping(value="/getInprogressTrainings/user/id")
    public List<Trainings> getInprogressTrainingsUser(@RequestHeader("Authorization") String jwttoken,@PathVariable("id") long id) {
        log.info("Fetching inprogress training for user with user_id " + id);
        List<Trainings> trainings = trainingsService.getInprogressTrainingsUser(id);
        long uid = id;
        Token t = new Token();
        t.token = jwttoken;
        boolean isUser = validateUser(t,uid);
        if(!isUser) {
            log.info("User is invalid");
            return null;
        }
        if (trainings == null) {
            log.error("No inprogress trainings for user: " + id);
        }
        return trainings;
    }

    @GetMapping(value="/getInprogressTrainings/mentor/id")
    public List<Trainings> getInprogressTrainingsMentor(@RequestHeader("Authorization") String jwttoken,@PathVariable("id") long id) {
        log.info("Fetching inprogress training for mentor with mentor_id " + id);
        List<Trainings> trainings = trainingsService.getInprogressTrainingsMentor(id);
        long mid = id;
        Token t = new Token();
        t.token = jwttoken;
        boolean isMentor = validateMentor(t,mid);
        if(!isMentor) {
            log.info("Mentor is invalid");
            return null;
        }
        if (trainings == null) {
            log.error("No inprogress trainings for mentor: " + id);
        }
        return trainings;
    }

    @GetMapping(value="/getCompleteTrainings")
    public List<Trainings> getCompleteTrainings() {
        List<Trainings> trainings = trainingsService.getCompleteTrainings();
        return trainings;
    }

    @GetMapping(value="/getCompleteTrainings/user/{id}")
    public List<Trainings> getCompleteTrainingsUser(@RequestHeader("Authorization") String jwttoken,@PathVariable("id") long id) {
        log.info("Fetching complete training for user with user_id " + id);
        List<Trainings> trainings = trainingsService.getCompleteTrainingsUser(id);
        long uid = id;
        Token t = new Token();
        t.token = jwttoken;
        boolean isUser = validateUser(t,uid);
        if(!isUser) {
            log.info("User is invalid");
            return null;
        }
        if (trainings == null) {
            log.error("No completed trainings for user: " + id);
        }
        return trainings;
    }

    @GetMapping(value="/getCompleteTrainings/mentor/{id}")
    public List<Trainings> getCompleteTrainingsMentor(@RequestHeader("Authorization") String jwttoken,@PathVariable("id") long id) {
        log.info("Fetching complete training for mentor with mentor_id " + id);
        List<Trainings> trainings = trainingsService.getCompleteTrainingsMentor(id);
        long mid = id;
        Token t = new Token();
        t.token = jwttoken;
        boolean isMentor = validateMentor(t,mid);
        if(!isMentor) {
            log.info("Mentor is invalid");
            return null;
        }
        if (trainings == null) {
            log.error("No completed trainings for mentor: " + id);
        }
        return trainings;
    }

    @GetMapping(value = "/getTrainingDetails/{id}")
    public ResponseEntity<Trainings> getTechById(@PathVariable("id") long id) {
        log.info("Fetching training with id " + id);
        Trainings tech = trainingsService.findById(id);
        if (tech == null) {
            log.error("No technology with id: " + id +" exists");
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
        log.info("Fetching training with id " + ctraining.getId());
        Trainings training = trainingsService.findById(ctraining.getId());
        if (training == null) {
            log.error("No training with id: " + ctraining.getId() +" exists");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        trainingsService.update(ctraining);
        return new ResponseEntity<String>(HttpStatus.OK);
    }



    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<Trainings> deleteTraining(@PathVariable("id") long id){
        log.info("Fetching training with id " + id);
        Trainings tech = trainingsService.findById(id);
        if (tech == null) {
            log.error("No training with id: " + id +" exists");
            return new ResponseEntity<Trainings>(HttpStatus.NOT_FOUND);
        }
        trainingsService.delete(id);
        log.info("Successfully deleted technology with id " + id);
        return new ResponseEntity<Trainings>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/updateAmt/{id}")
    public ResponseEntity<String> updateAmount(@PathVariable("id") long id, @RequestParam float amt){
        log.info("Fetching training with id " + id);
        Trainings training = trainingsService.findById(id);
        if (training == null) {
            log.error("No training with id: " + id +" exists");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        trainingsService.updateAmount(id,amt);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(value="/updateEnddate/{id}")
    public ResponseEntity<String> updateEndDate(@PathVariable("id") long id, @RequestParam java.time.LocalDate endDate){
        log.info("Fetching training with id " + id);
        Trainings training = trainingsService.findById(id);
        if (training == null) {
            log.error("No training with id: " + id +" exists");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        trainingsService.updateEndDate(id,endDate);
        return new ResponseEntity<String>(HttpStatus.OK);
    }


}
