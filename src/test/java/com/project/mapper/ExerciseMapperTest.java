package com.project.mapper;

import com.project.dto.ExerciseDTO;
import com.project.entity.Exercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseMapperTest {

    private ExerciseMapper exerciseMapper = Mappers.getMapper(ExerciseMapper.class);

    private Exercise exercise;
    private ExerciseDTO exerciseDTO;

    @BeforeEach
    void setUp() {
        exercise = createExercise();
        exerciseDTO = createExerciseDTO();
    }

    @Test
    @DisplayName("Map ExerciseDTO to Exercise")
    void mapToEntity() {

        Exercise entity = exerciseMapper.mapToEntity(exerciseDTO);

        assertEquals(exerciseDTO.getId(), entity.getId());
        assertEquals(exerciseDTO.getSource(), entity.getSource());
        assertEquals(exerciseDTO.getCodeListCode(), entity.getCodeListCode());
        assertEquals(exerciseDTO.getCode(), entity.getCode());
        assertEquals(exerciseDTO.getDisplayValue(), entity.getDisplayValue());
        assertEquals(exerciseDTO.getLongDescription(), entity.getLongDescription());
        assertEquals(exerciseDTO.getFromDate(), entity.getFromDate());
        assertEquals(exerciseDTO.getToDate(), entity.getToDate());
        assertEquals(exerciseDTO.getSortingPriority(), entity.getSortingPriority());
    }

    @Test
    @DisplayName("Map List<ExerciseDTO> to List<Exercise>")
    void mapToEntityList() {

        List<ExerciseDTO> dtos = Collections.singletonList(exerciseDTO);
        List<Exercise> entities = Collections.singletonList(exerciseMapper.mapToEntity(exerciseDTO));

        assertEquals(dtos.get(0).getId(), entities.get(0).getId());
        assertEquals(dtos.get(0).getSource(), entities.get(0).getSource());
        assertEquals(dtos.get(0).getCodeListCode(), entities.get(0).getCodeListCode());
        assertEquals(dtos.get(0).getCode(), entities.get(0).getCode());
        assertEquals(dtos.get(0).getDisplayValue(), entities.get(0).getDisplayValue());
        assertEquals(dtos.get(0).getLongDescription(), entities.get(0).getLongDescription());
        assertEquals(dtos.get(0).getFromDate(), entities.get(0).getFromDate());
        assertEquals(dtos.get(0).getToDate(), entities.get(0).getToDate());
        assertEquals(dtos.get(0).getSortingPriority(), entities.get(0).getSortingPriority());
    }

    @Test
    @DisplayName("Map Exercise to ExerciseDTO")
    void mapToDto() {

        ExerciseDTO dto = exerciseMapper.mapToDto(exercise);

        assertEquals(exercise.getId(), dto.getId());
        assertEquals(exercise.getSource(), dto.getSource());
        assertEquals(exercise.getCodeListCode(), dto.getCodeListCode());
        assertEquals(exercise.getCode(), dto.getCode());
        assertEquals(exercise.getDisplayValue(), dto.getDisplayValue());
        assertEquals(exercise.getLongDescription(), dto.getLongDescription());
        assertEquals(exercise.getFromDate(), dto.getFromDate());
        assertEquals(exercise.getToDate(), dto.getToDate());
        assertEquals(exercise.getSortingPriority(), dto.getSortingPriority());
    }

    @Test
    @DisplayName("Map List<Exercise> to List<ExerciseDTO>")
    void testMapToDto() {

        List<Exercise> entities = Collections.singletonList(exercise);
        List<ExerciseDTO> dtos = exerciseMapper.mapToDto(entities);

        assertEquals(dtos.get(0).getId(), entities.get(0).getId());
        assertEquals(dtos.get(0).getSource(), entities.get(0).getSource());
        assertEquals(dtos.get(0).getCodeListCode(), entities.get(0).getCodeListCode());
        assertEquals(dtos.get(0).getCode(), entities.get(0).getCode());
        assertEquals(dtos.get(0).getDisplayValue(), entities.get(0).getDisplayValue());
        assertEquals(dtos.get(0).getLongDescription(), entities.get(0).getLongDescription());
        assertEquals(dtos.get(0).getFromDate(), entities.get(0).getFromDate());
        assertEquals(dtos.get(0).getToDate(), entities.get(0).getToDate());
        assertEquals(dtos.get(0).getSortingPriority(), entities.get(0).getSortingPriority());
    }

    private Exercise createExercise() {

        Exercise entity = new Exercise();
        entity.setId(1L);
        entity.setSource("source");
        entity.setCodeListCode("codeListCode");
        entity.setCode("Type 10");
        entity.setDisplayValue("Display value");
        entity.setLongDescription("Long description");
        entity.setFromDate(LocalDate.now());
        entity.setToDate(LocalDate.now());
        entity.setSortingPriority(1);

        return entity;

    }

    private ExerciseDTO createExerciseDTO() {

        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(1L);
        dto.setSource("source");
        dto.setCodeListCode("codeListCode");
        dto.setCode("Type 10");
        dto.setDisplayValue("Display value");
        dto.setLongDescription("Long description");
        dto.setFromDate(LocalDate.now());
        dto.setToDate(LocalDate.now());
        dto.setSortingPriority(1);

        return dto;

    }
}