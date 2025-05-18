package com.mobiauto.gestao_revendas.oportunidade.domain;

import jakarta.persistence.Column;
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
    @Column(length = 100)
    private String nome;
    @NotEmpty
    @Column(length = 50)
    private String email;
    @NotEmpty
    @Column(length = 15)
    private String telefone;

    public Cliente(Cliente cliente) {
        this.nome = cliente.nome;
        this.email = cliente.email;
        this.telefone = cliente.telefone;
    }
}

