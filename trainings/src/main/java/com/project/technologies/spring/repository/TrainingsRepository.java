package com.project.technologies.spring.repository;

import com.project.technologies.spring.entity.Trainings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface TrainingsRepository extends PagingAndSortingRepository<Trainings,Long> {
    @Query(value = "SELECT * FROM Trainings where Status = 4", nativeQuery = true)
    public List<Trainings> getCompleteTrainings();

    @Query(value = "SELECT * FROM Trainings where Status = 4 AND uid = ?1", nativeQuery = true)
    public List<Trainings> getCompleteTrainingsUser(long uid);

    @Query(value = "SELECT * FROM Trainings where Status = 4 AND mid = ?1", nativeQuery = true)
    public List<Trainings> getCompleteTrainingsMentor(long uid);

    @Query(value = "SELECT * FROM Trainings where Status = 3", nativeQuery = true)
    public List<Trainings> getInprogressTrainings();

    @Query(value = "SELECT * FROM Trainings where Status = 3 AND  uid = ?1", nativeQuery = true)
    public List<Trainings> getInprogressTrainingsUser(long id);

    @Query(value = "SELECT * FROM Trainings where Status = 3 AND  mid = ?1", nativeQuery = true)
    public List<Trainings> getInprogressTrainingsMentor(long id);
}
