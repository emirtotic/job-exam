package com.project.repository;

import com.project.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findByCode(String code);

    @Query("SELECT e FROM Exercise e ORDER BY e.sortingPriority ASC NULLS LAST")
    List<Exercise> findAllAndSortByPriority();

}
