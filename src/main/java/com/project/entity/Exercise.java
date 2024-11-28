package com.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "exercise", name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "code_list_code", nullable = false)
    private String codeListCode;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "display_value")
    private String displayValue;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private LocalDate fromDate;

    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private LocalDate toDate;

    @Column(name = "sorting_priority")
    private Integer sortingPriority;

}

