package com.picpaysimplificado.repository;

import com.picpaysimplificado.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByDocument(String document);
    Optional<User> findUserByid(Long id);
}
