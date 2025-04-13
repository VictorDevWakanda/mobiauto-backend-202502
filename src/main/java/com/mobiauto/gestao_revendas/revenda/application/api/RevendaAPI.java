package com.mobiauto.gestao_revendas.revenda.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/revenda")
public interface RevendaAPI {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    RevendaResponse postRevenda(@Valid @RequestBody RevendaRequest revendaRequest);

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<RevendaListResponse> getTodasRevendas();

    @GetMapping(value = "/{idRevenda}")
    @ResponseStatus(HttpStatus.OK)
    RevendaDetalhadoResponse getRevendaPorId(@PathVariable UUID idRevenda);

    @DeleteMapping(value = "/{idRevenda}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRevendaPorId(@PathVariable UUID idRevenda);

    @PatchMapping(value = "/{idRevenda}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void patchAlteraRevenda(@PathVariable UUID idRevenda, @Valid @RequestBody RevendaAlteracaoRequest revendaAlteracaoRequest);
}
