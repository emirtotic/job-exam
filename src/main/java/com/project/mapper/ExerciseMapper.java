package com.project.mapper;

import com.project.dto.ExerciseDTO;
import com.project.entity.Exercise;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    Exercise mapToEntity(ExerciseDTO exerciseDTO);
    List<Exercise> mapToEntity(List<ExerciseDTO> exerciseDTOs);

    ExerciseDTO mapToDto(Exercise exercise);
    List<ExerciseDTO> mapToDto(List<Exercise> exercise);

}
