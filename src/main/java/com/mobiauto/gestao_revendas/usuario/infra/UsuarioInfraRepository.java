package com.mobiauto.gestao_revendas.usuario.infra;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
@RequiredArgsConstructor
public class UsuarioInfraRepository implements UsuarioRepository {
    private final UsuarioSpringDataJPARepository usuarioSpringDataJPARepository;

    @Override
    public Usuario salva(Usuario usuario) {
        log.info("[Inicia] UsuarioInfraRepository - salva");
        try {
            usuarioSpringDataJPARepository.save(usuario);

        } catch (DataIntegrityViolationException e) {
            throw APIException.build(HttpStatus.BAD_REQUEST, "Usuario já existe!", e);
        }
        log.info("[Finaliza] UsuarioInfraRepository - salva");
        return usuario;
    }

    @Override
    public List<Usuario> buscaTodosUsuarios() {
        log.info("[Inicia] UsuarioInfraRepository - buscaTodosUsuarios");
        List<Usuario> todosUsuarios = usuarioSpringDataJPARepository.findAll();
        log.info("[Finaliza] UsuarioInfraRepository - buscaTodosUsuarios");
        return todosUsuarios;
    }

    @Override
    public Usuario buscaUsuarioAtravesId(UUID idUsuario) {
        log.info("[Inicia] UsuarioInfraRepository - buscaUsuarioAtravesId");
        Usuario usuario = usuarioSpringDataJPARepository
                .findById(idUsuario)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
        log.info("[Finaliza] UsuarioInfraRepository - buscaUsuarioAtravesId");
        return usuario;
    }

    @Override
    public void deletaUsuario(Usuario usuario) {
        log.info("[Inicia] UsuarioInfraRepository - deletaUsuario");
        usuarioSpringDataJPARepository.delete(usuario);
        log.info("[Finaliza] UsuarioInfraRepository - deletaUsuario");
    }

}
