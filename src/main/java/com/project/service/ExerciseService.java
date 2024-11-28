package com.project.service;

import com.project.dto.ExerciseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.Writer;
import java.util.List;

public interface ExerciseService {

    void uploadRecordsFromFile(MultipartFile multipartFile);
    List<ExerciseDTO> findAllRecords();
    ExerciseDTO findRecordByCode(String code);
    void deleteAllRecords();
    void exportDataToCsv(Writer writer);
    List<ExerciseDTO> findAllRecordsSorted();
    ExerciseDTO updateRecord(String code, ExerciseDTO exerciseDTO);
}
