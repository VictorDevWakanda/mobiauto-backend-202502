package com.mobiauto.gestao_revendas.revenda.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mobiauto.gestao_revendas.revenda.domain.Revenda;

public interface RevendaRepository {

    Revenda salva(Revenda revenda);

    Page<Revenda> buscaTodasRevendas(Pageable pageable);

    Revenda buscaRevendaPorId(UUID idRevenda);

    void deletaRevendaPorId(Revenda revenda);

}
