package com.picpaysimplificado.service;

import com.picpaysimplificado.model.User;
import com.picpaysimplificado.view.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<User> createUser(UserDTO userDTO) ;
}
