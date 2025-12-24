package com.nafifsource.hsp.service_performance.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "HistoricServicePerformanceLog",
        indexes = {
                @Index(name = "hsp_start_date_index", columnList = "collectionStartDate"),
                @Index(name = "hsp_end_date_index", columnList = "collectionEndDate"),
        })
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricServicePerformanceLog {

    @Id
    private LocalDate historicServicePerformanceDate;
    private LocalDateTime collectionStartDate;
    private LocalDateTime collectionEndDate;
    private Integer totalRows;
    private Integer processedRows;
    private Integer skippedRows;
    private Integer errorRows;
    private boolean isComplete;

}
