package com.mobiauto.gestao_revendas.revenda.application.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.br.CNPJ;

@Value
public class RevendaRequest {
    @NotBlank
    private String nomeSocial;

    @NotBlank
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;
}
