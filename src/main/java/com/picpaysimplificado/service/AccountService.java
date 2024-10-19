package com.picpaysimplificado.service;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.view.AccountDTO;

import java.util.Optional;

public interface AccountService {
    Optional<Account>createAccount(long idUser, AccountDTO accountDTO);

    Optional<Account>getAccount(long idAccount);
}
