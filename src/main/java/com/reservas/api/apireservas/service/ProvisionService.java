package com.reservas.api.apireservas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservas.api.apireservas.dto.ProvisionDTO;
import com.reservas.api.apireservas.exception.NotFoundException;
import com.reservas.api.apireservas.mapper.Mapper;
import com.reservas.api.apireservas.model.Provision;
import com.reservas.api.apireservas.repository.ProvisionRepository;


@Service
public class ProvisionService implements IProvisionService{

    @Autowired
    private ProvisionRepository pRepository;

    @Override
    public ProvisionDTO createProvision(ProvisionDTO provisionDto) {
        Provision provision = Mapper.dtoTo(provisionDto);
        provision.setActive(true);
        provision = pRepository.save(provision);
        ProvisionDTO pDto = Mapper.toDTO(provision);
        return pDto;
    }

    @Override
    public void deleteProvision(Long id) {
        Provision p = pRepository.findById(id).orElse(null);
        if (p == null) throw new NotFoundException("Servicio no encontrado");
        p.setActive(false);
        pRepository.save(p);
    }

    @Override
    public ProvisionDTO modifyProvision(Long id, ProvisionDTO provisionDto) {
        Provision p = pRepository.findById(id).orElse(null);
        if (p == null) throw new NotFoundException("Servicio no encontrado");
        if (provisionDto.name()!=null) p.setName(provisionDto.name());
        if(provisionDto.durationMinutes() != 0) p.setDurationMinutes(provisionDto.durationMinutes());
        if(provisionDto.price() != 0) p.setPrice(provisionDto.price());
        pRepository.save(p);
        return Mapper.toDTO(p);
    }

    @Override
    public List<ProvisionDTO> listProvisions() {
        List<Provision> listProvisions = pRepository.findByActiveTrue();

        List<ProvisionDTO> listDtos = listProvisions.stream()
            .map(p -> Mapper.toDTO(p)).collect(Collectors.toList());
        
        return listDtos;
    }

    @Override
    public ProvisionDTO oneProvision(Long id) {
        Optional<Provision> provision = pRepository.findByIdAndActive(id, true);

        if (provision.isPresent()) {
            Provision p = provision.get();
            return Mapper.toDTO(p);
        }else{
            throw new NotFoundException("Servicio no encontrado");
        }
    }
}
