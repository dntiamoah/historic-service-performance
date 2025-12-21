package com.nafifsource.hsp.service_performance.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicScheduleDailyPerformance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String rid;
    private LocalDate dateOfService;
    private String tocCode;
    private String originCrs;
    private String terminateCrs;
    private List<BasicScheduleLocationDailyPerformance> locations;
}
