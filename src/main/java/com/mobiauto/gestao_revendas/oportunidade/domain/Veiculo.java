package com.mobiauto.gestao_revendas.oportunidade.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Veiculo {
    private String marca;
    private String modelo;
    private String versao;
    private int anoModelo;
}
