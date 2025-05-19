package com.mobiauto.gestao_revendas.revenda.application.api;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class RevendaAlteracaoRequest {
    @NotBlank
    private String nomeSocial;
    
    @NotBlank
    @CNPJ
    private String cnpj;
}
