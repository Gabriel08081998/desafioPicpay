package com.picpaysimplificado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private  TipoUsuario tipoUsuario;

}
