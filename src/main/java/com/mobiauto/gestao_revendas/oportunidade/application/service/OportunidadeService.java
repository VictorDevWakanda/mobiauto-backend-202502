package com.mobiauto.gestao_revendas.oportunidade.application.service;

import java.util.UUID;

import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeRequest;
import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeResponse;

public interface OportunidadeService {

    OportunidadeResponse criaOportunidade(UUID idRevenda, OportunidadeRequest oportunidadeRequest);

}
