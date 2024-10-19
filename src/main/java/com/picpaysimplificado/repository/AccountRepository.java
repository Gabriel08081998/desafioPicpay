package com.picpaysimplificado.repository;

import com.picpaysimplificado.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByCpfCnpj(String cpfCnpj);
}
