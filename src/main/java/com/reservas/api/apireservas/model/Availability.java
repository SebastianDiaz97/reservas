package com.reservas.api.apireservas.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Availability {
    
    private Long id;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    private Professional professional;

}