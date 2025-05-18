package com.mobiauto.gestao_revendas.oportunidade.infra;

import java.time.LocalDateTime;
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
import com.mobiauto.gestao_revendas.oportunidade.domain.StatusOportunidade;

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
        Page<Oportunidade> oportunidades = oportunidadeSpringDataJPARepository.findByRevenda_IdRevenda(idRevenda,
                pageable);
        log.info("[Finaliza] OportunidadeInfraRepository - buscaOportunidades");
        return oportunidades;
    }

    @Override
    public Oportunidade buscaOportunidadePorId(UUID idOportunidade) {
        log.info("[Inicia] OportunidadeInfraRepository - buscaOportunidadePorId");
        Oportunidade oportunidade = oportunidadeSpringDataJPARepository.findById(idOportunidade)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Oportunidade não encontrada!"));
        log.info("[Finaliza] OportunidadeInfraRepository - buscaOportunidadePorId");
        return oportunidade;
    }

    @Override
    public void deleta(UUID idOportunidade) {
        log.info("[Inicia] OportunidadeInfraRepository - deleta");
        try {
            oportunidadeSpringDataJPARepository.deleteById(idOportunidade);
        } catch (DataIntegrityViolationException e) {
            log.error("[Erro] OportunidadeInfraRepository - deleta: {}", e.getMessage());
            throw APIException.build(HttpStatus.BAD_REQUEST, "Oportunidade não pode ser deletada!", e);
        }
        log.info("[Finaliza] OportunidadeInfraRepository - deleta");
    }

    @Override
    public int countOportunidadesEmAndamento(UUID idUsuario) {
        return (int) oportunidadeSpringDataJPARepository.countByResponsavel_IdUsuarioAndStatusIn(idUsuario,
                List.of(StatusOportunidade.NOVO, StatusOportunidade.EM_ATENDIMENTO));
    }

    @Override
    public LocalDateTime ultimaDataAtribuicao(UUID idUsuario) {
        return oportunidadeSpringDataJPARepository.findTopByResponsavel_IdUsuarioOrderByDataAtribuicaoDesc(idUsuario)
                .map(Oportunidade::getDataAtribuicao)
                .orElse(null);
    }

}
