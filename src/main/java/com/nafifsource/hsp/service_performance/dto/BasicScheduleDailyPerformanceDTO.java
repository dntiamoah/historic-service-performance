package com.nafifsource.hsp.service_performance.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class BasicScheduleDailyPerformanceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String rid;
    private final LocalDate dateOfService;
    private final String tocCode;
    private final String originCrs;
    private final String terminateCrs;
    private final List<BasicScheduleLocationDailyPerformanceDTO> locations;
}
