package com.mobiauto.gestao_revendas.usuario.application.service;

import java.util.List;
import java.util.UUID;

import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioAlteracaoRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioDetalhadoResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioListResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioResponse;

import jakarta.validation.Valid;

public interface UsuarioService {

    UsuarioResponse criaUsuario(UUID idRevenda, @Valid UsuarioRequest usuarioRequest);

    List<UsuarioListResponse> buscaTodosUsuarios(UUID idRevenda);

    UsuarioDetalhadoResponse buscaUsuarioAtravesId(UUID idRevenda, UUID idUsuario);

    void deletaUsuarioAtravesId(UUID idRevenda, UUID idUsuario);

    void patchAlteraUsuario(UUID idRevenda, UUID idUsuario, UsuarioAlteracaoRequest usuarioAlteracaoRequest);

}
