package com.mobiauto.gestao_revendas.usuario.application.service;

import java.util.List;
import java.util.UUID;

import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioAlteracaoRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioDetalhadoResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioListResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioResponse;

public interface UsuarioService {

    UsuarioResponse criaUsuario(UsuarioRequest usuarioRequest);

    List<UsuarioListResponse> buscaTodosUsuarios();

    UsuarioDetalhadoResponse buscaUsuarioAtravesId(UUID idUsuario);

    void deletaUsuarioAtravesId(UUID idUsuario);

    void patchAlteraUsuario(UUID idUsuario, UsuarioAlteracaoRequest usuarioAlteracaoRequest);

}
