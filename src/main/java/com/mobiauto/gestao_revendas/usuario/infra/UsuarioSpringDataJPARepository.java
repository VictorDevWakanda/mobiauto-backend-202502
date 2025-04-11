package com.mobiauto.gestao_revendas.usuario.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

public interface UsuarioSpringDataJPARepository extends JpaRepository<Usuario, UUID> {

}
