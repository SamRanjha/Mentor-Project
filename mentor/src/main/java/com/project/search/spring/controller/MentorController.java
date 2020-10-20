package com.project.search.spring.controller;


import com.project.search.spring.entity.Mentor;
import com.project.search.spring.entity.MentorSkills;
import com.project.search.spring.model.Details;
import com.project.search.spring.model.User;
import com.project.search.spring.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.soap.Detail;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping
public class MentorController {
    @Autowired
    MentorService mentorService;


    @GetMapping(value = "/getSearchResults/{skill}")
    public ResponseEntity<List<Details>> getSearchResults(@PathVariable("skill") String skill) {
        List<Details> details = mentorService.searchResults(skill);
        if (details == null) {
            return new ResponseEntity<List<Details>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Details>>(details, HttpStatus.OK);
    }

    @GetMapping(value="/getMentorDetails/{mid}")
    public ResponseEntity<Details> getMentorDetails(@PathVariable("mid") long mid) {
        Mentor mentor = mentorService.findById(mid);
        RestTemplate restTemplate = new RestTemplate();
        String url = "";
        URI uri = null;
        User u = new User();
        url = "http://localhost:9010/getUser/" + mid;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<User>resultUser = restTemplate.getForEntity(uri, User.class);
        u = resultUser.getBody();

        Details detail = new Details();
        detail.setFirstname(u.getFirstname());
        detail.setLastname(u.getLastname());
        detail.setMentor(mentor);

        if (mentor == null) {
            return new ResponseEntity<Details>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Details>(detail, HttpStatus.OK);

    }


    @GetMapping(value="/getMentorSkills/{mid}")
    public List<MentorSkills> getMentorSkills(@PathVariable("mid") long mid) {
        Mentor mentor = mentorService.findById(mid);
        return mentorService.getMentorSkills(mentor);
    }

    @PostMapping(value = "/createMentor")
    public String createMentor(@RequestBody Mentor mentor){
        mentorService.createMentor(mentor);
        return "Succesfully created Mentor with id:" + mentor.getMid();
    }

    @PostMapping(value = "/createSkill/{mid}")
    public ResponseEntity<String> createSkill(@PathVariable("mid") long mid, @RequestBody MentorSkills skills) {
        Mentor m = mentorService.findById(mid);
        if (m == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        mentorService.createSkill(mid,skills);
        return new ResponseEntity<String>("Succesfully created skill", HttpStatus.OK);

    }

}
