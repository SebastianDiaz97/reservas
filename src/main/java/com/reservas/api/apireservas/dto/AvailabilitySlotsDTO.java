package com.reservas.api.apireservas.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AvailabilitySlotsDTO(
    LocalDate date,
    Long professionalId,
    Long serviceId,
    @JsonFormat(pattern = "HH:mm")
    List<LocalTime> availabilitySlots
) {

}
