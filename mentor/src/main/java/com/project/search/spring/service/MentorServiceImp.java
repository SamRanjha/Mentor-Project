package com.project.search.spring.service;

import com.project.search.spring.entity.Mentor;
import com.project.search.spring.entity.MentorSkills;
import com.project.search.spring.model.Details;
import com.project.search.spring.model.Technologies;
import com.project.search.spring.model.User;
import com.project.search.spring.repository.MentorRepository;
import com.project.search.spring.repository.MentorSkillsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@Service
public class MentorServiceImp implements MentorService {
    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    MentorSkillsRepository mentorSkillsRepository;

    private final Logger Log = LoggerFactory.getLogger(getClass());

    public Technologies convertToTechnologies(HashMap<String, Object> map) {
        Technologies tech = new Technologies();
        tech.setId((Integer) map.get("id"));
        tech.setName((String) map.get("name"));
        tech.setToc((String) map.get("toc"));
        tech.setDuration((String) map.get("duration"));
        tech.setPrerequites((String) map.get("prerequites"));
        if (map.get("active").equals("true"))
            tech.setActive(true);
        else
            tech.setActive(false);

        return tech;
    }

    public List<Details> searchResults(String skill) {
        Log.info("Searching for skill: " + skill);
        RestTemplate restTemplate = new RestTemplate();
        String url = "";
        URI uri = null;

        // getSkill from Technology
        url = "http://localhost:9011/searchSkill/" + skill;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ResponseEntity<List> result = restTemplate.getForEntity(uri, List.class);
        List<HashMap<String, Object>> resultBody = result.getBody();
        List<Technologies> technologies = new LinkedList<>();
        for (Object obj : resultBody) {
            HashMap<String, Object> map = (HashMap<String, Object>) obj;
            technologies.add(convertToTechnologies(map));
        }

        ArrayList<Long> skillID = new ArrayList<Long>();
        for (Technologies tech : technologies) {
            skillID.add(tech.getId());
        }

        //get skill id from skill_info
        long skill_id = skillID.get(0);
        Log.info("Successfull found skill_id for skill: " + skill_id);
        Log.info("Finding mentor with skill_id " + skill_id);

        List<MentorSkills> mentors = mentorSkillsRepository.findBySid(skill_id);
        List<Mentor> mentorList = new LinkedList<>();
        for (MentorSkills mskills : mentors) {
            mentorList.add(mskills.getMentor());
        }

        List<Details> searchResults = new LinkedList<>();

        for (Mentor mentor : mentorList) {
            searchResults.add(getUserDetails(mentor));
        }

        return searchResults;

    }

    public Details getUserDetails(Mentor mentor) {
        Log.info("Getting user name for mentor: " + mentor.getMid());
        RestTemplate restTemplate = new RestTemplate();
        String url = "";
        URI uri = null;
        User u = new User();
        url = "http://localhost:9010/getUser/" + mentor.getMid();
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<User> resultUser = restTemplate.getForEntity(uri, User.class);
        u = resultUser.getBody();
        if (u == null) {
            Log.warn("Username for mentor is null");
        }
        Log.info("Username for mentor is: " + u.getFirstname() + " " + u.getLastname());
        System.out.println(u.getFirstname());
        Details detail = new Details();
        detail.setMentor(mentor);
        detail.setFirstname(u.getFirstname());
        detail.setLastname(u.getLastname());

        return detail;

    }

    public Mentor findById(long id) {
        return mentorRepository.findById(id).get();
    }

    public Mentor createMentor(Mentor m) {
        Log.info("Saving mentor with id" + m.getMid());
        mentorRepository.save(m);
        return m;
    }

    public void createSkill(long mid, MentorSkills mentorSkills) {
        Log.info("Creating skill for mentor:" + mid + " with skill_id: " + mentorSkills.getSid());
        Mentor mentor = mentorRepository.findById(mid).get();
        MentorSkills skill = new MentorSkills();
        BeanUtils.copyProperties(mentorSkills, skill);
        skill.setMentor(mentor);
        mentorSkillsRepository.save(skill);

    }

    public List<MentorSkills> getMentorSkills(Mentor m) {
        return mentorSkillsRepository.findByMentor(m);
    }


}
