package com.reservas.api.apireservas.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reservas.api.apireservas.model.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Status status;

    @ManyToOne
    private User user;

    @ManyToOne
    private ProfessionalService professionalService;

}
