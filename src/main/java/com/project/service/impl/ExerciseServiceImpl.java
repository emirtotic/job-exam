package com.project.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.project.dto.ExerciseDTO;
import com.project.entity.Exercise;
import com.project.exception.RecordNotFoundException;
import com.project.mapper.ExerciseMapper;
import com.project.repository.ExerciseRepository;
import com.project.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public void uploadRecordsFromFile(MultipartFile multipartFile) {

        log.info("Uploading records from CSV file...");

        List<Exercise> exercises = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (CSVReader reader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String[] line;

            while ((line = reader.readNext()) != null) {
                if (line[0].equalsIgnoreCase("source")) continue;

                if (line.length < 8) {
                    log.error("CSV row does not have the required number of columns!");
                    throw new IllegalArgumentException("CSV row does not have the required number of columns: " + Arrays.toString(line));
                }

                LocalDate fromDate = null;
                LocalDate toDate = null;

                try {
                    LocalDate localFromDate = LocalDate.parse(line[5], formatter);
                    fromDate = LocalDate.from(localFromDate);
                } catch (DateTimeParseException e) {
                    log.error("Invalid format provided for fromDate field!");
                    System.err.println("Invalid 'fromDate' format: " + line[5]);
                }

                try {
                    if (!line[6].isEmpty()) {
                        LocalDate localToDate = LocalDate.parse(line[6], formatter);
                        toDate = LocalDate.from(localToDate);
                    }
                } catch (DateTimeParseException e) {
                    log.error("Invalid format provided for toDate field!");
                    System.err.println("Invalid 'toDate' format: " + line[6]);
                }

                ExerciseDTO exerciseDTO = ExerciseDTO.builder()
                        .source(line[0])
                        .codeListCode(line[1])
                        .code(line[2])
                        .displayValue(line[3])
                        .longDescription(line[4])
                        .fromDate(fromDate)
                        .toDate(toDate)
                        .sortingPriority(line[7].isEmpty() ? null : Integer.parseInt(line[7]))
                        .build();

                exercises.add(exerciseMapper.mapToEntity(exerciseDTO));
            }

            exerciseRepository.saveAll(exercises);
            log.info("Exercise records has been saved into db...");
        } catch (Exception e) {
            log.error("Attempt to process the CSV file failed!");
            throw new RuntimeException("Error processing CSV file", e);
        }
    }

    @Override
    public List<ExerciseDTO> findAllRecords() {

        log.info("Fetching all exercise records from DB...");

        return exerciseMapper.mapToDto(exerciseRepository.findAll());
    }

    @Override
    public ExerciseDTO findRecordByCode(String code) {

        log.info("Finding exercise record By code {}", code);

        Exercise exercise = exerciseRepository.findByCode(code)
                .orElseThrow(() -> new RecordNotFoundException(code));
        return exerciseMapper.mapToDto(exercise);
    }

    @Override
    public void deleteAllRecords() {

        log.warn("Deleting all records from DB!");
        exerciseRepository.deleteAll();
    }

    @Override
    public void exportDataToCsv(Writer writer) {

        log.info("Exporting exercise records to CSV file in progress...");

        List<Exercise> exercises = exerciseRepository.findAll();

        try (CSVWriter csvWriter = new CSVWriter(writer)) {

            String[] header = {"Source", "CodeListCode", "Code", "DisplayValue", "LongDescription", "FromDate", "ToDate", "SortingPriority"};
            csvWriter.writeNext(header);

            for (Exercise exercise : exercises) {
                String[] row = {
                        exercise.getSource(),
                        exercise.getCodeListCode(),
                        exercise.getCode(),
                        exercise.getDisplayValue(),
                        exercise.getLongDescription(),
                        exercise.getFromDate() != null ? exercise.getFromDate().toString() : "",
                        exercise.getToDate() != null ? exercise.getToDate().toString() : "",
                        exercise.getSortingPriority() != null ? exercise.getSortingPriority().toString() : ""
                };
                csvWriter.writeNext(row);
            }

            log.info("Export finished successfully!");

        } catch (Exception e) {
            log.error("Attempt to process the CSV file failed!");
            throw new RuntimeException("Error exporting data to CSV", e);
        }
    }

    @Override
    public List<ExerciseDTO> findAllRecordsSorted() {

        log.info("Retrieving all exercise records from DB sorted by priority...");
        return exerciseMapper.mapToDto(exerciseRepository.findAllAndSortByPriority());
    }

    @Override
    public ExerciseDTO updateRecord(String code, ExerciseDTO exerciseDTO) {

        log.info("Updating exercise record...");

        Exercise exercise = exerciseRepository.findByCode(code)
                .orElseThrow(() -> new RecordNotFoundException(code));

        exercise.setSource(exerciseDTO.getSource());
        exercise.setCodeListCode(exerciseDTO.getCodeListCode());
        exercise.setCode(exerciseDTO.getCode());
        exercise.setDisplayValue(exerciseDTO.getDisplayValue());
        exercise.setLongDescription(exerciseDTO.getLongDescription());
        exercise.setFromDate(exerciseDTO.getFromDate());
        exercise.setToDate(exerciseDTO.getToDate());
        exercise.setSortingPriority(exerciseDTO.getSortingPriority());

        exerciseRepository.save(exercise);

        log.info("Record updated.");

        return exerciseMapper.mapToDto(exercise);
    }
}
