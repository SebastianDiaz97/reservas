package com.reservas.api.apireservas.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ProfessionalProvisionDTO(
    Long id,
    
    Long idProfessional,

    Long idProvision,

    List<BookingDTO> bookings
) {

}
