package com.project.technologies.spring.service;


import com.project.technologies.spring.entity.Trainings;

import java.util.Date;
import java.util.List;


public interface TrainingsService {
    public Trainings proposeTraining(long mid, long uid, long sid);
    public List<Trainings> getTraining();
    public Trainings findById(long id);
    public Trainings update(Trainings tech);
    public void delete(long id);
    public List<Trainings> getAllTrainings(Integer pageNo, Integer pageSize, String sortBy);
    public void updateAmount(long id, float amt);
    public void updateEndDate(long id, Date endDate);
    public List<Trainings> getCompleteTrainings();
    public List<Trainings> getCompleteTrainingsUser(long id);
    public List<Trainings> getCompleteTrainingsMentor(long id);
    public List<Trainings> getInprogressTrainings();
    public List<Trainings> getInprogressTrainingsUser(long id);
    public List<Trainings> getInprogressTrainingsMentor(long id);

    Trainings approveTraining(Long id);
}

