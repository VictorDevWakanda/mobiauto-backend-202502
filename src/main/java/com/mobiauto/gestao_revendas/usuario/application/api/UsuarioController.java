package com.mobiauto.gestao_revendas.usuario.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import com.mobiauto.gestao_revendas.usuario.application.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UsuarioController implements UsuarioAPI {

    private final UsuarioService usuarioService;
    private static final String ID_USUARIO_LOG = "[idUsuario] {}";

    @Override
    public UsuarioResponse postUsuario(UsuarioRequest usuarioRequest) {
        log.info("[Inicia] UsuarioController - postUsuario");
        UsuarioResponse usuarioCriado = usuarioService.criaUsuario(usuarioRequest);
        log.info("[Finaliza] UsuarioController - postUsuario");
        return usuarioCriado;
    }

    @Override
    public List<UsuarioListResponse> getTodosUsuarios() {
        log.info("[Inicia] UsuarioController - getTodoUsuarios");
        List<UsuarioListResponse> usuarios = usuarioService.buscaTodosUsuarios();
        log.info("[Finaliza] UsuarioController - getTodosUsuarios");
        return usuarios;
    }

    @Override
    public UsuarioDetalhadoResponse getUsuarioAtravesId(UUID idUsuario) {
        log.info("[Inicia] UsuarioController - getUsuarioAtravesId");
        log.info(ID_USUARIO_LOG, idUsuario);
        UsuarioDetalhadoResponse usuarioDetalhado = usuarioService.buscaUsuarioAtravesId(idUsuario);
        log.info("[Finaliza] UsuarioController - getUsuarioAtravesId");
        return usuarioDetalhado;
    }

    @Override
    public void deletaUsuarioAtravesId(UUID idUsuario) {
        log.info("[Inicia] UsuarioController - deletaUsuarioAtravesId");
        log.info(ID_USUARIO_LOG, idUsuario);
        usuarioService.deletaUsuarioAtravesId(idUsuario);
        log.info("[Finaliza] UsuarioController - deletaUsuarioAtravesId");
    }

    @Override
    public void patchAlteraUsuario(UUID idUsuario, @Valid UsuarioAlteracaoRequest usuarioAlteracaoRequest) {
        log.info("[Inicia] UsuarioController - patchAlteraUsuario");
        log.info(ID_USUARIO_LOG, idUsuario);
        usuarioService.patchAlteraUsuario(idUsuario, usuarioAlteracaoRequest);
        log.info("[Finaliza] UsuarioController - patchAlteraUsuario");
    }

}
