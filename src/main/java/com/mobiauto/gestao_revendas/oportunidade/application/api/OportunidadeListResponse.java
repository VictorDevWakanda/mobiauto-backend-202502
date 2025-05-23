package com.mobiauto.gestao_revendas.oportunidade.application.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;
import com.mobiauto.gestao_revendas.oportunidade.domain.StatusOportunidade;

import lombok.Value;

@Value
public class OportunidadeListResponse {

        private UUID idOportunidade;

        private StatusOportunidade status;

        private String motivoConclusao;

        private OportunidadeResponsavelResponse responsavel;

        private OportunidadeRevendaResponse revenda;

        private OportunidadeClienteResponse cliente;

        public static Page<OportunidadeListResponse> converte(Page<Oportunidade> oportunidades) {
                List<OportunidadeListResponse> content = oportunidades.stream()
                                .map(OportunidadeListResponse::new)
                                .collect(Collectors.toList());
                return new PageImpl<>(content, oportunidades.getPageable(), oportunidades.getTotalElements());
        }

        public OportunidadeListResponse(Oportunidade oportunidade) {
                this.idOportunidade = oportunidade.getIdOportunidade();
                this.status = oportunidade.getStatus();
                this.motivoConclusao = oportunidade.getMotivoConclusao();
                this.responsavel = new OportunidadeResponsavelResponse(
                                oportunidade.getResponsavel().getNomeCompleto(),
                                oportunidade.getResponsavel().getEmail());
                this.revenda = new OportunidadeRevendaResponse(
                                oportunidade.getRevenda().getNomeSocial(),
                                oportunidade.getRevenda().getCnpj());
                this.cliente = new OportunidadeClienteResponse(
                                oportunidade.getCliente().getNome(),
                                oportunidade.getCliente().getEmail(),
                                oportunidade.getCliente().getTelefone());
        }

}
