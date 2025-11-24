package com.reservas.api.apireservas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.api.apireservas.model.Professional;

public interface ProfessionalRepository extends JpaRepository <Professional, Long>{
    List<Professional> findByActiveTrue();
    Optional<Professional> findByIdAndActive(Long id, Boolean active);
}
