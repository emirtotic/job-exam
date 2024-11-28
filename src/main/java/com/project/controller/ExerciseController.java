package com.project.controller;

import com.project.dto.ExerciseDTO;
import com.project.service.ExerciseService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final String FILE_NAME = "allRecords.csv";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(name = "file") MultipartFile multipartFile) {

        exerciseService.uploadRecordsFromFile(multipartFile);
        return new ResponseEntity<>("Records successfully uploaded", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExerciseDTO>> findAllRecords() {
        return new ResponseEntity<>(exerciseService.findAllRecords(), HttpStatus.OK);
    }

    @GetMapping("/record/code")
    public ResponseEntity<ExerciseDTO> findRecordByCode(@RequestParam(name = "code") String code) {
        return new ResponseEntity<>(exerciseService.findRecordByCode(code), HttpStatus.OK);
    }

    @DeleteMapping("/all/delete")
    public ResponseEntity<String> deleteAllRecords() {
        exerciseService.deleteAllRecords();
        return new ResponseEntity<>("Records has been removed successfully", HttpStatus.OK);
    }

    @GetMapping("/all/export-csv")
    public ResponseEntity<Void> exportDataToCsv(HttpServletResponse response) {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);

        try (PrintWriter writer = response.getWriter()) {
            exerciseService.exportDataToCsv(writer);
        } catch (Exception e) {
            throw new RuntimeException("Error writing CSV file", e);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all/priority")
    public ResponseEntity<List<ExerciseDTO>> findAllAndSortByPriority() {
        return new ResponseEntity<>(exerciseService.findAllRecordsSorted(), HttpStatus.OK);
    }

    @PutMapping("/record/update/{code}")
    public ResponseEntity<ExerciseDTO> updateRecord(@PathVariable(name = "code") String code,
                                                    @RequestBody ExerciseDTO exerciseDTO) {
        return new ResponseEntity<>(exerciseService.updateRecord(code, exerciseDTO), HttpStatus.OK);
    }

}
