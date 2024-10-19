package com.picpaysimplificado.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    @NotNull
    private String cpfCnpj;
    @NotNull
    private int bankCode;
    @NotNull
    private float value;
}
