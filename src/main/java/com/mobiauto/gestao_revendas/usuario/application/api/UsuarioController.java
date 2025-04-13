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
    private static final String ID_REVENDA_LOG = "[idRevenda] {}";

    @Override
    public UsuarioResponse postUsuario(UUID idRevenda, @Valid UsuarioRequest usuarioRequest) {
        log.info("[Inicia] UsuarioController - postUsuario");
        log.info(ID_REVENDA_LOG, idRevenda);
        UsuarioResponse usuarioCriado = usuarioService.criaUsuario(idRevenda, usuarioRequest);
        log.info("[Finaliza] UsuarioController - postUsuario");
        return usuarioCriado;
    }

    @Override
    public List<UsuarioListResponse> getTodosUsuarios(UUID idRevenda) {
        log.info("[Inicia] UsuarioController - getTodoUsuarios");
        log.info(ID_REVENDA_LOG, idRevenda);
        List<UsuarioListResponse> usuarios = usuarioService.buscaTodosUsuarios(idRevenda);
        log.info("[Finaliza] UsuarioController - getTodosUsuarios");
        return usuarios;
    }

    @Override
    public UsuarioDetalhadoResponse getUsuarioAtravesId(UUID idRevenda, UUID idUsuario) {
        log.info("[Inicia] UsuarioController - getUsuarioAtravesId");
        log.info(ID_REVENDA_LOG, ID_USUARIO_LOG, idRevenda, idUsuario);
        UsuarioDetalhadoResponse usuarioDetalhado = usuarioService.buscaUsuarioAtravesId(idRevenda, idUsuario);
        log.info("[Finaliza] UsuarioController - getUsuarioAtravesId");
        return usuarioDetalhado;
    }

    @Override
    public void deletaUsuarioAtravesId(UUID idRevenda, UUID idUsuario) {
        log.info("[Inicia] UsuarioController - deletaUsuarioAtravesId");
        log.info(ID_REVENDA_LOG, ID_USUARIO_LOG, idRevenda, idUsuario);
        usuarioService.deletaUsuarioAtravesId(idRevenda, idUsuario);
        log.info("[Finaliza] UsuarioController - deletaUsuarioAtravesId");
    }

    @Override
    public void patchAlteraUsuario(UUID idRevenda, UUID idUsuario,
            @Valid UsuarioAlteracaoRequest usuarioAlteracaoRequest) {
        log.info("[Inicia] UsuarioController - patchAlteraUsuario");
        log.info(ID_REVENDA_LOG, ID_USUARIO_LOG, idRevenda, idUsuario);
        usuarioService.patchAlteraUsuario(idRevenda, idUsuario, usuarioAlteracaoRequest);
        log.info("[Finaliza] UsuarioController - patchAlteraUsuario");
    }

}
