package com.mobiauto.gestao_revendas.revenda.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobiauto.gestao_revendas.revenda.domain.Revenda;

public interface RevendaSpringDataRepository extends JpaRepository<Revenda, UUID> {

}
