package com.mobiauto.gestao_revendas.revenda.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.mobiauto.gestao_revendas.revenda.domain.Revenda;

import lombok.Value;

@Value
public class RevendaListResponse {
    private UUID idRevenda;
    private String nomeSocial;
    private String cnpj;

    public static Page<RevendaListResponse> converte(Page<Revenda> revendas) {
        List<RevendaListResponse> content = revendas.stream()
                .map(RevendaListResponse::new)
                .collect(Collectors.toList());
        return new PageImpl<>(content, revendas.getPageable(), revendas.getTotalElements());
    }

    public RevendaListResponse(Revenda revenda) {
        this.nomeSocial = revenda.getNomeSocial();
        this.cnpj = revenda.getCnpj();
        this.idRevenda = revenda.getIdRevenda();
    }
}
