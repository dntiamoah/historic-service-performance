package com.nafifsource.hsp.service_performance.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Builder
@Getter
public class TimetableBasicSchedule implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private final String trainUid;
    private final String originTipLocCode;
    private final String originCrsCode;
    private final String originStationName;
    private final String originPublicDepartureTime;
    private final String originScheduledDepartureTime;
    private final String terminateTipLocCode;
    private final String terminateCrsCode;
    private final String terminateStationName;
    private final String extractDate;
    private final String scheduleId;
    private final String daysRun;
    private final String dateRunsFrom;
    private final String dateRunsTo;
}
