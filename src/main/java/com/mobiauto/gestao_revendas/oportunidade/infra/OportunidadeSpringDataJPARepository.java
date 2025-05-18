package com.mobiauto.gestao_revendas.oportunidade.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;
import com.mobiauto.gestao_revendas.oportunidade.domain.StatusOportunidade;

public interface OportunidadeSpringDataJPARepository extends JpaRepository<Oportunidade, UUID> {
    Page<Oportunidade> findByRevenda_IdRevenda(UUID idRevenda, Pageable pageable);

    int countByResponsavel_IdUsuarioAndStatusIn(UUID idUsuario, List<StatusOportunidade> of);

    Optional<Oportunidade> findTopByResponsavel_IdUsuarioOrderByDataAtribuicaoDesc(UUID idUsuario);

}
