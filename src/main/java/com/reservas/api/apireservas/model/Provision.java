package com.reservas.api.apireservas.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int durationMinutes;
    private int price;
    private Boolean active;

    @OneToMany(mappedBy = "provision", cascade = CascadeType.ALL)
    private List<ProfessionalProvision> professionals;

    @OneToMany(mappedBy = "provision", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
