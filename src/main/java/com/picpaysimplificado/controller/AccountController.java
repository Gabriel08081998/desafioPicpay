package com.picpaysimplificado.controller;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.service.AccountService;
import com.picpaysimplificado.view.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/{idUser}")
    public ResponseEntity<?> createAccount(@PathVariable("idUser") long idUser, @RequestBody @Valid AccountDTO accountDTO) {
        try{
            Optional<Account> optionalAccount = accountService.createAccount(idUser,accountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalAccount.get());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{idAccount}")
    public ResponseEntity<?> getAccount(@PathVariable("idAccount") long idAccount) {
        try{
            Optional<Account> optionalAccount = accountService.getAccount(idAccount);
            return ResponseEntity.status(HttpStatus.OK).body(optionalAccount.get());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }}
