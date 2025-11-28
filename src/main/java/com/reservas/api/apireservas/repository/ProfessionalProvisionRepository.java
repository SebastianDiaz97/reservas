package com.reservas.api.apireservas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.api.apireservas.model.ProfessionalProvision;

public interface ProfessionalProvisionRepository extends JpaRepository <ProfessionalProvision, Long>{

    Optional<ProfessionalProvision> findByProfessionalIdAndProvisionIdAndActive(Long professionalId, Long provisionId, Boolean active);
    List<ProfessionalProvision> findByProfessionalIdAndActiveTrue(Long professionalId);
    boolean existsByProfessionalIdAndProvisionId(Long professionalId, Long provisionId);
}
