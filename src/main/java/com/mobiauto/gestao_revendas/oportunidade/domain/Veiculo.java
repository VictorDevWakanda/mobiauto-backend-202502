package com.mobiauto.gestao_revendas.oportunidade.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Embeddable
@Data
@RequiredArgsConstructor
public class Veiculo {
    @NotBlank
    @Column(length = 50)
    private String marca;
    @NotBlank
    @Column(length = 50)
    private String modelo;
    @NotBlank
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
