package com.mobiauto.gestao_revendas.oportunidade.application.repository;

import java.util.List;
import java.util.UUID;

import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;

public interface OportunidadeRepository {

    void salva(Oportunidade oportunidade);

    List<Oportunidade> buscaOportunidades(UUID idRevenda);

}
