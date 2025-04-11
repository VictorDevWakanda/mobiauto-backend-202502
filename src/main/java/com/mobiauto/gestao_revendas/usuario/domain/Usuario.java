package com.mobiauto.gestao_revendas.usuario.domain;

import java.util.UUID;

import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioAlteracaoRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Senha não faz parte dessa regra de negocios, normalmente ela faz parte da regra de ALTENTICAÇÃO.
// Na criação de algo ((Cliente)) é necessário o uso de um construtor vazio ou a anotação @NoArgsConstructor, pois dessa forma o cliente é criado com os atributos,
// caso contrario seria um cliente vazio(sem atributos).
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Data
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)",name = "id_usuario", updatable = false, unique = true, nullable = false)
    private UUID idUsuario;
    @NotBlank
    @Column(name = "nome_completo")
    private String nomeCompleto;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    private Cargo cargo;

    public Usuario(UsuarioRequest usuarioRequest) {
        this.nomeCompleto = usuarioRequest.getNomeCompleto();
        this.email = usuarioRequest.getEmail();
        this.cargo = usuarioRequest.getCargo();
    }

    public void altera(UsuarioAlteracaoRequest usuarioAlteracaoRequest) {
        this.nomeCompleto = usuarioAlteracaoRequest.getNomeCompleto();
        this.email = usuarioAlteracaoRequest.getEmail();
        this.cargo = usuarioAlteracaoRequest.getCargo();
    }

}
