// package com.mobiauto.gestao_revendas.config;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
// import com.mobiauto.gestao_revendas.revenda.repository.RevendaRepository;
// import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
// import com.mobiauto.gestao_revendas.usuario.domain.Cargo;
// import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

// @Component
// public class DataLoader implements CommandLineRunner {

//     private final UsuarioRepository usuarioRepository;
//     private final RevendaRepository revendaRepository;
//     private final PasswordEncoder passwordEncoder;

//     public DataLoader(UsuarioRepository usuarioRepository, RevendaRepository revendaRepository,
//             PasswordEncoder passwordEncoder) {
//         this.usuarioRepository = usuarioRepository;
//         this.revendaRepository = revendaRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         // Cria uma revenda padrão se não existir
//         String cnpj = "11.111.111/1111-11";
//         Revenda revenda = revendaRepository.buscaTodasRevendas().stream()
//                 .filter(r -> r.getCnpj().equals(cnpj))
//                 .findFirst()
//                 .orElseGet(() -> {
//                     Revenda nova = new Revenda();
//                     nova.setNomeSocial("ADMIN");
//                     nova.setCnpj(cnpj);
//                     return revendaRepository.salva(nova);
//                 });

//         // Cria o usuário admin se não existir
//         if (usuarioRepository.findByEmail("admin@revenda.com").isEmpty()) {
//             Usuario admin = new Usuario();
//             admin.setNomeCompleto("Administrador");
//             admin.setEmail("admin@revenda.com");
//             admin.setSenha(passwordEncoder.encode("admin")); // Senha criptografada
//             admin.setCargo(Cargo.ADMINISTRADOR);
//             admin.setIdRevenda(revenda);
//             usuarioRepository.salva(admin);
//             System.out.println("Usuário administrador criado com sucesso!");
//         }
//     }
// }
