package com.mobiauto.gestao_revendas.oportunidade.application.api;

import lombok.Value;

@Value
public class OportunidadeClienteResponse {
    private String nome;
    private String email;
    private String telefone;
}
