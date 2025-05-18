package com.mobiauto.gestao_revendas.oportunidade.infra;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.oportunidade.application.repository.OportunidadeRepository;
import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class OportunidadeInfraRepository implements OportunidadeRepository {

    private final OportunidadeSpringDataJPARepository oportunidadeSpringDataJPARepository;

    @Override
    public void salva(Oportunidade oportunidade) {
        log.info("[Inicia] OportunidadeInfraRepository - salva");
        try {
            oportunidadeSpringDataJPARepository.save(oportunidade);

        } catch (DataIntegrityViolationException e) {
            log.error("[Erro] OportunidadeInfraRepository - salva: {}", e.getMessage());
            throw APIException.build(HttpStatus.BAD_REQUEST, "Oportunidade já existe!", e);
        }
        log.info("[Finaliza] OportunidadeInfraRepository - salva");
    }

    @Override
    public Page<Oportunidade> buscaOportunidades(UUID idRevenda, Pageable pageable) {
        log.info("[Inicia] OportunidadeInfraRepository - buscaOportunidades");
        Page<Oportunidade> oportunidades = oportunidadeSpringDataJPARepository.findByRevenda_IdRevenda(idRevenda, pageable);
        log.info("[Finaliza] OportunidadeInfraRepository - buscaOportunidades");
        return oportunidades;
    }

}
