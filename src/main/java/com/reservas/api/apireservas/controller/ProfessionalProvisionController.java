package com.reservas.api.apireservas.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservas.api.apireservas.dto.ProfessionalProvisionDTO;
import com.reservas.api.apireservas.service.IProfessionalProvisionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/professionals")
public class ProfessionalProvisionController {

    @Autowired
    private IProfessionalProvisionService ppService;

    @PostMapping("/{professionalId}/services/{provisionId}")
    public ResponseEntity<ProfessionalProvisionDTO> postRelation(@PathVariable Long professionalId, @PathVariable Long provisionId) {
        ProfessionalProvisionDTO pp = ppService.createRelation(professionalId, provisionId);
        String url = "/professionals" + pp.idProfessional() + "/services" + pp.idProvision();

        return ResponseEntity.created(URI.create(url)).body(pp);
    }

    @DeleteMapping("/{professionalId}/services/{provisionId}")
    public ResponseEntity<Void> deleteRelation(@PathVariable Long professionalId, @PathVariable Long provisionId){
        ppService.deleteRelation(professionalId, provisionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<List<ProfessionalProvisionDTO>> getListRelation(@PathVariable Long professionalId) {
        List<ProfessionalProvisionDTO> ppList = ppService.listRelations(professionalId);

        return ResponseEntity.ok(ppList);
    }
    
    
}
