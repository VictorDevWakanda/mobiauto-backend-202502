package com.mobiauto.gestao_revendas.revenda.application.api;

import java.util.UUID;

import com.mobiauto.gestao_revendas.revenda.domain.Revenda;

import lombok.Value;

@Value
public class RevendaDetalhadoResponse {
    private UUID idRevenda;
    private String nomeSocial;
    private String cnpj;

    public RevendaDetalhadoResponse(Revenda revenda) {
        this.idRevenda = revenda.getIdRevenda();
        this.nomeSocial = revenda.getNomeSocial();
        this.cnpj = revenda.getCnpj();
    }
}
