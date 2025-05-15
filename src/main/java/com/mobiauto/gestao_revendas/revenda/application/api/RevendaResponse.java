package com.mobiauto.gestao_revendas.revenda.application.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RevendaResponse {
    private String cnpj;
}
