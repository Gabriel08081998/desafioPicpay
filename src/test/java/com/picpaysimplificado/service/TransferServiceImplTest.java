package com.picpaysimplificado.service;

import com.picpaysimplificado.model.Account;
import com.picpaysimplificado.model.TipoUsuario;
import com.picpaysimplificado.model.Transfer;
import com.picpaysimplificado.model.User;
import com.picpaysimplificado.repository.AccountRepository;
import com.picpaysimplificado.repository.TransferRepository;
import com.picpaysimplificado.view.TransferDTO;
import com.picpaysimplificado.view.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    public static final long ID = 1L;
    public static final String CPF_CNPJ = "1234567896";
    public static final int BANK_CODE = 238;
    public static final float VALUE = 123.45f;
    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TipoUsuario tipoUsuario;

    private TransferDTO transferDTO;
    private Transfer transfer;
    private Account account;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTransfer();
    }

    @Test
    void transferAccoutNotExixst() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> transferService.transfer(ID, transferDTO));
        assertEquals("Conta inexistente", exception.getMessage());

    }
    @Test
    void transferUserTypePJ() {
        User user = new User();
        user.setTipoUsuario(TipoUsuario.PJ);
        account.setIdUser(user);

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));

        UserException exception = assertThrows(UserException.class, () ->{
            transferService.transfer(ID, transferDTO);
        });
        assertEquals("Transferência não permitida para usuários do tipo PJ", exception.getMessage());

    }

    @Test
    void testTransferInsufficientBalance(){
        account.setValue(50.0f);
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        UserException exception = assertThrows(UserException.class,() -> {
            transferService.transfer(ID, transferDTO);
        });
        assertEquals("Saldo insuficiente", exception.getMessage());
    }
    @Test
    void testTransferDestinationAccountNotExist(){
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(accountRepository.findByCpfCnpj(anyString())).thenReturn(Optional.empty());
        UserException exception = assertThrows(UserException.class,() -> {
            transferService.transfer(ID, transferDTO);
        });
        assertEquals("CPF/CNPJ de destino inexistente", exception.getMessage());
    }

    @Test
    void testTransferNotAuthorized(){
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(accountRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(new Account()));

        Map<String,Boolean> reponseBody = new HashMap<>();
        reponseBody.put("authorized", false);

        ResponseEntity<Map> response = new ResponseEntity<>(reponseBody, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenReturn(response);
        UserException exception = assertThrows(UserException.class,() -> {
            transferService.transfer(ID, transferDTO);
        });
        assertEquals("Transferência não autorizada pelo serviço externo", exception.getMessage());
    }


    @Test
    void testSuccessfulTransfer(){
        // Inicialize o usuário e a conta de origem
        User originUser = new User();
        originUser.setEmail("origin@example.com");

        Account originAccount = new Account();
        originAccount.setIdUser(originUser);

        // Inicialize a conta de destino
        User destinationUser = new User();
        destinationUser.setEmail("destination@example.com");

        Account destinationAccount = new Account();
        destinationAccount.setIdUser(destinationUser);

        // Configure o DTO de transferência
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setCpfCnpj("12345678900"); // CPF/CNPJ de destino
        // Configure outros campos necessários do transferDTO

        // Mock do repositório para conta de origem
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(originAccount));

        // Mock do repositório para conta de destino
        when(accountRepository.findByCpfCnpj("12345678900")).thenReturn(Optional.of(destinationAccount));

        // Mock da resposta do template rest
        Map<String, Boolean> responseBody = new HashMap<>();
        responseBody.put("authorized", true);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenReturn(responseEntity);

        // Mock do repositório de transferência
        Transfer transfer = new Transfer();
        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);

        // Chame o serviço de transferência
        Optional<Transfer> optionalTransfer = transferService.transfer(1L, transferDTO);

        // Assertions
        assertTrue(optionalTransfer.isPresent());
        assertEquals(transfer, optionalTransfer.get());
    }


    private void startTransfer() {
        transfer = new Transfer(ID,new Account(), CPF_CNPJ, BANK_CODE, VALUE);
        transferDTO = new TransferDTO(ID, CPF_CNPJ, BANK_CODE, VALUE);
        account= new Account(ID, new User(), CPF_CNPJ, BANK_CODE, VALUE);

    }
}