package com.mobiauto.gestao_revendas.oportunidade.application.api;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import com.mobiauto.gestao_revendas.common.api.PageResponse;
import com.mobiauto.gestao_revendas.oportunidade.application.service.OportunidadeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class OportunidadeController implements OportunidadeAPI {

    private static final String ID_REVENDA_LOG = "[idRevenda] {}";
    private static final String ID_OPORTUNIDADE_LOG = "[idOportunidade] {}";

    private final OportunidadeService oportunidadeService;

    @Override
    public OportunidadeResponse postOportunidade(UUID idRevenda, OportunidadeRequest oportunidadeRequest) {
        log.info("[inicia] OportunidadeController - postOportunidade");
        OportunidadeResponse novaOportunidade = oportunidadeService.criaOportunidade(idRevenda, oportunidadeRequest);
        log.info("[finaliza] OportunidadeController - postOportunidade");
        return novaOportunidade;
    }

    @Override
    public PageResponse<OportunidadeListResponse> getOportunidades(UUID idRevenda, int page, int size) {
        log.info("[inicia] OportunidadeController - getOportunidades");
        log.info(ID_REVENDA_LOG, idRevenda);
        Page<OportunidadeListResponse> oportunidades = oportunidadeService.buscaOportunidades(idRevenda, page, size);
        log.info("[finaliza] OportunidadeController - getOportunidades");
        return new PageResponse<>(oportunidades);
    }

    @Override
    public void patchOportunidade(UUID idRevenda, UUID idOportunidade, @Valid OportunidadeRequest oportunidadeRequest) {
        log.info("[inicia] OportunidadeController - patchOportunidade");
        log.info(ID_REVENDA_LOG + ID_OPORTUNIDADE_LOG, idRevenda, idOportunidade);
        oportunidadeService.alteraOportunidade(idRevenda, idOportunidade, oportunidadeRequest);
        log.info("[finaliza] OportunidadeController - patchOportunidade");
    }

    @Override
    public void deleteOportunidade(UUID idRevenda, UUID idOportunidade) {
        log.info("[inicia] OportunidadeController - deleteOportunidade");
        log.info(ID_REVENDA_LOG + ID_OPORTUNIDADE_LOG, idRevenda, idOportunidade);
        oportunidadeService.deletaOportunidade(idRevenda, idOportunidade);
        log.info("[finaliza] OportunidadeController - deleteOportunidade");
    }

    @Override
    public void patchTransfereOportunidade(UUID idRevenda, UUID idOportunidade,
            @Valid OportunidadeRequest oportunidadeRequest) {
        log.info("[inicia] OportunidadeController - patchTransfereOportunidade");
        log.info(ID_REVENDA_LOG + ID_OPORTUNIDADE_LOG, idRevenda, idOportunidade);
        oportunidadeService.transfereOportunidade(idRevenda, idOportunidade, oportunidadeRequest);
        log.info("[finaliza] OportunidadeController - patchTransfereOportunidade");
    }


}
