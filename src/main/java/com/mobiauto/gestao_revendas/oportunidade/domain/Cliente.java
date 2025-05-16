package com.mobiauto.gestao_revendas.oportunidade.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Embeddable
@Data
@RequiredArgsConstructor
public class Cliente {
    @NotNull
    private String nome;
    @NotEmpty
    private String email;
    @NotEmpty
    private String telefone;

    public Cliente(Cliente cliente) {
        this.nome = cliente.nome;
        this.email = cliente.email;
        this.telefone = cliente.telefone;
    }
}

