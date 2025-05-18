package com.mobiauto.gestao_revendas.oportunidade.application.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/revenda/{idRevenda}/oportunidade")
public interface OportunidadeAPI {

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    OportunidadeResponse postOportunidade(@PathVariable UUID idRevenda, @Valid @RequestBody OportunidadeRequest oportunidadeRequest);

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<OportunidadeListResponse> getOportunidades(@PathVariable UUID idRevenda);

}
