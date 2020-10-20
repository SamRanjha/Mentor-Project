package com.project.search.spring.service;


import com.project.search.spring.entity.Mentor;
import com.project.search.spring.entity.MentorSkills;
import com.project.search.spring.model.Details;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface MentorService {
    public List<Details> searchResults(String skill);
    public Mentor findById(long id);
    public Mentor createMentor(Mentor m);
    public void createSkill(long mid, MentorSkills mentorSkills);
    public List<MentorSkills> getMentorSkills( Mentor m);


    }

