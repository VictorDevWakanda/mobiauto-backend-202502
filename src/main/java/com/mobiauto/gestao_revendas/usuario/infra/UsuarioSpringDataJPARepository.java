package com.mobiauto.gestao_revendas.usuario.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

public interface UsuarioSpringDataJPARepository extends JpaRepository<Usuario, UUID> {
    Page<Usuario> findByRevenda_IdRevenda(UUID idRevenda, Pageable pageable);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findAssistenteByRevenda_IdRevenda(UUID idRevenda);

}
