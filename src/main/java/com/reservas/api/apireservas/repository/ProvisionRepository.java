package com.reservas.api.apireservas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.api.apireservas.model.Provision;

public interface ProvisionRepository extends JpaRepository <Provision, Long>{

    List<Provision> findByActiveTrue();
    Optional<Provision> findByIdAndActive(Long id, Boolean active);
}
