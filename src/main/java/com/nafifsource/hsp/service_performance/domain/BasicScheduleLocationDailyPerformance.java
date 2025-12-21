package com.nafifsource.hsp.service_performance.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Table(name = "BasicScheduleLocationDailyPerformance",
        indexes = {
                @Index(name = "hsp_lateCancelReason", columnList = "lateCancellationReason"),
        })
@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BasicScheduleLocationDailyPerformance implements Serializable {

    @EmbeddedId
    private BasicScheduleLocationDailyPerformanceId id;
    private String publishedTimeOfDeparture;
    private String publishedTimeOfArrival;
    private String actualTimeOfDeparture;
    private String actualTimeOfArrival;
    private String lateCancellationReason;
    private Integer callingPointOrder;

}
