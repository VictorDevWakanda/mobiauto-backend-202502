package com.mobiauto.gestao_revendas.usuario.application.api;

import com.mobiauto.gestao_revendas.usuario.domain.Cargo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UsuarioAlteracaoRequest {
    @NotBlank
    private String nomeCompleto;
    @NotBlank
    private String email;
    @NotNull
    private Cargo cargo;
}
