package com.reservas.api.apireservas.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reservas.api.apireservas.dto.AvailabilityDTO;
import com.reservas.api.apireservas.service.IAvailabilityService;

@RestController
public class AvailabilityController {

    @Autowired
    private IAvailabilityService service;


    @GetMapping("/professionals/{professionalId}/availability")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilityProfessional(@PathVariable Long professionalId) {
        List<AvailabilityDTO> list = service.listProfessional(professionalId);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/professionals/{professionalId}/availability")
    public ResponseEntity<AvailabilityDTO> postAvailability(@PathVariable Long professionalId, @RequestBody AvailabilityDTO dto) {
        AvailabilityDTO availabilityDTO = service.createAvailability(professionalId, dto);
        String url = "/professionals/" + availabilityDTO.idProfessional() + "/availability";

        return ResponseEntity.created(URI.create(url)).body(availabilityDTO);
    }
    
    @PutMapping("/availability/{id}")
    public ResponseEntity<AvailabilityDTO> putAvailability(@PathVariable Long id, @RequestBody AvailabilityDTO dto) {
        AvailabilityDTO availabilityDTO = service.modifyAvailability(id, dto);
        
        return ResponseEntity.ok(availabilityDTO);
    }


    @DeleteMapping("/availability/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id){
        service.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }
}
