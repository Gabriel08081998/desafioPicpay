package com.picpaysimplificado.controller;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.model.User;
import com.picpaysimplificado.repository.AccountRepository;
import com.picpaysimplificado.service.AccountService;
import com.picpaysimplificado.view.AccountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    public static final long ID = 1L;
    public static final User ID_USER = new User();
    public static final String CPF_CNPJ = "123456789";
    public static final int BANK_CODE = 238;
    public static final float VALUE = 123.45f;
    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    private Account account;
    private AccountDTO accountDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAccount();
    }

    @Test
    void createAccount() {
        when(accountService.createAccount(ID_USER.getId(), accountDTO)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = accountController.createAccount(ID_USER.getId(), accountDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void createAccountCreated() {
        when(accountService.createAccount(ID_USER.getId(), accountDTO)).thenReturn(Optional.of(account));
        ResponseEntity<?> responseEntity = accountController.createAccount(ID_USER.getId(), accountDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @Test
    void getAccount()  throws Exception {
        when(accountService.getAccount(anyLong())).thenReturn(Optional.of(account));
        ResponseEntity<?> responseEntity = accountController.getAccount(anyLong());
        assertNotNull(responseEntity);

        assertEquals (HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void getAccountBadRequest()  throws Exception {
        when(accountService.getAccount(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = accountController.getAccount(anyLong());
        assertNotNull(responseEntity);

        assertEquals (HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    private void startAccount() {
        account = new Account(ID, ID_USER, CPF_CNPJ, BANK_CODE, VALUE);
        accountDTO = new AccountDTO(CPF_CNPJ, BANK_CODE, VALUE);
    }
}