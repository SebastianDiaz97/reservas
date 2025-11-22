package com.reservas.api.apireservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.api.apireservas.model.ProfessionalProvision;

public interface ProfessionalServiceRepository extends JpaRepository <ProfessionalProvision, Long>{

}
