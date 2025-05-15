package com.mobiauto.gestao_revendas.revenda.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaAlteracaoRequest;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaDetalhadoResponse;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaListResponse;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaRequest;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaResponse;
import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
import com.mobiauto.gestao_revendas.revenda.repository.RevendaRepository;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class RevendaApplicationService implements RevendaService {

    private final RevendaRepository revendaRepository;

    private final UsuarioRepository usuarioRepository;

    @Override
    public RevendaResponse criaRevenda(RevendaRequest revendaRequest) {
        log.info("[Inicia] RevendaApplicationService - criaRevenda");
        Revenda revenda = revendaRepository.salva(new Revenda(revendaRequest));
        log.info("[Finaliza] RevendaApplicationService - criaRevenda");
        return RevendaResponse.builder()
                .cnpj(revenda.getCnpj())
                .build();
    }

    @Override
    public List<RevendaListResponse> buscaTodasRevendas() {
        log.info("[Inicia] RevendaApplicationService - buscaTodasRevendas");
        List<Revenda> revendas = revendaRepository.buscaTodasRevendas();
        log.info("[Finaliza] RevendaApplicationService - buscaTodasRevendas");
        return RevendaListResponse.converte(revendas);
    }

    @Override
    public RevendaDetalhadoResponse buscaRevendaPorId(UUID idRevenda) {
        log.info("[Inicia] RevendaApplicationService - buscaRevendaPorId");
        Usuario usuarioAutenticado = getUsuarioAutenticado();
        if (!usuarioAutenticado.getIdRevenda().equals(idRevenda)) {
            throw APIException.build(HttpStatus.FORBIDDEN,
                    "Você só pode buscar informações da sua própria Revenda.");
        }
        Revenda revenda = revendaRepository.buscaRevendaPorId(idRevenda);
        log.info("[Finaliza] RevendaApplicationService - buscaRevendaPorId");
        return new RevendaDetalhadoResponse(revenda);
    }

    private Usuario getUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, "Usuário não autenticado."));
    }

    // TODO: Depois de criar o endpoint de deletar revenda, verificar se o usuario
    // tem permissão para deletar. Verificar se precisa fazer semelhante ao
    // buscaPorId
    @Override
    public void deletaRevendaPorId(UUID idRevenda) {
        log.info("[Inicia] RevendaApplicationService - deletaRevendaPorId");
        Revenda revenda = revendaRepository.buscaRevendaPorId(idRevenda);
        revendaRepository.deletaRevendaPorId(revenda);
        log.info("[Finaliza] RevendaApplicationService - deletaRevendaPorId");
    }

    @Override
    public void patchAlteraRevenda(UUID idRevenda, RevendaAlteracaoRequest revendaAlteracaoRequest) {
        log.info("[Inicia] RevendaApplicationService - patchAlteraRevenda");
        Revenda revenda = revendaRepository.buscaRevendaPorId(idRevenda);
        revenda.alteraRevenda(revendaAlteracaoRequest);
        revendaRepository.salva(revenda);
        log.info("[Finaliza] RevendaApplicationService - patchAlteraRevenda");
    }

}
