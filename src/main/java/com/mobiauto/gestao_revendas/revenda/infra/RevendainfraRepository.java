package com.mobiauto.gestao_revendas.revenda.infra;

import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
import com.mobiauto.gestao_revendas.revenda.repository.RevendaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
@RequiredArgsConstructor
public class RevendainfraRepository implements RevendaRepository {

    private final RevendaSpringDataRepository revendaSpringDataRepository;

    @Override
    public Revenda salva(Revenda revenda) {
        log.info("[Inicia] RevendainfraRepository - salva");
        try {
            revendaSpringDataRepository.save(revenda);
        } catch (DataIntegrityViolationException e) {
            throw APIException.build(HttpStatus.BAD_REQUEST, "Revenda já existe");
        }
        log.info("[Finaliza] RevendainfraRepository - salva");
        return revenda;

    }

    @Override
    public Page<Revenda> buscaTodasRevendas(Pageable pageable) {
        log.info("[Inicia] RevendainfraRepository - buscaTodasRevendas");
        Page<Revenda> todasRevendas = revendaSpringDataRepository.findAll(pageable);
        log.info("[Finaliza] RevendainfraRepository - buscaTodasRevendas");
        return todasRevendas;
    }

    @Override
    public Revenda buscaRevendaPorId(UUID idRevenda) {
        log.info("[Inicia] RevendainfraRepository - buscaRevendaPorId");
        Revenda revenda = revendaSpringDataRepository.findById(idRevenda)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Revenda não encontrada"));
        log.info("[Finaliza] RevendainfraRepository - buscaRevendaPorId");
        return revenda;
    }

    @Override
    public void deletaRevendaPorId(Revenda revenda) {
        log.info("[Inicia] RevendainfraRepository - deletaRevendaPorId");
        revendaSpringDataRepository.delete(revenda);
        log.info("[Finaliza] RevendainfraRepository - deletaRevendaPorId");
    }

}
