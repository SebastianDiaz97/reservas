package com.reservas.api.apireservas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservas.api.apireservas.dto.UserDTO;
import com.reservas.api.apireservas.exception.NotFoundException;
import com.reservas.api.apireservas.mapper.Mapper;
import com.reservas.api.apireservas.model.User;
import com.reservas.api.apireservas.repository.UserRepository;


@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<UserDTO> listUsers() {
        List<User> users = repository.findAll();
        return users.stream().map(Mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO oneUser(Long id) {
        User user = repository.findById(id).orElse(null);
        if(user == null) throw new NotFoundException("Usuario no encontrado");
        return Mapper.toDto(user);
    }

}
