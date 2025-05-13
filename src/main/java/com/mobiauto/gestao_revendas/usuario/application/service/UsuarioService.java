package com.mobiauto.gestao_revendas.usuario.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioAlteracaoRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioDetalhadoResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioListResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioResponse;

import jakarta.validation.Valid;

public interface UsuarioService {

    UsuarioResponse criaUsuario(UUID idRevenda, @Valid UsuarioRequest usuarioRequest);

    Page<UsuarioListResponse> buscaTodosUsuarios(UUID idRevenda, int page, int size);

    UsuarioDetalhadoResponse buscaUsuarioAtravesId(UUID idRevenda, UUID idUsuario);

    void deletaUsuarioAtravesId(UUID idRevenda, UUID idUsuario);

    void patchAlteraUsuario(UUID idRevenda, UUID idUsuario, UsuarioAlteracaoRequest usuarioAlteracaoRequest);

    void alterarUsuarioAdmin(UsuarioAlteracaoRequest usuarioAlteracaoRequest);

}
