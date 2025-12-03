package com.reservas.api.apireservas.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.api.apireservas.model.Availability;

public interface AvailabilityRepository extends JpaRepository <Availability, Long>{
    List<Availability> findByProfessionalIdAndActiveTrue(Long professionalId);
    Availability findByProfessionalIdAndDayOfWeekAndActiveTrue(Long professionalId, DayOfWeek dayOfWeek);
}
