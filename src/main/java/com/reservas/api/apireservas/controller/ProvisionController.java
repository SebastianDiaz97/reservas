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

import com.reservas.api.apireservas.dto.ProvisionDTO;
import com.reservas.api.apireservas.service.IProvisionService;

@RestController
@RequestMapping("/services")
public class ProvisionController {

    @Autowired
    private IProvisionService provisionService;

    @GetMapping
    public ResponseEntity<List<ProvisionDTO>> getAllProvisions() {
        return ResponseEntity.ok(provisionService.listProvisions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvisionDTO> getOneProvision(@PathVariable Long id) {
        return ResponseEntity.ok(provisionService.oneProvision(id));
    }

    @PostMapping
    public ResponseEntity<ProvisionDTO> postProvision(@RequestBody ProvisionDTO dto) {
        ProvisionDTO create = provisionService.createProvision(dto);

        return ResponseEntity.created(URI.create("/service" + create.id())).body(create);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delteProvision(@PathVariable Long id) {
        provisionService.deleteProvision(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvisionDTO> putProvision(@PathVariable Long id, @RequestBody ProvisionDTO p) {
        return ResponseEntity.ok(provisionService.modifyProvision(id, p));
    }
    
    
    
    
}
