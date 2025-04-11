package com.mobiauto.gestao_revendas.usuario.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioAlteracaoRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioDetalhadoResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioListResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioResponse;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsuarioApplicationService implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponse criaUsuario(UsuarioRequest usuarioRequest) {
        log.info("[Inicia] UsuarioApplicationService - criaUsuario");
        Usuario usuario = usuarioRepository.salva(new Usuario(usuarioRequest));
        log.info("[Finaliza] UsuarioApplicationService - criaUsuario");
        return UsuarioResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .build();
    }

    @Override
    public List<UsuarioListResponse> buscaTodosUsuarios() {
        log.info("[Inicia] UsuarioApplicationService - buscaTodosUsuarios");
        List<Usuario> usuarios = usuarioRepository.buscaTodosUsuarios();
        log.info("[Finaliza] UsuarioApplicationService - buscaTodosUsuarios");
        return UsuarioListResponse.converte(usuarios);
    }

    @Override
    public UsuarioDetalhadoResponse buscaUsuarioAtravesId(UUID idUsuario) {
        log.info("[Inicia] UsuarioApplicationService - buscaUsuarioAtravesId");
        Usuario usuario = usuarioRepository.buscaUsuarioAtravesId(idUsuario);
        log.info("[Finaliza] UsuarioApplicationService - buscaUsuarioAtravesId");
        return new UsuarioDetalhadoResponse(usuario);
    }

    @Override
    public void deletaUsuarioAtravesId(UUID idUsuario) {
        log.info("[Inicia] UsuarioApplicationService - deletaUsuarioAtravesId");
        Usuario usuario = usuarioRepository.buscaUsuarioAtravesId(idUsuario);
        usuarioRepository.deletaUsuario(usuario);
        log.info("[Finaliza] UsuarioApplicationService - deletaUsuarioAtravesId");
    }

    @Override
    public void patchAlteraUsuario(UUID idUsuario, UsuarioAlteracaoRequest usuarioAlteracaoRequest) {
        log.info("[Inicia] UsuarioApplicationService - patchAlteraUsuario");
        Usuario usuario = usuarioRepository.buscaUsuarioAtravesId(idUsuario);
        usuario.altera(usuarioAlteracaoRequest);
        usuarioRepository.salva(usuario);
        log.info("[Finaliza] UsuarioApplicationService - patchAlteraUsuario");
    }

}
