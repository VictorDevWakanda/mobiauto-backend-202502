// package com.mobiauto.gestao_revendas.config;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
// import com.mobiauto.gestao_revendas.usuario.domain.Cargo;
// import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

// @Component
// public class DataLoader implements CommandLineRunner {

//     private final UsuarioRepository usuarioRepository;
//     private final PasswordEncoder passwordEncoder;

//     public DataLoader(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
//         this.usuarioRepository = usuarioRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (usuarioRepository.findByEmail("victoradmin@example.com").isEmpty()) {
//             Usuario admin = new Usuario();
//             admin.setNomeCompleto("VictorADMIN");
//             admin.setEmail("victoradmin@example.com");
//             admin.setSenha(passwordEncoder.encode("admin")); // Criptografa a senha
//             admin.setCargo(Cargo.ADMINISTRADOR);
//             usuarioRepository.save(admin);
//             System.out.println("Usuário administrador criado com sucesso!");
//         }
//     }
// }
