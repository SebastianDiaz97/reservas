package com.reservas.api.apireservas.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "professional_service")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Professional professional;

    @ManyToOne
    private Service service;

    @OneToMany(mappedBy = "professionalService", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
