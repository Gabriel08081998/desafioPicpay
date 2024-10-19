package com.picpaysimplificado.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {



    private String cpfCnpj;

    private int bankCode;

    private float value;
}
