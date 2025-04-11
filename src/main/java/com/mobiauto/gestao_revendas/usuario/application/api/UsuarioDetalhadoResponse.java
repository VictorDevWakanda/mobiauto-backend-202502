package com.mobiauto.gestao_revendas.usuario.application.api;

import java.util.UUID;

import com.mobiauto.gestao_revendas.usuario.domain.Cargo;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.Value;

@Value
public class UsuarioDetalhadoResponse {
    private UUID idUsuario;
    private String nomeCompleto;
    private String email;
    private Cargo cargo;

    public UsuarioDetalhadoResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.email = usuario.getEmail();
        this.cargo = usuario.getCargo();
    }
}
