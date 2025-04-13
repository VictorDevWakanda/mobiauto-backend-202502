package com.mobiauto.gestao_revendas.usuario.application.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.revenda.application.service.RevendaService;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioAlteracaoRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioDetalhadoResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioListResponse;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioResponse;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
import com.mobiauto.gestao_revendas.usuario.domain.Cargo;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsuarioApplicationService implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RevendaService revendaService;

    @Override
    public UsuarioResponse criaUsuario(UUID idRevenda, @Valid UsuarioRequest usuarioRequest) {
        log.info("[Inicia] UsuarioApplicationService - criaUsuario");

        revendaService.buscaRevendaPorId(idRevenda);
        Optional.ofNullable(getUsuarioAutenticado())
                .ifPresentOrElse(usuarioAutenticado -> {
                    if (usuarioAutenticado.getCargo() == Cargo.ASSISTENTE) {
                        throw APIException.build(HttpStatus.FORBIDDEN,
                                "Assistentes não têm permissão para criar usuários.");
                    }

                    if ((usuarioAutenticado.getCargo() == Cargo.GERENTE
                            || usuarioAutenticado.getCargo() == Cargo.PROPRIETARIO) &&
                            !usuarioRequest.getIdRevenda().equals(usuarioAutenticado.getRevenda().getIdRevenda())) {
                        throw APIException.build(HttpStatus.FORBIDDEN,
                                "Você só pode criar usuários para sua própria Revenda.");
                    }

                }, () -> {
                    throw APIException.build(HttpStatus.UNAUTHORIZED, "Usuário não autenticado.");
                });

        Usuario usuario = usuarioRepository.salva(new Usuario(idRevenda, usuarioRequest));
        log.info("[Finaliza] UsuarioApplicationService - criaUsuario");
        return new UsuarioResponse(usuario.getIdUsuario());
    }

    private Usuario getUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, "Usuário não autenticado."));
    }

    @Override
    public Page<UsuarioListResponse> buscaTodosUsuarios(UUID idRevenda, int page, int size) {
        log.info("[Inicia] UsuarioApplicationService - buscaTodosUsuarios");
        revendaService.buscaRevendaPorId(idRevenda);
        Page<Usuario> usuarios = usuarioRepository.buscaTodosUsuarios(idRevenda, PageRequest.of(page, size));
        log.info("[Finaliza] UsuarioApplicationService - buscaTodosUsuarios");
        return UsuarioListResponse.converte(usuarios);
    }

    @Override
    public UsuarioDetalhadoResponse buscaUsuarioAtravesId(UUID idRevenda, UUID idUsuario) {
        log.info("[Inicia] UsuarioApplicationService - buscaUsuarioAtravesId");
        revendaService.buscaRevendaPorId(idRevenda);
        Usuario usuario = usuarioRepository.buscaUsuarioAtravesId(idUsuario);
        log.info("[Finaliza] UsuarioApplicationService - buscaUsuarioAtravesId");
        return new UsuarioDetalhadoResponse(usuario);
    }

    @Override
    public void deletaUsuarioAtravesId(UUID idRevenda, UUID idUsuario) {
        log.info("[Inicia] UsuarioApplicationService - deletaUsuarioAtravesId");
        revendaService.buscaRevendaPorId(idUsuario);
        Usuario usuario = usuarioRepository.buscaUsuarioAtravesId(idUsuario);
        usuarioRepository.deletaUsuario(usuario);
        log.info("[Finaliza] UsuarioApplicationService - deletaUsuarioAtravesId");
    }

    @Override
    public void patchAlteraUsuario(UUID idRevenda, UUID idUsuario, UsuarioAlteracaoRequest usuarioAlteracaoRequest) {
        log.info("[Inicia] UsuarioApplicationService - patchAlteraUsuario");
        revendaService.buscaRevendaPorId(idRevenda);
        Usuario usuario = usuarioRepository.buscaUsuarioAtravesId(idUsuario);
        usuario.altera(usuarioAlteracaoRequest);
        usuarioRepository.salva(usuario);
        log.info("[Finaliza] UsuarioApplicationService - patchAlteraUsuario");
    }

}
