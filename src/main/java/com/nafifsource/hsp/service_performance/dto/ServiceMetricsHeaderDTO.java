/*
 * Copyright (c) 2025. Natif Source Ltd. (Daniel Ntiamoah) All rights reserved
 */

package com.nafifsource.hsp.service_performance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceMetricsHeaderDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("from_location")
    private String fromLocation;
    @JsonProperty("to_location")
    private String toLocation;
}
