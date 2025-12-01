package com.reservas.api.apireservas.service;

import java.util.List;

import com.reservas.api.apireservas.dto.UserDTO;

public interface IUserService {
    List<UserDTO> listUsers();
    UserDTO oneUser(Long id);
}
