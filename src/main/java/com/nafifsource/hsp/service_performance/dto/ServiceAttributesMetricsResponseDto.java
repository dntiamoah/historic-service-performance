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
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAttributesMetricsResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("header")
    private ServiceMetricsHeaderDTO header;
    @JsonProperty("Services")
    private List<ServiceDTO> services;
}
