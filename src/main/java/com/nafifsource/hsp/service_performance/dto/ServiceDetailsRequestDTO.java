/*
 * Copyright (c) 2025. Natif Source Ltd. (Daniel Ntiamoah) All rights reserved
 */

package com.nafifsource.hsp.service_performance.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class ServiceDetailsRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String rid;
}
