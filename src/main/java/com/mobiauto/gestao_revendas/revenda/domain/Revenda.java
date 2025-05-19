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
    @Column(columnDefinition = "CHAR(36)",name = "id_revenda", updatable = false, unique = true, nullable = false)
    private UUID idRevenda;

    @NotBlank
    @Column(name = "nome_social", nullable = false, length = 50)
    private String nomeSocial;
    
    @NotBlank
    @CNPJ (message = "CNPJ inválido")
    @Column(name = "cnpj", unique = true, nullable = false, length = 18)
    private String cnpj;

    public Revenda(UUID idRevenda) {
        this.idRevenda = idRevenda;
    }

    public Revenda(RevendaRequest revendaRequest) {
        this.nomeSocial = revendaRequest.getNomeSocial();
        this.cnpj = formatarCNPJ(sanitizarCNPJ(revendaRequest.getCnpj()));
    }

    public void alteraRevenda(RevendaAlteracaoRequest revendaAlteracaoRequest) {
        this.nomeSocial = revendaAlteracaoRequest.getNomeSocial();
        this.cnpj = formatarCNPJ(sanitizarCNPJ(revendaAlteracaoRequest.getCnpj()));
    }

    
    private String sanitizarCNPJ(String cnpj) {
        return cnpj.replaceAll("[^\\d]", "");
    }

    
    private String formatarCNPJ(String cnpj) {
        return cnpj.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

}
