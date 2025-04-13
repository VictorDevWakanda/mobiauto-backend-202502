package com.mobiauto.gestao_revendas.revenda.repository;

import java.util.List;
import java.util.UUID;

import com.mobiauto.gestao_revendas.revenda.domain.Revenda;

public interface RevendaRepository {

    Revenda salva(Revenda revenda);

    List<Revenda> buscaTodasRevendas();

    Revenda buscaRevendaPorId(UUID idRevenda);

    void deletaRevendaPorId(Revenda revenda);

}
