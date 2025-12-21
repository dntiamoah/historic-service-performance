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
public class ServiceAttributesDetailDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("date_of_service")
    private String dateOfService;
    @JsonProperty("toc_code")
    private String tocCode;
    private String rid;
    private List<LocationDTO> locations;
}
