package com.mobiauto.gestao_revendas.usuario.application.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

public interface UsuarioRepository {

    Usuario salva(Usuario usuario);

    List<Usuario> buscaTodosUsuarios(UUID idRevenda);

    Usuario buscaUsuarioAtravesId(UUID idUsuario);

    void deletaUsuario(Usuario usuario);

    Optional <Usuario> findByEmail(String email);

    
}
