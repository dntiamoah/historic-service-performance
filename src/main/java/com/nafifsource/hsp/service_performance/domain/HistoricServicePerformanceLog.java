package com.nafifsource.hsp.service_performance.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "HistoricServicePerformanceLog",
        indexes = {
                @Index(name = "hsp_date_index", columnList = "historicServicePerformanceDate"),
        })
@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class HistoricServicePerformanceLog {

    @Id
    private final LocalDate historicServicePerformanceDate;
    private final LocalDateTime collectionStartDate;
    private final LocalDateTime collectionEndDate;
    private final Integer totalRows;
    private final Integer processedRows;
    private final Integer skippedRows;
    private final Integer errorRows;
    private final boolean isComplete;

}
