package com.picpaysimplificado.service;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.model.TipoUsuario;
import com.picpaysimplificado.model.Transfer;
import com.picpaysimplificado.repository.AccountRepository;
import com.picpaysimplificado.repository.TransferRepository;
import com.picpaysimplificado.view.TransferDTO;
import com.picpaysimplificado.view.UserException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    @Transactional
    public Optional<Transfer> transfer(long idAccount, TransferDTO transfersDTO) {
        Optional<Account> optionalAccount = accountRepository.findById(idAccount);

        if (!optionalAccount.isPresent()) {
            throw new UserException("Conta inexistente");
        }
        Account account = optionalAccount.get();
//        if (!account.getCpfCnpj().equals(transfersDTO.getCpfCnpj())) {
//            throw new UserException("CPF/CNPJ inválido");
//        }
        if (account.getIdUser().getTipoUsuario() == TipoUsuario.PJ) {
            throw new UserException("Transferência não permitida para usuários do tipo PJ");
        }

        if (account.getValue() < transfersDTO.getValue()) {
            throw new UserException("Saldo insuficiente");
        }


        Optional<Account> destinationAccountOptional = accountRepository.findByCpfCnpj(transfersDTO.getCpfCnpj());
        if (!destinationAccountOptional.isPresent()) {
            throw new UserException("CPF/CNPJ de destino inexistente");
        }

        String authorizeUrl ="https://util.devi.tools/api/v2/authorize";
        ResponseEntity<Map> response = restTemplate.getForEntity(authorizeUrl, Map.class);
        if (!response.getStatusCode().is2xxSuccessful() || !Boolean.TRUE.equals(response.getBody().get("authorized"))) {
            throw new UserException("Transferência não autorizada pelo serviço externo");
        }

        Account destinationAccount = destinationAccountOptional.get();

        try {
            account.setValue(account.getValue() - transfersDTO.getValue());
            accountRepository.save(account);

            destinationAccount.setValue(destinationAccount.getValue() + transfersDTO.getValue());
            accountRepository.save(destinationAccount);

            Transfer transfer1 = new Transfer();
            BeanUtils.copyProperties(transfersDTO, transfer1);
            Transfer saveTransfer = transferRepository.save(transfer1);

            sendNotification(destinationAccount.getIdUser().getEmail(), "Você recebeu uma transferência.");


            return Optional.of(saveTransfer);
        }catch (Exception e) {
            throw new UserException("Erro na transferência:"+  e.getMessage());
        }

    }

    private void sendNotification(String email, String message) {
        String notifyUrl = "https://util.devi.tools/api/v1/notify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> notificationRequest = new HashMap<>();
        notificationRequest.put("email", email);
        notificationRequest.put("message", message);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(notificationRequest, headers);

        try {
            restTemplate.postForEntity(notifyUrl, requestEntity, Void.class);
        } catch (Exception e) {
            // Log the error or handle notification failure
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
    }







