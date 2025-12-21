package com.nafifsource.hsp.service_performance.dto;

import com.nafifsource.hsp.service_performance.domain.BasicScheduleLocationDailyPerformanceId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@SuperBuilder
public class BasicScheduleLocationDailyPerformanceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final BasicScheduleLocationDailyPerformanceId id;
    private final String publishedTimeOfDeparture;
    private final String publishedTimeOfArrival;
    private final String actualTimeOfDeparture;
    private final String actualTimeOfArrival;
    private final String lateCancellationReason;

}
