package com.reservas.api.apireservas.dto;

import lombok.Builder;

@Builder
public record ProfessionalProvisionDTO(
    Long id,
    
    Long idProfessional,

    Long idProvision,

    Boolean active

) {

}
