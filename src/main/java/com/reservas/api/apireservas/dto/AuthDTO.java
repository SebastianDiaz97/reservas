package com.reservas.api.apireservas.dto;

import lombok.Builder;

@Builder
public record AuthDTO(
    String name,
    String email,
    String password
) {

}
