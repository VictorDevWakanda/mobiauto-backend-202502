package com.mobiauto.gestao_revendas.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mobiauto.gestao_revendas.revenda.application.api.RevendaRequest;
import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
import com.mobiauto.gestao_revendas.revenda.repository.RevendaRepository;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
import com.mobiauto.gestao_revendas.usuario.domain.Cargo;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2

public class DataLoader implements CommandLineRunner {

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.senha}")
    private String adminSenha;

    private final UsuarioRepository usuarioRepository;
    private final RevendaRepository revendaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, RevendaRepository revendaRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.revendaRepository = revendaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Cria uma revenda padrão se não existir
        String cnpj = "12.345.678/0001-95";
        Revenda revenda = revendaRepository.buscaTodasRevendas(org.springframework.data.domain.PageRequest.of(0, 1))
                .stream().findFirst().orElse(null);
        if (revenda == null) {
            RevendaRequest revendaRequest = new RevendaRequest("Revenda Padrão", cnpj);
            Revenda novaRevenda = new Revenda(revendaRequest);
            revenda = revendaRepository.salva(novaRevenda);
        }

        // Cria o usuário admin se não existir
        Optional<Usuario> adminOpt = usuarioRepository.findByEmail(adminEmail);
        if (adminOpt.isEmpty()) {
            UsuarioRequest usuarioRequest = new UsuarioRequest(
                "Administrador",
                "admin@revenda.com",
                Cargo.ADMINISTRADOR,
                revenda.getIdRevenda().toString(),
                passwordEncoder.encode(adminSenha)
            );
            Usuario admin = new Usuario(revenda, usuarioRequest);
            usuarioRepository.salva(admin);
            log.debug("Usuário administrador criado com sucesso!");
        }
    }
}
