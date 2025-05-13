package com.mobiauto.gestao_revendas.usuario.domain;

import java.util.UUID;

import org.hibernate.annotations.ManyToAny;

import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioAlteracaoRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_revenda", nullable = false)
    private Revenda idRevenda;

    @NotBlank
    @Column(name = "nome_completo")
    private String nomeCompleto;

    @NotBlank
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @NotBlank
    private String senha;


    public Usuario(UUID idRevenda, UsuarioRequest usuarioRequest) {
        //TODO this.idRevenda = new Revenda(idRevenda);
        this.nomeCompleto = usuarioRequest.getNomeCompleto();
        this.email = usuarioRequest.getEmail();
        this.cargo = usuarioRequest.getCargo();
        this.senha = usuarioRequest.getSenha();
    }

    public void altera(UsuarioAlteracaoRequest usuarioAlteracaoRequest) {
        this.nomeCompleto = usuarioAlteracaoRequest.getNomeCompleto();
        this.email = usuarioAlteracaoRequest.getEmail();
        this.cargo = usuarioAlteracaoRequest.getCargo();
        this.senha = usuarioAlteracaoRequest.getSenha();
    }

}
