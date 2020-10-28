package com.project.technologies.spring.service;

import com.project.technologies.spring.entity.Trainings;
import com.project.technologies.spring.repository.TrainingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Payments {
    // make data members private
    String Id;;
    String Uid;
    String Mid;
    String Tid;
    String Amt;
    String Datetime;
    String Remarks;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getAmt() {
        return Amt;
    }

    public void setAmt(String amt) {
        Amt = amt;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}

@Service
public class TrainingsServiceImp implements TrainingsService {
    @Autowired
    TrainingsRepository trainingsRepository;

    public Trainings proposeTraining(long mid, long uid, long sid) {
        Trainings training = new Trainings(mid, uid, sid);
        trainingsRepository.save(training);
        return training;
    }

    public List<Trainings> getTraining() {
        return (List<Trainings>) trainingsRepository.findAll();

    }

    public Trainings findById(long id) {
        return trainingsRepository.findById(id).get();
    }

    public Trainings update(Trainings tech) {
        return trainingsRepository.save(tech);
    }

    public void delete(long id) {
        trainingsRepository.deleteById(id);
    }


    public List<Trainings> getAllTrainings(Integer pageNo, Integer pageSize, String sortBy) {
        Sort s = Sort.by(sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, s);

        Page<Trainings> pagedResult = trainingsRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Trainings>();
        }
    }

    public void updateAmount(long id, float amt) {
        Trainings training = trainingsRepository.findById(id).get();
        training.setAmount(amt);
    }

    public void updateEndDate(long id, java.time.LocalDate endDate) {
        Trainings training = trainingsRepository.findById(id).get();
        training.setEnd_date(endDate);
    }

    public List<Trainings> getCompleteTrainings() {
        return trainingsRepository.getCompleteTrainings();
    }

    public List<Trainings> getCompleteTrainingsUser(long id) {
        return trainingsRepository.getCompleteTrainingsUser(id);
    }

    public List<Trainings> getCompleteTrainingsMentor(long id) {
        return trainingsRepository.getCompleteTrainingsMentor(id);
    }

    public List<Trainings> getInprogressTrainings() {
        return trainingsRepository.getInprogressTrainings();
    }

    public List<Trainings> getInprogressTrainingsUser(long id) {
        return trainingsRepository.getInprogressTrainingsUser(id);
    }

    public List<Trainings> getInprogressTrainingsMentor(long id) {
        return trainingsRepository.getInprogressTrainingsMentor(id);
    }

    @Override
    public Trainings approveTraining(Long id) {
        Trainings training = trainingsRepository.findById(id).get();
        training.setStatus(Trainings.Status.APPROVED);
        trainingsRepository.save(training);
        return training;
    }


    public Trainings finalizeTraining(Long id) {
        // create batch program for marking training as complete when end date
        Trainings training = trainingsRepository.findById(id).get();
        training.setStatus(Trainings.Status.INPROGRESS);
        training.setStart_date(java.time.LocalDate.now());
        training.setStart_time(java.time.LocalTime.now());
        training.setEnd_date(java.time.LocalDate.now().plusDays(10));
        training.setEnd_time(java.time.LocalTime.now());
        Payments payments = new Payments();
        payments.Id = "" + training.getId();
        payments.Mid = "" + training.getMid();
        payments.Uid = "" + training.getUid();
        payments.Tid = "" + training.getId();
        payments.Amt = "" + 2000;
        payments.Datetime = "" + java.time.LocalDateTime.now();
        payments.Remarks = "Payment done";
//        training.setAmount(2000);
        // setting payment in payments table
        RestTemplate restTemplate = new RestTemplate();
        String url = "";
        URI uri = null;
        url = "http://localhost:9016/createPayment";
        restTemplate.postForEntity(url,payments,Payments.class);
        trainingsRepository.save(training);
        return training;
    }


}
