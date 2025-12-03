package com.reservas.api.apireservas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservas.api.apireservas.dto.ProfessionalProvisionDTO;
import com.reservas.api.apireservas.exception.NotFoundException;
import com.reservas.api.apireservas.exception.ValidationException;
import com.reservas.api.apireservas.mapper.Mapper;
import com.reservas.api.apireservas.model.Professional;
import com.reservas.api.apireservas.model.ProfessionalProvision;
import com.reservas.api.apireservas.model.Provision;
import com.reservas.api.apireservas.repository.ProfessionalProvisionRepository;
import com.reservas.api.apireservas.repository.ProfessionalRepository;
import com.reservas.api.apireservas.repository.ProvisionRepository;

@Service
public class ProfessionalProvisionService implements IProfessionalProvisionService{

    @Autowired
    ProfessionalRepository professionalRepo;

    @Autowired
    ProvisionRepository provisionRepo;

    @Autowired
    ProfessionalProvisionRepository ppRepo;

    @Override
    public ProfessionalProvisionDTO createRelation(Long professionalId, Long provisionId) {
        Optional<Professional> professOptional = professionalRepo.findByIdAndActive(professionalId, true);
        if(professOptional.isEmpty()) throw new NotFoundException("Profesional no encontrado");
        Optional<Provision> provisOptional = provisionRepo.findByIdAndActive(provisionId, true);
        if(provisOptional.isEmpty()) throw new NotFoundException("Servicio no encontrado");

        boolean exists = ppRepo.existsByProfessionalIdAndProvisionId(professionalId, provisionId);
        if(exists) throw new ValidationException("Registro duplicado");

        Professional professional = professOptional.get();
        Provision provision = provisOptional.get();

        ProfessionalProvision pp = new ProfessionalProvision();
        pp.setActive(true);
        pp.setProfessional(professional);
        pp.setProvision(provision);
        pp = ppRepo.save(pp);

        return Mapper.toDto(pp);
    }
    @Override
    public void deleteRelation(Long professionalId, Long provisionId) {
        Optional<ProfessionalProvision> exists = ppRepo.findByProfessionalIdAndProvisionIdAndActive(professionalId, provisionId, true);
        if (exists.isEmpty()) throw new NotFoundException("Relacion no encontrada");
        
        ProfessionalProvision pp = exists.get();
        pp.setActive(false);

        ppRepo.save(pp);
    }
    @Override
    public List<ProfessionalProvisionDTO> listRelations(Long professionalId) {
        List<ProfessionalProvision> ppList = ppRepo.findByProfessionalIdAndActiveTrue(professionalId);

        List <ProfessionalProvisionDTO> ppListDtos = ppList.stream().map(l -> Mapper.toDto(l)).collect(Collectors.toList());
        return ppListDtos;
    }



    

}
