package com.mobiauto.gestao_revendas.oportunidade.domain;

import java.util.UUID;

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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "oportunidade")
public class Oportunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)",name = "id_oportunidade", updatable = false, unique = true, nullable = false)
    private UUID idOportunidade;

    @Enumerated(EnumType.STRING)
    private StatusOportunidade status;

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
}
