package com.mobiauto.gestao_revendas.oportunidade.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeRequest;
import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "oportunidade")
public class Oportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "CHAR(36)", name = "id_oportunidade", updatable = false, unique = true, nullable = false)
    private UUID idOportunidade;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private StatusOportunidade status;

    @Column(length = 100)
    private String motivoConclusao;

    @ManyToOne
    private Usuario responsavel;

    @ManyToOne
    private Revenda revenda;

    @NotNull
    @Embedded
    private Veiculo veiculo;

    @NotNull
    @Embedded
    private Cliente cliente;

    private LocalDateTime dataAtribuicao;

    private LocalDateTime dataConclusao;

    public Oportunidade(Revenda revenda, Usuario responsavel, OportunidadeRequest oportunidadeRequest) {
        this.status = StatusOportunidade.NOVO;
        this.motivoConclusao = oportunidadeRequest.getMotivoConclusao();
        this.responsavel = responsavel;
        this.revenda = revenda;
        this.veiculo = oportunidadeRequest.getVeiculo();
        this.cliente = oportunidadeRequest.getCliente();
        this.dataAtribuicao = LocalDateTime.now();
    }

    public void atualiza(Revenda revenda, Usuario responsavel, OportunidadeRequest oportunidadeRequest) {
        this.status = StatusOportunidade.NOVO;
        this.motivoConclusao = oportunidadeRequest.getMotivoConclusao();
        this.responsavel = responsavel;
        this.revenda = revenda;
        this.veiculo = oportunidadeRequest.getVeiculo();
        this.cliente = oportunidadeRequest.getCliente();
        if (this.status == StatusOportunidade.CONCLUIDO) {
            this.dataConclusao = LocalDateTime.now();
        }
    }

}
