package com.nafifsource.hsp.service_performance.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "HistoricServicePerformanceErrorResponse",
        indexes = {
                @Index(name = "hsp_error_trainuid", columnList = "trainuid"),
                @Index(name = "hsp_error_dateOfService", columnList = "dateOfService"),
        })
@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricServicePerformanceErrorResponse {

    @Id
    @Column(length = 15)
    private String rid;
    @Column(length = 6)
    private String trainuid;
    private String dateOfService;
    private String errorMessage;
    private String httpStatus;
    private Integer retries;
    private LocalDateTime errorTimeStamp;
    private LocalDateTime lastRetry;
    private boolean isSuccessful;

}
