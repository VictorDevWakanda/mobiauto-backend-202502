package com.mobiauto.gestao_revendas.usuario.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaDetalhadoResponse;
import com.mobiauto.gestao_revendas.revenda.application.service.RevendaService;
import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponse criaUsuario(UUID idRevenda, @Valid UsuarioRequest usuarioRequest) {
        log.info("[Inicia] UsuarioApplicationService - criaUsuario");

        revendaService.buscaRevendaPorId(idRevenda);
        revendaInexistente(idRevenda);
        Usuario usuarioAutenticado = getUsuarioAutenticado();
        validaUsuario(usuarioAutenticado, idRevenda);
        Revenda revenda = new Revenda(idRevenda);
        revenda.setIdRevenda(idRevenda);
        Usuario usuario = new Usuario(revenda, usuarioRequest);
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));

        usuarioRepository.salva(usuario);
        log.info("[Finaliza] UsuarioApplicationService - criaUsuario");
        return new UsuarioResponse(usuario.getIdUsuario());
    }

    public void validaUsuario(Usuario usuarioAutenticado, UUID idRevenda) {
        if (usuarioAutenticado.getCargo() == Cargo.ASSISTENTE) {
            throw APIException.build(HttpStatus.FORBIDDEN,
                    "Assistentes não têm permissão para criar novos usuários.");
        }

        if ((usuarioAutenticado.getCargo() == Cargo.GERENTE
                || usuarioAutenticado.getCargo() == Cargo.PROPRIETARIO) &&
                !idRevenda.equals(usuarioAutenticado.getRevenda().getIdRevenda())) {
            throw APIException.build(HttpStatus.FORBIDDEN,
                    "Você só pode criar usuários para sua própria Revenda.");
        }
    }

    private void revendaInexistente(UUID idRevenda) {
        RevendaDetalhadoResponse revenda = revendaService.buscaRevendaPorId(idRevenda);
        if (revenda == null) {
            throw APIException.build(HttpStatus.NOT_FOUND, "Revenda não encontrada.");
        }
    }

    public Usuario getUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, "Usuário não autenticado."));
    }

    @Override
    public Page<UsuarioListResponse> buscaTodosUsuarios(UUID idRevenda, int page, int size) {
        log.info("[Inicia] UsuarioApplicationService - buscaTodosUsuarios");
        revendaService.buscaRevendaPorId(idRevenda);
        Page<Usuario> usuarios = usuarioRepository.buscaTodosUsuarios(idRevenda,
                PageRequest.of(page, size, Sort.by("nomeCompleto").ascending()));
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
        revendaService.buscaRevendaPorId(idRevenda);
        Usuario usuario = usuarioRepository.buscaUsuarioAtravesId(idUsuario);
        usuarioRepository.deletaUsuario(usuario);
        log.info("[Finaliza] UsuarioApplicationService - deletaUsuarioAtravesId");
    }

    @Override
    public void patchAlteraUsuario(UUID idRevenda, UUID idUsuario, UsuarioAlteracaoRequest usuarioAlteracaoRequest) {
        log.info("[Inicia] UsuarioApplicationService - patchAlteraUsuario");
        revendaService.buscaRevendaPorId(idRevenda);
        Usuario usuario = usuarioRepository.buscaUsuarioAtravesId(idUsuario);
        Usuario usuarioAutenticado = getUsuarioAutenticado();
        validarPermissaoAlteracao(usuarioAutenticado);
        UsuarioAlteracaoRequest alteracaoRequest = requestCriptografada(usuarioAlteracaoRequest);
        usuario.altera(alteracaoRequest);
        usuarioRepository.salva(usuario);
        log.info("[Finaliza] UsuarioApplicationService - patchAlteraUsuario");
    }

    private UsuarioAlteracaoRequest requestCriptografada(UsuarioAlteracaoRequest request) {
        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            return new UsuarioAlteracaoRequest(
                    request.getNomeCompleto(),
                    request.getEmail(),
                    request.getCargo(),
                    passwordEncoder.encode(request.getSenha()));
        }
        return request;
    }

    private void validarPermissaoAlteracao(Usuario usuarioAutenticado) {
        if (!usuarioAutenticado.getCargo().equals(Cargo.ADMINISTRADOR) &&
                !usuarioAutenticado.getCargo().equals(Cargo.PROPRIETARIO)) {
            throw APIException.build(HttpStatus.UNAUTHORIZED, "Apenas superiores podem alterar dados de usuario.");
        }
    }

    @Override
    public void alterarUsuarioAdmin(UsuarioAlteracaoRequest usuarioAlteracaoRequest) {
        log.info("[Inicia] UsuarioApplicationService - alterarUsuarioAdmin");

        
        Usuario usuarioAdmin = usuarioRepository.findByEmail("admin@revenda.com")
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Usuário administrador não encontrado"));

        
        usuarioAdmin.setEmail(usuarioAlteracaoRequest.getEmail());
        usuarioAdmin.setNomeCompleto(usuarioAlteracaoRequest.getNomeCompleto());
        usuarioAdmin.setSenha(passwordEncoder.encode(usuarioAlteracaoRequest.getSenha()));

        usuarioRepository.salva(usuarioAdmin);
        log.info("[Finaliza] UsuarioApplicationService - alterarUsuarioAdmin");
    }

    public List<Usuario> buscaAssistentesPorRevenda(UUID idRevenda) {
        log.info("[Inicia] UsuarioApplicationService - buscaAssistentesPorRevenda");
        revendaService.buscaRevendaPorId(idRevenda);
        List<Usuario> assistentes = usuarioRepository.buscaAssistentesPorRevenda(idRevenda);
        if (assistentes.isEmpty()) {
            throw APIException.build(HttpStatus.NOT_FOUND, "Nenhum assistente encontrado para a revenda.");
        }
        log.info("[Finaliza] UsuarioApplicationService - buscaAssistentesPorRevenda");
        return assistentes;
    }

}
