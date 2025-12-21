package com.nafifsource.hsp.service_performance.dto;

import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@SuperBuilder
public class BasicScheduleLocationDailyPerformanceDTOId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String rid;
    private final String location;
}
