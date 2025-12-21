/*
 * Copyright (c) 2025. Natif Source Ltd. (Daniel Ntiamoah) All rights reserved
 */

package com.nafifsource.hsp.service_performance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
@Builder
public class ServiceMetricsRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("from_loc")
    private String fromLOC;
    @JsonProperty("to_loc")
    private String toLOC;
    @JsonProperty("from_time")
    private String fromTime;
    @JsonProperty("to_time")
    private String toTime;
    @JsonProperty("from_date")
    private String fromDate;
    @JsonProperty("to_date")
    private String toDate;
}
