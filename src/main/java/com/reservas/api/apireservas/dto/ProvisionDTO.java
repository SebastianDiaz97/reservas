package com.reservas.api.apireservas.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ProvisionDTO(
    Long id,
    String name,
    int durationMinutes,
    int price,

    List<ProfessionalProvisionDTO> professionals
) {

}
