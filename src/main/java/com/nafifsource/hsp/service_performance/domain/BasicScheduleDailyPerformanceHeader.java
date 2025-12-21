package com.nafifsource.hsp.service_performance.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "BasicScheduleDailyPerformance",
        indexes = {
                @Index(name = "hsp_date", columnList = "dateOfService"),
                @Index(name = "hsp_toc", columnList = "tocCode"),
        })
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicScheduleDailyPerformanceHeader implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String rid;
    private LocalDate dateOfService;
    private String tocCode;
    private String originCrs;
    private String terminateCrs;
}
