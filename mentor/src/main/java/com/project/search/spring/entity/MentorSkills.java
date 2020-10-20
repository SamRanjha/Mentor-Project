package com.project.search.spring.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "MentorSkills")
public class MentorSkills {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "mid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Mentor mentor;

    @Column
    private long sid;

    @Column
    private long self_rating;

    @Column
    private Float years_of_Experience;

    @Column
    private int trainings_delivered;

    @Column
    private String facilities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getSelf_rating() {
        return self_rating;
    }

    public void setSelf_rating(long self_rating) {
        this.self_rating = self_rating;
    }

    public Float getYears_of_Experience() {
        return years_of_Experience;
    }

    public void setYears_of_Experience(Float years_of_Experience) {
        this.years_of_Experience = years_of_Experience;
    }

    public int getTrainings_delivered() {
        return trainings_delivered;
    }

    public void setTrainings_delivered(int trainings_delivered) {
        this.trainings_delivered = trainings_delivered;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
}
