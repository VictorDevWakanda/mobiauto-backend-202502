package com.mobiauto.gestao_revendas.oportunidade.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;

public interface OportunidadeSpringDataJPARepository extends JpaRepository<Oportunidade, UUID>{

}
