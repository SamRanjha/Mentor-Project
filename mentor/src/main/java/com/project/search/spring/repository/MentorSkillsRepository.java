package com.project.search.spring.repository;

import com.project.search.spring.entity.Mentor;
import com.project.search.spring.entity.MentorSkills;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface MentorSkillsRepository extends PagingAndSortingRepository<MentorSkills,Long> {

    public List<MentorSkills> findBySid(long skillId);
    public List<MentorSkills> findByMentor(Mentor m);

}
