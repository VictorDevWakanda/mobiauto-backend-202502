package com.mobiauto.gestao_revendas.revenda.application.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/revenda")
public interface RevendaAPI {

    @Operation(summary = "Cria uma nova revenda")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    RevendaResponse postRevenda(@Valid @RequestBody RevendaRequest revendaRequest);

    @Operation(summary = "Lista todas as revendas")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    PageResponse<RevendaListResponse> getTodasRevendas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Busca revenda por ID")
    @GetMapping(value = "/{idRevenda}")
    @ResponseStatus(HttpStatus.OK)
    RevendaDetalhadoResponse getRevendaPorId(@PathVariable UUID idRevenda);

    @Operation(summary = "Deleta revenda por ID")
    @DeleteMapping(value = "/{idRevenda}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRevendaPorId(@PathVariable UUID idRevenda);

    @Operation(summary = "Altera revenda por ID")
    @PatchMapping(value = "/{idRevenda}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void patchAlteraRevenda(@PathVariable UUID idRevenda,
            @Valid @RequestBody RevendaAlteracaoRequest revendaAlteracaoRequest);
}
