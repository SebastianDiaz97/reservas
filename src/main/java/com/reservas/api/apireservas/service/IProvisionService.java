package com.reservas.api.apireservas.service;

import java.util.List;

import com.reservas.api.apireservas.dto.ProvisionDTO;

public interface IProvisionService {
    ProvisionDTO createProvision(ProvisionDTO provisionDto);
    void deleteProvision(Long id);
    ProvisionDTO modifyProvision(Long id, ProvisionDTO provisionDto);
    List<ProvisionDTO> listProvisions();
    ProvisionDTO oneProvision(Long id);

}
