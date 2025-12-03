package com.reservas.api.apireservas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reservas.api.apireservas.model.enums.Status;

import lombok.Builder;

@Builder
public record BookingDTO(
    Long id,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime,
    Status status,

    Long userId,
    
    Long professionalId,
    Long serviceId
) {
    
}
