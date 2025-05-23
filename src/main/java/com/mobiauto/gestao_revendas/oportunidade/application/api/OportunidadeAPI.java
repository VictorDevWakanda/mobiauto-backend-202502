package com.mobiauto.gestao_revendas.oportunidade.application.api;

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
@RequestMapping("/api/revenda/{idRevenda}/oportunidade")
public interface OportunidadeAPI {

    @Operation(summary = "Cria uma nova oportunidade")
    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    OportunidadeResponse postOportunidade(@PathVariable UUID idRevenda,
            @Valid @RequestBody OportunidadeRequest oportunidadeRequest);

    @Operation(summary = "Lista as oportunidades")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    PageResponse<OportunidadeListResponse> getOportunidades(@PathVariable UUID idRevenda,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Atualiza oportunidade por ID")
    @PatchMapping("/{idOportunidade}")
    @ResponseStatus(HttpStatus.OK)
    void patchOportunidade(@PathVariable UUID idRevenda,
            @PathVariable UUID idOportunidade,
            @Valid @RequestBody OportunidadeRequest oportunidadeRequest);

    @Operation(summary = "Deleta oportunidade por ID")
    @DeleteMapping("/{idOportunidade}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOportunidade(@PathVariable UUID idRevenda, @PathVariable UUID idOportunidade);

    @Operation(summary = "Transfere oportunidade por ID")
    @PatchMapping("/transfere/{idOportunidade}")
    @ResponseStatus(HttpStatus.OK)
    void patchTransfereOportunidade(@PathVariable UUID idRevenda,
            @PathVariable UUID idOportunidade,
            @Valid @RequestBody OportunidadeRequest oportunidadeRequest);
}
