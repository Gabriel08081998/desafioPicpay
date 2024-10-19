package com.picpaysimplificado.service;

import com.picpaysimplificado.model.User;
import com.picpaysimplificado.repository.UserRepository;
import com.picpaysimplificado.view.UserDTO;
import com.picpaysimplificado.view.UserException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> createUser(UserDTO userDTO)  {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        Optional<User> optionalCpf = userRepository.findByCpfCnpj(userDTO.getCpfCnpj());

        if (!optionalUser.isEmpty()) {
                throw new UserException("Email ja cadastrado");

        } else if (!optionalCpf.isEmpty()) {
                throw new UserException("Cpf/Cnpj ja cadastrado");

        }

        User user = User.builder().build();
        BeanUtils.copyProperties(userDTO, user);
        return Optional.of(userRepository.save(user));
    }
    public  boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public boolean existsByCpf (String cpfCnpj) {
        return userRepository.existsByCpfCnpj(cpfCnpj);
    }
}
