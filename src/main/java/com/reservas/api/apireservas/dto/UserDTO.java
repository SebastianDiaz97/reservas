package com.reservas.api.apireservas.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record UserDTO(
    Long id,
    String name,
    String email,
    List<BookingDTO> bookings
) {

}
