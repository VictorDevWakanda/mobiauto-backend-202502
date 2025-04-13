package com.mobiauto.gestao_revendas.revenda.domain;

import java.util.UUID;

import org.hibernate.validator.constraints.br.CNPJ;

import com.mobiauto.gestao_revendas.revenda.application.api.RevendaAlteracaoRequest;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Data
@Table(name = "revenda")
public class Revenda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)",name = "id_revenda", updatable = false, unique = true, nullable = false)
    private UUID idRevenda;

    @NotBlank
    @Column(name = "nome_social", nullable = false)
    private String nomeSocial;
    
    @NotBlank
    @CNPJ (message = "CNPJ inválido")
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;

    public Revenda(RevendaRequest usuarioRequest) {
        this.nomeSocial = usuarioRequest.getNomeSocial();
        this.cnpj = usuarioRequest.getCnpj();
    }

    public void alteraRevenda(RevendaAlteracaoRequest revendaAlteracaoRequest) {
        this.nomeSocial = revendaAlteracaoRequest.getNomeSocial();
        this.cnpj = revendaAlteracaoRequest.getCnpj();
    }

}
