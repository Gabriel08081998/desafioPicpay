package com.picpaysimplificado.service;

import com.picpaysimplificado.model.TipoUsuario;
import com.picpaysimplificado.model.User;
import com.picpaysimplificado.repository.UserRepository;
import com.picpaysimplificado.view.UserDTO;
import com.picpaysimplificado.view.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String FULL_NAME = "Gabriel Oliveira";
    public static final String CPF_CNPJ = "124455768546";
    public static final String EMAIL = "gabrieloliveira@gmail.com";
    public static final String PASSWORD = "123456";
    public static final TipoUsuario TIPO_USUARIO = TipoUsuario.PF;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();

    }

    @Test
    void createUser() {


        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        // Verifica que o usuário foi criado com sucesso
        Optional<User> userOptional = userService.createUser(userDTO);
        assertTrue(userOptional.isPresent());
        assertEquals(ID, userOptional.get().getId());
        assertEquals(FULL_NAME, userOptional.get().getFullName());
        assertEquals(CPF_CNPJ, userOptional.get().getCpfCnpj());
        assertEquals(EMAIL, userOptional.get().getEmail());
        assertEquals(PASSWORD, userOptional.get().getPassword());
        assertEquals(TIPO_USUARIO, userOptional.get().getTipoUsuario());

        // Simula que o usuário já existe no banco de dados
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        // Verifica que a exceção é lançada quando tenta criar um usuário com email já cadastrado
        UserException exception = assertThrows(UserException.class, () -> userService.createUser(userDTO));
        assertEquals("Email ja cadastrado", exception.getMessage());
    }
    @Test
    void createUserCpfNotExists() {


        when(userRepository.findByCpfCnpj(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        // Verifica que o usuário foi criado com sucesso
        Optional<User> userOptional = userService.createUser(userDTO);
        assertTrue(userOptional.isPresent());
        assertEquals(ID, userOptional.get().getId());
        assertEquals(FULL_NAME, userOptional.get().getFullName());
        assertEquals(CPF_CNPJ, userOptional.get().getCpfCnpj());
        assertEquals(EMAIL, userOptional.get().getEmail());
        assertEquals(PASSWORD, userOptional.get().getPassword());
        assertEquals(TIPO_USUARIO, userOptional.get().getTipoUsuario());

        // Simula que o usuário já existe no banco de dados
        when(userRepository.findByCpfCnpj(any())).thenReturn(Optional.of(user));

        // Verifica que a exceção é lançada quando tenta criar um usuário com email já cadastrado
        UserException exception = assertThrows(UserException.class, () -> userService.createUser(userDTO));
        assertEquals("Cpf/Cnpj ja cadastrado", exception.getMessage());
    }

    @Test
    void  existsByEmail() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);
        boolean result = userService.existsByEmail(EMAIL);
        assertTrue(result);
    }
    @Test
    void  existsByEmailNotExist() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        boolean result = userService.existsByEmail(EMAIL);
        assertFalse(result);
    }
    @Test
    void  existsByCpfCnpj() {
        when(userRepository.existsByCpfCnpj(CPF_CNPJ)).thenReturn(true);
        boolean result = userService.existsByCpf(CPF_CNPJ);
        assertTrue(result);
    }
    @Test
    void  existsByCpfCnpjNotExist() {
        when(userRepository.existsByCpfCnpj(CPF_CNPJ)).thenReturn(false);
        boolean result = userService.existsByCpf(CPF_CNPJ);
        assertFalse(result);
    }





    private void startUser() {
        user =new User(ID, FULL_NAME, CPF_CNPJ, EMAIL, PASSWORD, TIPO_USUARIO);
        userDTO = new UserDTO(ID, FULL_NAME, CPF_CNPJ, EMAIL, PASSWORD, TIPO_USUARIO);

    }
}