package com.mobiauto.gestao_revendas.oportunidade.application.api;

import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import com.mobiauto.gestao_revendas.oportunidade.application.service.OportunidadeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class OportunidadeController implements OportunidadeAPI {

    private final OportunidadeService oportunidadeService;

    @Override
    public OportunidadeResponse postOportunidade(UUID idRevenda, OportunidadeRequest oportunidadeRequest) {
        log.info("[inicia] OportunidadeController - postOportunidade");
        OportunidadeResponse novaOportunidade = oportunidadeService.criaOportunidade(idRevenda, oportunidadeRequest);
        log.info("[finaliza] OportunidadeController - postOportunidade");
        return novaOportunidade;
    }


}
