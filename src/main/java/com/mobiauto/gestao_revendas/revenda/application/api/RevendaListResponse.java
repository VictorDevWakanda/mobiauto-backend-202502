package com.mobiauto.gestao_revendas.revenda.application.api;

import java.util.List;

import com.mobiauto.gestao_revendas.revenda.domain.Revenda;

import lombok.Value;

@Value
public class RevendaListResponse {
    private String nomeSocial;
    private String cnpj;

    public static List<RevendaListResponse> converte(List<Revenda> revendas) {
        return revendas.stream()
                .map(RevendaListResponse::new)
                .toList();

    }

    public RevendaListResponse(Revenda revenda) {
        this.nomeSocial = revenda.getNomeSocial();
        this.cnpj = revenda.getCnpj();
    }
}
