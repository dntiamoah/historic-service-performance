package com.nafifsource.hsp.service_performance.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Embeddable
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BasicScheduleLocationDailyPerformanceId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String rid;
    private String location;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
