package com.mobiauto.gestao_revendas.revenda.application.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class RevendaAlteracaoRequest {
    @NotBlank
    private String nomeSocial;
    
    @NotBlank
    private String cnpj;
}
