package com.reservas.api.apireservas.service;

import java.util.List;

import com.reservas.api.apireservas.dto.ProfessionalProvisionDTO;

public interface IProfessionalProvisionService {
    ProfessionalProvisionDTO createRelation(Long professionalId, Long provisionId);
    void deleteRelation(Long professionalId, Long provisionId);
    List<ProfessionalProvisionDTO> listRelations(Long professionalId);
}
