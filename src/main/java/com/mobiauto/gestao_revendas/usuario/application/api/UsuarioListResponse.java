package com.mobiauto.gestao_revendas.usuario.application.api;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.Value;

@Value
public class UsuarioListResponse {
    private UUID idUsuario;
    private String nomeCompleto;
    private String email;

    public static Page<UsuarioListResponse> converte(Page<Usuario> usuario) {
        return usuario.map(UsuarioListResponse::new);
    }

    public UsuarioListResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.email = usuario.getEmail();
    }

}
