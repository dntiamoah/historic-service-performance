package com.nafifsource.hsp.service_performance.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
public class DailyPerformanceServiceRID implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private final String trainUid;
    private final String rid;

}
