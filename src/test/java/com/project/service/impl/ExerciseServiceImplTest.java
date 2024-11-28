package com.project.service.impl;

import com.project.dto.ExerciseDTO;
import com.project.entity.Exercise;
import com.project.exception.RecordNotFoundException;
import com.project.mapper.ExerciseMapper;
import com.project.repository.ExerciseRepository;
import com.project.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExerciseServiceImplTest {

    private ExerciseMapper exerciseMapper;
    private ExerciseRepository exerciseRepository;

    private ExerciseService exerciseService;

    @BeforeEach
    void setUp() {
        exerciseMapper = mock(Mappers.getMapperClass(ExerciseMapper.class));
        exerciseRepository = mock(ExerciseRepository.class);
        exerciseService = new ExerciseServiceImpl(exerciseRepository, exerciseMapper);
    }

    @Test
    @DisplayName("Upload records from file Test")
    void uploadRecordsFromFile() {

        String csvContent = """
                source,codeListCode,code,displayValue,longDescription,fromDate,toDate,sortingPriority
                source,codeListCode,Type 10,Display value,Long description,2024-11-28,2024-11-28,1
                """;

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        ExerciseDTO dto = createExerciseDTO();
        Exercise entity = createExercise();

        when(exerciseMapper.mapToEntity(dto)).thenReturn(entity);

        exerciseService.uploadRecordsFromFile(multipartFile);

        verify(exerciseRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Find all records Test")
    void findAllTest() {

        List<ExerciseDTO> dtos = Collections.singletonList(createExerciseDTO());
        List<Exercise> entities = Collections.singletonList(createExercise());

        when(exerciseMapper.mapToEntity(dtos)).thenReturn(entities);

        exerciseService.findAllRecords();

        verify(exerciseRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Find all records sorted Test")
    void findAllSortedTest() {

        List<ExerciseDTO> dtos = Collections.singletonList(createExerciseDTO());
        List<Exercise> entities = Collections.singletonList(createExercise());

        when(exerciseMapper.mapToEntity(dtos)).thenReturn(entities);

        exerciseService.findAllRecordsSorted();

        verify(exerciseRepository, times(1)).findAllAndSortByPriority();
    }

    @Test
    @DisplayName("Find record by code Test")
    void findRecordByCode() {

        ExerciseDTO dto = createExerciseDTO();
        Exercise entity = createExercise();

        when(exerciseMapper.mapToEntity(dto)).thenReturn(entity);
        when(exerciseRepository.findByCode(anyString())).thenReturn(Optional.of(entity));

        exerciseService.findRecordByCode("code");

        verify(exerciseRepository, times(1)).findByCode(anyString());
    }

    @Test
    @DisplayName("Delete all records Test")
    void deleteAllRecords() {

        exerciseService.deleteAllRecords();

        verify(exerciseRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("Update record Test")
    void updateRecord() {
        String code = "Type 10";

        Exercise exercise = createExercise();

        ExerciseDTO exerciseDTO = createExerciseDTO();
        exerciseDTO.setSortingPriority(10);

        Exercise updatedExercise = createExercise();
        updatedExercise.setSortingPriority(10);

        ExerciseDTO expectedDTO = createExerciseDTO();
        expectedDTO.setSortingPriority(10);

        when(exerciseRepository.findByCode(code)).thenReturn(Optional.of(exercise));
        when(exerciseMapper.mapToDto(exercise)).thenReturn(expectedDTO);
        when(exerciseRepository.save(updatedExercise)).thenReturn(updatedExercise);

        exerciseService.updateRecord(code, exerciseDTO);

        verify(exerciseRepository, times(1)).findByCode(anyString());
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
        verify(exerciseMapper, times(1)).mapToDto(any(Exercise.class));
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