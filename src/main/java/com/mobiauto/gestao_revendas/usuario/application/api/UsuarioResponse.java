package com.mobiauto.gestao_revendas.usuario.application.api;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UsuarioResponse {
    private UUID idUsuario;
}
