package com.reservas.api.apireservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.api.apireservas.model.User;

public interface UserRepository extends JpaRepository <User, Long>{

}
