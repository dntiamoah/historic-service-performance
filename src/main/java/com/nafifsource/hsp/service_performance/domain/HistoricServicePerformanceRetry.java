package com.nafifsource.hsp.service_performance.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricServicePerformanceRetry {

    private LocalDate dateOfService;
    private boolean retry;

}
