package com.mobiauto.gestao_revendas.oportunidade.application.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;

public interface OportunidadeRepository {

    void salva(Oportunidade oportunidade);

    Page<Oportunidade> buscaOportunidades(UUID idRevenda, Pageable pageable);

    Oportunidade buscaOportunidadePorId(UUID idOportunidade);

    void deleta(UUID idOportunidade);

    int countOportunidadesEmAndamento(UUID idUsuario);

    LocalDateTime ultimaDataAtribuicao(UUID idUsuario);

}
