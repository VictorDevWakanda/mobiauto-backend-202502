package com.mobiauto.gestao_revendas.usuario.application.api;

import java.util.UUID;

import com.mobiauto.gestao_revendas.usuario.domain.Cargo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UsuarioRequest {
    @NotBlank
    private String nomeCompleto;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private Cargo cargo;
    @NotNull
    private UUID idRevenda;
    @NotBlank
    private String senha;
}
