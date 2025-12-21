/*
 * Copyright (c) 2025. Natif Source Ltd. (Daniel Ntiamoah) All rights reserved
 */

package com.nafifsource.hsp.service_performance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
public class MetricDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("tolerance_value")
    private String toleranceValue;
    @JsonProperty("num_not_tolerance")
    private String numNotTolerance;
    @JsonProperty("num_tolerance")
    private String numTolerance;
    @JsonProperty("percent_tolerance")
    private String percentTolerance;
    @JsonProperty("global_tolerance")
    private boolean globalTolerance;
}
