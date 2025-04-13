package com.mobiauto.gestao_revendas.revenda.application.service;

import java.util.List;
import java.util.UUID;

import com.mobiauto.gestao_revendas.revenda.application.api.RevendaAlteracaoRequest;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaDetalhadoResponse;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaListResponse;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaRequest;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaResponse;

public interface RevendaService {

    RevendaResponse criaRevenda(RevendaRequest revendaRequest);

    List<RevendaListResponse> buscaTodasRevendas();

    RevendaDetalhadoResponse buscaRevendaPorId(UUID idRevenda);

    void deletaRevendaPorId(UUID idRevenda);

    void patchAlteraRevenda(UUID idRevenda, RevendaAlteracaoRequest revendaAlteracaoRequest);

}
