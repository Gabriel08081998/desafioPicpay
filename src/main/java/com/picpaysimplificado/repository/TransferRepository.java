package com.picpaysimplificado.repository;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.model.Transfer;
import org.springframework.data.repository.CrudRepository;

public interface TransferRepository extends CrudRepository<Transfer, Long> {
}
