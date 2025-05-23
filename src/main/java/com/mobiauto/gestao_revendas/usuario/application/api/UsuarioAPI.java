package com.mobiauto.gestao_revendas.usuario.application.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mobiauto.gestao_revendas.common.api.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/revenda/{idRevenda}/usuario")
public interface UsuarioAPI {

        @Operation(summary = "Cria um novo usuário na revenda")
        @PreAuthorize("isAuthenticated()")
        @PostMapping()
        @ResponseStatus(HttpStatus.CREATED)
        UsuarioResponse postUsuario(@PathVariable UUID idRevenda, @Valid @RequestBody UsuarioRequest usuarioRequest);

        @Operation(summary = "Lista todos os usuários da revenda")
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        PageResponse<UsuarioListResponse> getTodosUsuarios(@PathVariable UUID idRevenda,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size);

        @Operation(summary = "Busca usuário da revenda")
        @GetMapping(value = "/{idUsuario}")
        @ResponseStatus(HttpStatus.OK)
        UsuarioDetalhadoResponse getUsuarioAtravesId(@PathVariable UUID idRevenda, @PathVariable UUID idUsuario);

        @Operation(summary = "Deleta usuário da revenda")
        @PreAuthorize("hasRole('ADMINISTRADOR' or 'PROPRIETARIO' or 'GERENTE')")
        @DeleteMapping(value = "/{idUsuario}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        void deletaUsuarioAtravesId(@PathVariable UUID idRevenda, @PathVariable UUID idUsuario);

        @Operation(summary = "Altera usuário da revenda")
        @PatchMapping(value = "/{idUsuario}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        void patchAlteraUsuario(@PathVariable UUID idRevenda, @PathVariable UUID idUsuario,
                        @Valid @RequestBody UsuarioAlteracaoRequest usuarioAlteracaoRequest);
        @Operation(summary = "Altera usuário admin")
        @PreAuthorize("hasRole('ADMINISTRADOR')")
        @PatchMapping("/alterar-admin")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        void alterarUsuarioAdmin(@Valid @RequestBody UsuarioAlteracaoRequest usuarioAlteracaoRequest);
}
