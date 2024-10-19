package com.picpaysimplificado.repository;

import com.picpaysimplificado.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpfCnpj(String cpfCnpj);
    Optional<User> findByEmail(String email);
    Optional<User> findByCpfCnpj(String cpfCnpj);
}
