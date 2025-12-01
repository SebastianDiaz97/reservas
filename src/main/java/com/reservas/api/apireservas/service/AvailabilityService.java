package com.reservas.api.apireservas.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservas.api.apireservas.dto.AvailabilityDTO;
import com.reservas.api.apireservas.exception.NotFoundException;
import com.reservas.api.apireservas.mapper.Mapper;
import com.reservas.api.apireservas.model.Availability;
import com.reservas.api.apireservas.model.Professional;
import com.reservas.api.apireservas.repository.AvailabilityRepository;
import com.reservas.api.apireservas.repository.ProfessionalRepository;

@Service
public class AvailabilityService implements IAvailabilityService{

    @Autowired
    private AvailabilityRepository repository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Override
    public AvailabilityDTO createAvailability(Long professionalId,
                                              AvailabilityDTO availabilityDTO) {
        Optional<Professional> profeOptional = professionalRepository.findByIdAndActive(professionalId, true);
        if(profeOptional.isEmpty()) throw new NotFoundException("Profesional no encontrado");
        Professional professional = profeOptional.get();

        Availability availability = new Availability();
        availability.setActive(true);
        availability.setDayOfWeek(DayOfWeek.valueOf(availabilityDTO.dayOfWeek().toUpperCase()));
        availability.setStartTime(availabilityDTO.startTime());
        availability.setEndTime(availabilityDTO.endTime());
        availability.setProfessional(professional);
        availability = repository.save(availability);

        AvailabilityDTO aDto = Mapper.toDTO(availability);
        return aDto;
    
    }

    @Override
    public void deleteAvailability(Long id) {
        Optional<Availability> avOptional= repository.findById(id);
        if(avOptional.isEmpty()) throw new NotFoundException("Recurso no encontrado");
        Availability availability = avOptional.get();
        availability.setActive(false);
        repository.save(availability);
    }

    @Override
    public AvailabilityDTO modifyAvailability(Long availabilityId, AvailabilityDTO availabilityDTO) {
        Optional<Availability> avOptional= repository.findById(availabilityId);
        if(avOptional.isEmpty()) throw new NotFoundException("Recurso no encontrado");
        Availability availability = avOptional.get();

        if (!availabilityDTO.dayOfWeek().isEmpty()) {
            availability.setDayOfWeek(DayOfWeek.valueOf(availabilityDTO.dayOfWeek().toUpperCase()));
        }

        if (!availabilityDTO.startTime().toString().isEmpty()) {
            availability.setStartTime(availabilityDTO.startTime());
        }

        if (!availabilityDTO.endTime().toString().isEmpty()) {
            availability.setEndTime(availabilityDTO.endTime());
        }

        

        availability = repository.save(availability);
        return Mapper.toDTO(availability);

    }

    @Override
    public List<AvailabilityDTO> listProfessional(Long professionalId) {
        Professional p = professionalRepository.findById(professionalId).orElse(null);
        if(p== null) throw new NotFoundException("Profesional no encontrado");
        List<Availability> aList = repository.findByProfessionalIdAndActiveTrue(professionalId);

        List<AvailabilityDTO> aDtos = aList.stream().map(Mapper::toDTO).collect(Collectors.toList());
        return aDtos;
    }

}
