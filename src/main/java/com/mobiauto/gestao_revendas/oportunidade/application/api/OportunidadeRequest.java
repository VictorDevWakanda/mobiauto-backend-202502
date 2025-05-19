package com.mobiauto.gestao_revendas.oportunidade.application.api;

import java.time.LocalDateTime;

import com.mobiauto.gestao_revendas.oportunidade.domain.Cliente;
import com.mobiauto.gestao_revendas.oportunidade.domain.StatusOportunidade;
import com.mobiauto.gestao_revendas.oportunidade.domain.Veiculo;
import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OportunidadeRequest {

    @NotNull
    private StatusOportunidade status;
    @NotNull
    private String motivoConclusao;
    private Usuario responsavel;
    @NotNull
    private Revenda revenda;
    @NotNull
    private Veiculo veiculo;
    @NotNull
    private Cliente cliente;
    private LocalDateTime dataAtribuicao;
    private LocalDateTime dataConclusao;
}
