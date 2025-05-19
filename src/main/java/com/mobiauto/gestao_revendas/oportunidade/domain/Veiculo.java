package com.mobiauto.gestao_revendas.oportunidade.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Embeddable
@Data
public class Veiculo {

    @NotNull
    @Column(length = 50)
    private String marca;
    @NotNull
    @Column(length = 50)
    private String modelo;
    @NotNull
    @Column(length = 10)
    private String versao;
    @Column(length = 4)
    private int anoModelo;

    public Veiculo(Veiculo veiculo) {
        this.marca = veiculo.marca;
        this.modelo = veiculo.modelo;
        this.versao = veiculo.versao;
        this.anoModelo = veiculo.anoModelo;
    }
}
