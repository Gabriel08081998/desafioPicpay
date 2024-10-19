package com.picpaysimplificado.controller;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.model.Transfer;
import com.picpaysimplificado.service.TransferService;
import com.picpaysimplificado.view.TransferDTO;
import com.picpaysimplificado.view.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("transfers")
public class TransfersController {
    @Autowired
    private TransferService transferService;





    @PostMapping("/{idAccount}")
    public ResponseEntity<?> transfer(@PathVariable("idAccount") long idAccount,@RequestBody @Valid TransferDTO transferDTO) {
        try {
            Optional<Transfer> optionalTransfers = transferService.transfer(idAccount, transferDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(optionalTransfers.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
    }
    }
}



