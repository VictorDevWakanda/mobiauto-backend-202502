package com.mobiauto.gestao_revendas.oportunidade.infra;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;

public interface OportunidadeSpringDataJPARepository extends JpaRepository<Oportunidade, UUID> {
    Page<Oportunidade> findByRevenda_IdRevenda(UUID idRevenda, Pageable pageable);

}
