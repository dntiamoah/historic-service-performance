/*
 * Copyright (c) 2025. Natif Source Ltd. (Daniel Ntiamoah) All rights reserved
 */

package com.nafifsource.hsp.service_performance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class ServiceAttributesMetricDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("origin_location")
    private String originLocation;
    @JsonProperty("destination_location")
    private String destinationLocation;
    @JsonProperty("gbtt_ptd")
    private String gbtt_ptd;
    @JsonProperty("gbtt_pta")
    private String gbtt_pta;
    @JsonProperty("toc_code")
    private String tocCode;
    @JsonProperty("matched_services")
    private String matchedServices;
    @JsonProperty("rids")
    private List<String> rids;
}
