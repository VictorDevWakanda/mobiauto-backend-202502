package com.mobiauto.gestao_revendas.usuario.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.Value;

@Value
public class UsuarioListResponse {
    private UUID idUsuario;
    private String nomeCompleto;
    private String email;

    public static List<UsuarioListResponse> converte(List<Usuario> usuario) {
        return usuario.stream()
                .map(UsuarioListResponse::new)
                .collect(Collectors.toList());
    }

    public UsuarioListResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.email = usuario.getEmail();
    }

}
