package com.reservas.api.apireservas.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "professional_provision",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"professional_id", "provision_id"})
    }
)
public class ProfessionalProvision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Professional professional;

    @ManyToOne(fetch = FetchType.LAZY)
    private Provision provision;

    private Boolean active;

    @OneToMany(mappedBy = "professionalProvision", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
