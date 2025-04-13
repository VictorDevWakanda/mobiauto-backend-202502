package com.mobiauto.gestao_revendas.revenda.application.api;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RevendaResponse {
    private UUID idRevenda;
}
