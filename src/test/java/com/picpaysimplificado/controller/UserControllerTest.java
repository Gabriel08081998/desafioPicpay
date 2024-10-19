package com.picpaysimplificado.controller;

import com.picpaysimplificado.model.TipoUsuario;
import com.picpaysimplificado.model.User;
import com.picpaysimplificado.service.UserService;
import com.picpaysimplificado.view.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    public static final long ID = 1l;
    public static final String FULL_NAME = "Marcos silva";
    public static final String CPF_CNPJ = "123456789";
    public static final String EMAIL = "teste@gmail.com";
    public static final String PASSWORD = "123456";
    public static final TipoUsuario TIPO_USUARIO = TipoUsuario.PF;
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();

    }

    @Test
    void createUserCreated() {
        when(userService.createUser(any())).thenReturn(optionalUser);
        ResponseEntity<?> response = userController.createUser(userDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    void createUser() {
        when(userService.createUser(any())).thenReturn(Optional.empty());
        ResponseEntity<?> response = userController.createUser(userDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private void startUser(){
        user = new User(ID, FULL_NAME, CPF_CNPJ, EMAIL, PASSWORD, TIPO_USUARIO);
        userDTO = new UserDTO(ID, FULL_NAME, CPF_CNPJ, EMAIL, PASSWORD, TIPO_USUARIO);
        optionalUser = Optional.of(new User(ID, FULL_NAME, CPF_CNPJ, EMAIL, PASSWORD, TIPO_USUARIO));
    }
}