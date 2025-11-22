package com.reservas.api.apireservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.api.apireservas.model.Booking;

public interface BookingRepository extends JpaRepository <Booking, Long>{

}
