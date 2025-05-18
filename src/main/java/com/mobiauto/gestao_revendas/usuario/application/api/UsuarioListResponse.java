package com.mobiauto.gestao_revendas.usuario.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.Value;

@Value
public class UsuarioListResponse {
    private UUID idUsuario;
    private String nomeCompleto;
    private String email;

    public static Page<UsuarioListResponse> converte(Page<Usuario> usuario) {
        List<UsuarioListResponse> content = usuario.stream()
                .map(UsuarioListResponse::new)
                .collect(Collectors.toList());
        return new PageImpl<>(content, usuario.getPageable(), usuario.getTotalElements());
    }

    public UsuarioListResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.email = usuario.getEmail();
    }

}
