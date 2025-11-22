package com.reservas.api.apireservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.api.apireservas.model.Provision;

public interface ProvisionRepository extends JpaRepository <Provision, Long>{

}
