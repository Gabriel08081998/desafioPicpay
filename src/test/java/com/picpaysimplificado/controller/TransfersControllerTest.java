package com.picpaysimplificado.controller;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.model.Transfer;
import com.picpaysimplificado.model.User;
import com.picpaysimplificado.repository.AccountRepository;
import com.picpaysimplificado.repository.TransferRepository;
import com.picpaysimplificado.service.TransferService;
import com.picpaysimplificado.view.TransferDTO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransfersControllerTest {

    public static final long ID = 1L;
    public static final String CPF_CNPJ = "123456789f";
    public static final int BANK_CODE = 238;
    public static final float VALUE = 123.45f;
    @InjectMocks
    private TransfersController transfersController;

    @Mock
    private TransferService transferService;

    @Mock
    private TransferRepository transferRepository;

    private TransferDTO transferDTO;

    private Transfer transfer;
//    private Optional<Transfer> optionalTransfers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTransfer();
    }

    @Test
    void transferCreated() {
        when(transferService.transfer(ID, transferDTO)).thenReturn(Optional.of(transfer));
        ResponseEntity<?> responseEntity = transfersController.transfer(ID,transferDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(transfer, responseEntity.getBody());


    }
    @Test
    void transferBadRequest() {
        when(transferService.transfer(ID, transferDTO)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = transfersController.transfer(ID,transferDTO);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());



    }
    private void startTransfer() {
        transfer = new Transfer(ID,new Account(), CPF_CNPJ, BANK_CODE, VALUE);
        transferDTO = new TransferDTO(ID, CPF_CNPJ, BANK_CODE, VALUE);

    }


}