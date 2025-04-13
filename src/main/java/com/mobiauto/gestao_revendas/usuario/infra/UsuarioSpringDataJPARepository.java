package com.mobiauto.gestao_revendas.usuario.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

public interface UsuarioSpringDataJPARepository extends JpaRepository<Usuario, UUID> {
    List<Usuario> findByRevendaId(UUID idRevenda);

    Optional<Usuario> findByEmail(String email);

}
