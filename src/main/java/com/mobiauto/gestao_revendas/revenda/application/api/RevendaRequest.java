package com.mobiauto.gestao_revendas.revenda.application.api;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class RevendaRequest {
    @NotBlank
    private String nomeSocial;

    @NotBlank
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;
}
