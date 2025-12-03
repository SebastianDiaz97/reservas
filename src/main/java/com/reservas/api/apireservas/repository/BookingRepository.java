package com.reservas.api.apireservas.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reservas.api.apireservas.model.Booking;

public interface BookingRepository extends JpaRepository <Booking, Long>{
    List<Booking> findByUserId(Long userId);

    @Query("""
    SELECT r FROM Booking r
    WHERE r.professional.id = :professionalId
      AND r.date = :date
      AND r.startTime < :fin
      AND r.endTime > :inicio
      AND r.status = 0
""")
    List<Booking> findOverlaps( 
        Long professionalId,
        LocalDate date,
        LocalTime inicio,
        LocalTime fin
    );
}
