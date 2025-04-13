package com.mobiauto.gestao_revendas.usuario.application.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/revenda/{idUsuario}/usuario")
public interface UsuarioAPI {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    UsuarioResponse postUsuario(@PathVariable UUID idRevenda, @Valid @RequestBody UsuarioRequest usuarioRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UsuarioListResponse> getTodosUsuarios(@PathVariable UUID idRevenda);

    @GetMapping(value = "/{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    UsuarioDetalhadoResponse getUsuarioAtravesId(@PathVariable UUID idRevenda, UUID idUsuario);

    @DeleteMapping(value = "/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletaUsuarioAtravesId(@PathVariable UUID idRevenda, UUID idUsuario);

    @PatchMapping(value = "/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void patchAlteraUsuario(@PathVariable UUID idRevenda, @PathVariable UUID idUsuario,
            @Valid @RequestBody UsuarioAlteracaoRequest usuarioAlteracaoRequest);
}
