package com.mobiauto.gestao_revendas.oportunidade.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Embeddable
@Data
@RequiredArgsConstructor
public class Veiculo {
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotBlank
    private String versao;
    private int anoModelo;

    public Veiculo(Veiculo veiculo) {
        this.marca = veiculo.marca;
        this.modelo = veiculo.modelo;
        this.versao = veiculo.versao;
        this.anoModelo = veiculo.anoModelo;
    }
}
