package com.reservas.api.apireservas.dto;

import java.time.LocalTime;

import lombok.Builder;

@Builder
public record AvailabilityDTO(
    Long id,
    String dayOfWeek,
    LocalTime startTime,
    LocalTime endTime,
    Boolean active,

    Long idProfessional
) {

}
