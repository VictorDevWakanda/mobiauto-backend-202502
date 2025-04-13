package com.mobiauto.gestao_revendas.revenda.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import com.mobiauto.gestao_revendas.revenda.application.service.RevendaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class RevendaController implements RevendaAPI {

    private final RevendaService revendaService;
    private static final String ID_REVENDA_LOG = "[idRevenda] {}";

    @Override
    public RevendaResponse postRevenda(@Valid RevendaRequest revendaRequest) {
        log.info("[Inicia] ClienteController - postRevenda");
        RevendaResponse revendaCriada = revendaService.criaRevenda(revendaRequest);
        log.info("[Finaliza] ClienteController - postRevenda");
        return revendaCriada;

    }

    @Override
    public List<RevendaListResponse> getTodasRevendas() {
        log.info("[Inicia] ClienteController - getTodasRevendas");
        List<RevendaListResponse> revendas = revendaService.buscaTodasRevendas();
        log.info("[Finaliza] ClienteController - getTodasRevendas");
        return revendas;
    }

    @Override
    public RevendaDetalhadoResponse getRevendaPorId(UUID idRevenda) {
        log.info("[Inicia] ClienteController - getRevendaPorId");
        log.info(ID_REVENDA_LOG, idRevenda);
        RevendaDetalhadoResponse revendaDetalhado = revendaService.buscaRevendaPorId(idRevenda);
        log.info("[Finaliza] ClienteController - getRevendaPorId");
        return revendaDetalhado;
    }

    @Override
    public void deleteRevendaPorId(UUID idRevenda) {
        log.info("[Inicia] ClienteController - deleteRevendaPorId");
        log.info(ID_REVENDA_LOG, idRevenda);
        revendaService.deletaRevendaPorId(idRevenda);
        log.info("[Inicia] ClienteController - deleteRevendaPorId");
    }

    @Override
    public void patchAlteraRevenda(UUID idRevenda, @Valid RevendaAlteracaoRequest revendaAlteracaoRequest) {
        log.info("[Inicia] ClienteController - patchAlteraRevenda");
        log.info(ID_REVENDA_LOG, idRevenda);
        revendaService.patchAlteraRevenda(idRevenda, revendaAlteracaoRequest);
        log.info("[Finaliza] ClienteController - patchAlteraRevenda");
    }

}
