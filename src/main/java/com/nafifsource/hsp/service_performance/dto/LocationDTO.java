/*
 * Copyright (c) 2025. Natif Source Ltd. (Daniel Ntiamoah) All rights reserved
 */

package com.nafifsource.hsp.service_performance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class LocationDTO extends ResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("location")
    private String location;
    @JsonProperty("gbtt_ptd")
    private String published_gbtt_ptd;
    @JsonProperty("gbtt_pta")
    private String published_gbtt_pta;
    @JsonProperty("actual_td")
    private String actual_td;
    @JsonProperty("actual_ta")
    private String actual_ta;
    @JsonProperty("late_canc_reason")
    private String lateCancelReason;
}
