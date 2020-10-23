package com.project.technologies.spring.service;

import com.project.technologies.spring.entity.Trainings;
import com.project.technologies.spring.repository.TrainingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class TrainingsServiceImp implements TrainingsService {
    @Autowired
    TrainingsRepository trainingsRepository;

    public Trainings proposeTraining(long mid, long uid, long sid){
        Trainings training = new Trainings(mid, uid, sid);
        trainingsRepository.save(training);
        return training;
    }

    public List<Trainings> getTraining(){
        return (List<Trainings>) trainingsRepository.findAll();

    }

    public Trainings findById(long id){
        return trainingsRepository.findById(id).get();
    }

    public Trainings update(Trainings tech){
        return trainingsRepository.save(tech);
    }

    public void delete(long id){
        trainingsRepository.deleteById(id);
    }


    public List<Trainings> getAllTrainings(Integer pageNo, Integer pageSize, String sortBy)
    {
        Sort s = Sort.by(sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize,s);

        Page<Trainings> pagedResult = trainingsRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Trainings>();
        }
    }

    public void updateAmount(long id,float amt){
        Trainings training = trainingsRepository.findById(id).get();
        training.setAmount(amt);
    }

    public void updateEndDate(long id, Date endDate){
        Trainings training = trainingsRepository.findById(id).get();
        training.setEnd_date(endDate);
    }

    public List<Trainings> getCompleteTrainings(){
        return trainingsRepository.getCompleteTrainings();
    }

    public List<Trainings> getCompleteTrainingsUser(long id){
        return trainingsRepository.getCompleteTrainingsUser(id);
    }

    public List<Trainings> getCompleteTrainingsMentor(long id){
        return trainingsRepository.getCompleteTrainingsMentor(id);
    }

    public List<Trainings> getInprogressTrainings(){
        return trainingsRepository.getInprogressTrainings();
    }

    public List<Trainings> getInprogressTrainingsUser(long id){
        return trainingsRepository.getInprogressTrainingsUser(id);
    }

    public List<Trainings> getInprogressTrainingsMentor(long id){
        return trainingsRepository.getInprogressTrainingsMentor(id);
    }

    @Override
    public Trainings approveTraining(Long id) {
        Trainings training = trainingsRepository.findById(id).get();
        training.setStatus(Trainings.Status.APPROVED);
        trainingsRepository.save(training);
        return training;
    }


    public Trainings finalizeTraining(Long id){
        Trainings training = trainingsRepository.findById(id).get();
        training.setStatus(Trainings.Status.INPROGRESS);
        training.setAmount(2000);
        trainingsRepository.save(training);
        return training;
    }



}
