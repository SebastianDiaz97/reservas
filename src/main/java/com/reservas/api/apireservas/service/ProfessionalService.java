package com.reservas.api.apireservas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservas.api.apireservas.dto.ProfessionalDTO;
import com.reservas.api.apireservas.exception.NotFoundException;
import com.reservas.api.apireservas.mapper.Mapper;
import com.reservas.api.apireservas.model.Professional;
import com.reservas.api.apireservas.repository.ProfessionalRepository;

@Service
public class ProfessionalService implements IProfessionaService{
    
    @Autowired
    private ProfessionalRepository repository;

    @Override
    public ProfessionalDTO createProfessional(ProfessionalDTO professionalDto) {
        Professional professional = Mapper.dtoTo(professionalDto);
        professional.setActive(true);
        professional = repository.save(professional);
        ProfessionalDTO pDto = Mapper.toDTO(professional);
        return pDto;
    }

    @Override
    public void deleteProfessional(Long id) {
        Professional p = repository.findById(id).orElse(null);
        if(p == null) throw new NotFoundException("Profesional no encontrado");
        p.setActive(false);
        repository.save(p);
    }

    @Override
    public ProfessionalDTO modifyProfessional(Long id, ProfessionalDTO professionalDTO) {
        Professional p = repository.findById(id).orElse(null);
        if(p== null) throw new NotFoundException("Profesional no encontrado");
        if(professionalDTO.name() != null) p.setName(professionalDTO.name());
        if(professionalDTO.speciality() != null) p.setSpeciality(professionalDTO.speciality());
        return Mapper.toDTO(p);

    }


    @Override
    public List<ProfessionalDTO> listProfessional() {
        List<Professional> listProfessionals = repository.findByActiveTrue();
        List<ProfessionalDTO> listDtos = listProfessionals.stream()
            .map(p -> Mapper.toDTO(p)).collect(Collectors.toList());
        return listDtos;
    }

    @Override
    public ProfessionalDTO oneProfessional(Long id) {
        Optional<Professional> professional = repository.findByIdAndActive(id, true);
        if (professional.isPresent()) {
            Professional p = professional.get();
            return Mapper.toDTO(p);
        }else{
            throw new NotFoundException("Profesional no encontrado");
        }
    }

    

}
