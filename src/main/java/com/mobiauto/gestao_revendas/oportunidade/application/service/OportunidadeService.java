package com.mobiauto.gestao_revendas.oportunidade.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeListResponse;
import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeRequest;
import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeResponse;

public interface OportunidadeService {

    OportunidadeResponse criaOportunidade(UUID idRevenda, OportunidadeRequest oportunidadeRequest);

    Page<OportunidadeListResponse> buscaOportunidades(UUID idRevenda, int page, int size);

    void alteraOportunidade(UUID idRevenda, UUID idOportunidade, OportunidadeRequest oportunidadeRequest);

    void deletaOportunidade(UUID idRevenda, UUID idOportunidade);

    void transfereOportunidade(UUID idRevenda, UUID idOportunidade, OportunidadeRequest oportunidadeRequest);

}
