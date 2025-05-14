package com.mobiauto.gestao_revendas.oportunidade.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Cliente {
    private String nome;
    private String email;
    private String telefone;
}

