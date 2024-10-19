package com.picpaysimplificado.service;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.model.Transfer;
import com.picpaysimplificado.view.TransferDTO;

import java.util.Optional;

public interface TransferService {
    Optional<Transfer> transfer(long idAccount, TransferDTO transfersDTO);
}
