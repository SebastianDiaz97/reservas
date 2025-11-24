package com.reservas.api.apireservas.service;

import java.util.List;

import com.reservas.api.apireservas.dto.ProfessionalDTO;

public interface IProfessionaService {
    ProfessionalDTO createProfessional(ProfessionalDTO professionalDto);
    void deleteProfessional(Long id);
    ProfessionalDTO modifyProfessional(Long id, ProfessionalDTO professionalDTO);
    List<ProfessionalDTO> listProfessional();
    ProfessionalDTO oneProfessional(Long id);
}
