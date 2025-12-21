/*
 * Copyright (c) 2025. Natif Source Ltd. (Daniel Ntiamoah) All rights reserved
 */

package com.nafifsource.hsp.service_performance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ServiceAttributesDetailsResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("serviceAttributesDetails")
    private ServiceAttributesDetailDTO serviceAttributesDetailDTO;
}
