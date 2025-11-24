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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservas.api.apireservas.dto.ProfessionalDTO;
import com.reservas.api.apireservas.service.IProfessionaService;



@RestController
@RequestMapping("/professionals")
public class ProfessionalController {

    @Autowired
    private IProfessionaService service;

    @GetMapping
    public ResponseEntity<List<ProfessionalDTO>> getAllProfessionals() {
        return ResponseEntity.ok(service.listProfessional());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalDTO> getOneProfessional(@PathVariable Long id) {
        return ResponseEntity.ok(service.oneProfessional(id));
    }

    @PostMapping
    public ResponseEntity<ProfessionalDTO> postProfessional(@RequestBody ProfessionalDTO dto) {
        ProfessionalDTO create = service.createProfessional(dto);
        return ResponseEntity.created(URI.create("/professional" + create.id())).body(create);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delteProvision(@PathVariable Long id) {
        service.deleteProfessional(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionalDTO> putProvision(@PathVariable Long id, @RequestBody ProfessionalDTO p) {
        return ResponseEntity.ok(service.modifyProfessional(id, p));
    }
    
    
    
}
