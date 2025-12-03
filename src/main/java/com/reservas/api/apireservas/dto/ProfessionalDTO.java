package com.reservas.api.apireservas.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ProfessionalDTO(
    Long id,
    String name,
    String speciality,

    List<ProfessionalProvisionDTO> provision,

    List<AvailabilityDTO> availabilities,
    List<BookingDTO> bookings
) {
         
}
