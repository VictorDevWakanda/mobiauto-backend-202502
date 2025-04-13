package com.mobiauto.gestao_revendas.usuario.application.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

public interface UsuarioRepository {

    Usuario salva(Usuario usuario);

    Page<Usuario> buscaTodosUsuarios(UUID idRevenda, Pageable pageable);

    Usuario buscaUsuarioAtravesId(UUID idUsuario);

    void deletaUsuario(Usuario usuario);

    Optional <Usuario> findByEmail(String email);

    
}
