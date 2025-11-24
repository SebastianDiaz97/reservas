package com.reservas.api.apireservas.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.reservas.api.apireservas.dto.AvailabilityDTO;
import com.reservas.api.apireservas.dto.BookingDTO;
import com.reservas.api.apireservas.dto.ProfessionalDTO;
import com.reservas.api.apireservas.dto.ProfessionalProvisionDTO;
import com.reservas.api.apireservas.dto.ProvisionDTO;
import com.reservas.api.apireservas.dto.UserDTO;
import com.reservas.api.apireservas.model.Availability;
import com.reservas.api.apireservas.model.Booking;
import com.reservas.api.apireservas.model.Professional;
import com.reservas.api.apireservas.model.ProfessionalProvision;
import com.reservas.api.apireservas.model.Provision;
import com.reservas.api.apireservas.model.User;

public class Mapper {

    // mapper de Provision a ProvisionDTO

    public static ProvisionDTO toDTO(Provision p) {

        if (p == null) return null;

        return ProvisionDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .durationMinutes(p.getDurationMinutes())
                .price(p.getPrice())
                .build();
    }    


    public static Provision dtoTo(ProvisionDTO p){
        if (p == null) return null;
        
        return Provision.builder()
                .name(p.name())
                .durationMinutes(p.durationMinutes())
                .price(p.price())
                .build();
    }

    // mapper User a UserDTO

    public static UserDTO toDto(User u){
        if(u == null) return null;

        List<BookingDTO> details = u.getBookings().stream().map(det ->
                toDTO(det)).collect(Collectors.toList());

        return UserDTO.builder()
            .id(u.getId())
            .name(u.getName())
            .email(u.getEmail())
            .bookings(details)
            .build();
    }

    // mapper Booking a BookingDTO

    public static BookingDTO toDTO(Booking b){
        if(b == null) return null;

        return BookingDTO.builder()
            .id(b.getId())
            .date(b.getDate())
            .startTime(b.getStartTime())
            .endTime(b.getEndTime())
            .idUser(b.getUser().getId())
            .idProfessionalProvision(b.getProfessionalProvision().getId())
            .build();
    }


    // mapper ProPro to dto

    public static ProfessionalProvisionDTO toDto(ProfessionalProvision p){
        if (p == null) return null;

        List<BookingDTO> details = p.getBookings().stream().map(det ->
                toDTO(det)).collect(Collectors.toList());


        return ProfessionalProvisionDTO.builder()
            .id(p.getId())
            .idProfessional(p.getProfessional().getId())
            .idProvision(p.getProvision().getId())
            .bookings(details)
            .build();
    }

    // mapper Professional a DTO

    public static ProfessionalDTO toDTO(Professional p){
        if (p==null) return null;

        List<ProfessionalProvisionDTO> detailsProvision = Collections.emptyList();
        List<AvailabilityDTO> detailsAvailability = Collections.emptyList();

        if (p.getProvision() != null) {
                detailsProvision = p.getProvision().stream().map(det ->
                toDto(det)).collect(Collectors.toList());
        }
        if (p.getAvailabilities() != null) {
                detailsAvailability = p.getAvailabilities().stream().map(det ->
                        toDTO(det)).collect(Collectors.toList());
        }
        return ProfessionalDTO.builder()
            .id(p.getId())
            .name(p.getName())
            .speciality(p.getSpeciality())
            .provision(detailsProvision)
            .availabilities(detailsAvailability)
            .build();
    }

    public static Professional dtoTo(ProfessionalDTO p){
        if (p == null) return null;

        return Professional.builder()
                .name(p.name())
                .speciality(p.speciality())
                .build();
    }
    // mapper Availability a DTO

    public static AvailabilityDTO toDTO(Availability a){
        if(a==null) return null;

        return AvailabilityDTO.builder()
            .id(a.getId())
            .dayOfWeek(a.getDayOfWeek())
            .startTime(a.getStartTime())
            .endTime(a.getEndTime())
            .idProfessional(a.getProfessional().getId())
            .build();
    }
}
