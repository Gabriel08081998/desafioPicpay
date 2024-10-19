package com.picpaysimplificado.view;

import com.picpaysimplificado.model.TipoUsuario;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    @NotNull
    private String fullName;
    @Column(unique = true)
    @NotNull
    private String cpfCnpj;
    @NotNull
    @Column(unique = true)
    @Email(message = "Email inválido")
    private String email;
    @NotNull
    private String password;
    @NotNull
    private TipoUsuario tipoUsuario;
}
