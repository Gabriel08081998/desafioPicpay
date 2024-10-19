package com.picpaysimplificado.service;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.model.User;
import com.picpaysimplificado.repository.AccountRepository;
import com.picpaysimplificado.repository.UserRepository;
import com.picpaysimplificado.view.AccountDTO;
import com.picpaysimplificado.view.UserException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<Account> createAccount( long idUser ,AccountDTO accountDTO) {
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (!optionalUser.isPresent()) {
            throw new UserException("Usário não encontrado");
        }
        if (!accountDTO.getCpfCnpj().equals(optionalUser.get().getCpfCnpj())) {
            throw new UserException("CPF/CNPJ inválido");

        }

        Optional<Account> optionalAccount = accountRepository.findByCpfCnpj(accountDTO.getCpfCnpj());
        if (optionalAccount.isPresent()) {
            throw new UserException("Conta ja existente");
        }

        User user = optionalUser.get();

        Account account= Account.builder()
                .idUser(user)
                .cpfCnpj(user.getCpfCnpj())
                .bankCode(accountDTO.getBankCode())
                .value(accountDTO.getValue())
                .build();



        BeanUtils.copyProperties(accountDTO, account);
        account.setIdUser(user);
        return Optional.of(accountRepository.save(account));
    }

    @Override
    public Optional<Account> getAccount(long idAccount) {
        Optional<Account> optionalAccount = accountRepository.findById(idAccount);
        return optionalAccount;
    }

}
