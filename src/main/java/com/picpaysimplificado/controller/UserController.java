package com.picpaysimplificado.controller;

import com.picpaysimplificado.model.User;
import com.picpaysimplificado.repository.UserRepository;
import com.picpaysimplificado.service.UserService;
import com.picpaysimplificado.view.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO){
        try {
            Optional<User> optionalUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
