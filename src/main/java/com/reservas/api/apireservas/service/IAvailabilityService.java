package com.reservas.api.apireservas.service;

import java.util.List;

import com.reservas.api.apireservas.dto.AvailabilityDTO;

public interface IAvailabilityService {
    AvailabilityDTO createAvailability(Long professionalId, AvailabilityDTO availabilityDTO);
    void deleteAvailability(Long id);
    AvailabilityDTO modifyAvailability(Long availabilityId, AvailabilityDTO availabilityDTO);
    List<AvailabilityDTO> listProfessional(Long professionalId);
}
