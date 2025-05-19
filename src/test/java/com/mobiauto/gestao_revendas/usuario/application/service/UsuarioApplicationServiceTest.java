package com.mobiauto.gestao_revendas.usuario.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaDetalhadoResponse;
import com.mobiauto.gestao_revendas.revenda.application.service.RevendaService;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioAlteracaoRequest;
import com.mobiauto.gestao_revendas.usuario.application.api.UsuarioRequest;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
import com.mobiauto.gestao_revendas.usuario.domain.Cargo;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

class UsuarioApplicationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RevendaService revendaService;
    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioApplicationService usuarioService;

    private final UUID idRevenda = UUID.randomUUID();
    private final UUID idUsuario = UUID.randomUUID();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock do contexto de segurança
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin@email.com"); // ou o e-mail do usuário de teste
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    // --- Cadastro de Usuário ---

    @Test
    void devePermitirAdminCadastrarUsuario() {
        Usuario admin = mock(Usuario.class);
        when(admin.getCargo()).thenReturn(Cargo.ADMINISTRADOR);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(admin));
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");
        when(revendaService.buscaRevendaPorId(any())).thenReturn(mock(RevendaDetalhadoResponse.class));
        UsuarioRequest req = new UsuarioRequest("Novo Usuário", "novo@email.com", Cargo.ASSISTENTE,
                idRevenda.toString(), "senha123");
        assertDoesNotThrow(() -> usuarioService.criaUsuario(idRevenda, req));
    }

    @Test
    void devePermitirProprietarioCadastrarUsuarioNaSuaRevenda() {
        Usuario proprietario = mock(Usuario.class);
        when(proprietario.getCargo()).thenReturn(Cargo.PROPRIETARIO);
        com.mobiauto.gestao_revendas.revenda.domain.Revenda revenda = mock(
                com.mobiauto.gestao_revendas.revenda.domain.Revenda.class);
        when(revenda.getIdRevenda()).thenReturn(idRevenda);
        when(proprietario.getRevenda()).thenReturn(revenda);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(proprietario));
        when(revendaService.buscaRevendaPorId(any())).thenReturn(mock(RevendaDetalhadoResponse.class));
        UsuarioRequest req = new UsuarioRequest("Novo Usuário", "novo@email.com", Cargo.ASSISTENTE,
                idRevenda.toString(), "senha123");
        assertDoesNotThrow(() -> usuarioService.criaUsuario(idRevenda, req));
    }

    @Test
    void naoPermiteProprietarioCadastrarUsuarioEmOutraRevenda() {
        Usuario proprietario = mock(Usuario.class);
        when(proprietario.getCargo()).thenReturn(Cargo.PROPRIETARIO);
        com.mobiauto.gestao_revendas.revenda.domain.Revenda outraRevenda = mock(
                com.mobiauto.gestao_revendas.revenda.domain.Revenda.class);
        when(outraRevenda.getIdRevenda()).thenReturn(UUID.randomUUID());
        when(proprietario.getRevenda()).thenReturn(outraRevenda);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(proprietario));
        when(revendaService.buscaRevendaPorId(any())).thenReturn(mock(RevendaDetalhadoResponse.class));
        UsuarioRequest req = new UsuarioRequest("Novo Usuário", "novo@email.com", Cargo.ASSISTENTE,
                idRevenda.toString(), "senha123");
        APIException ex = assertThrows(APIException.class, () -> usuarioService.criaUsuario(idRevenda, req));
        assertTrue(ex.getMessage().contains("sua própria Revenda"));
    }

    @Test
    void naoPermiteAssistenteCadastrarUsuario() {
        Usuario assistente = mock(Usuario.class);
        when(assistente.getCargo()).thenReturn(Cargo.ASSISTENTE);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(assistente));
        when(revendaService.buscaRevendaPorId(any())).thenReturn(mock(RevendaDetalhadoResponse.class));
        UsuarioRequest req = new UsuarioRequest("Novo Usuário", "novo@email.com", Cargo.ASSISTENTE,
                idRevenda.toString(), "senha123");
        APIException ex = assertThrows(APIException.class, () -> usuarioService.criaUsuario(idRevenda, req));
        assertTrue(ex.getMessage().contains("Assistentes não têm permissão"));
    }

    // --- Edição de Usuário ---

    @Test
    void devePermitirAdminAlterarUsuario() {
        Usuario admin = mock(Usuario.class);
        when(admin.getCargo()).thenReturn(Cargo.ADMINISTRADOR);
        when(usuarioRepository.findByEmail("admin@revenda.com")).thenReturn(Optional.of(admin));
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");
        UsuarioAlteracaoRequest req = new UsuarioAlteracaoRequest("Admin", "admin@revenda.com", Cargo.ADMINISTRADOR,
                "senhaNova");
        assertDoesNotThrow(() -> usuarioService.alterarUsuarioAdmin(req));
    }

    @Test
    void naoPermiteAlterarUsuarioAdminSeNaoExistir() {
        when(usuarioRepository.findByEmail("admin@revenda.com")).thenReturn(Optional.empty());
        UsuarioAlteracaoRequest req = new UsuarioAlteracaoRequest("Admin", "admin@revenda.com", Cargo.ADMINISTRADOR,
                "senhaNova");
        APIException ex = assertThrows(APIException.class, () -> usuarioService.alterarUsuarioAdmin(req));
        assertTrue(ex.getMessage().contains("administrador não encontrado"));
    }

    @Test
    void devePermitirProprietarioAlterarUsuarioDaSuaRevenda() {
        Usuario proprietario = mock(Usuario.class);
        when(proprietario.getCargo()).thenReturn(Cargo.PROPRIETARIO);
        com.mobiauto.gestao_revendas.revenda.domain.Revenda revenda = mock(
                com.mobiauto.gestao_revendas.revenda.domain.Revenda.class);
        when(revenda.getIdRevenda()).thenReturn(idRevenda);
        when(proprietario.getRevenda()).thenReturn(revenda);
        Usuario usuario = mock(Usuario.class);
        when(usuario.getCargo()).thenReturn(Cargo.ASSISTENTE);
        when(usuarioRepository.buscaUsuarioAtravesId(idUsuario)).thenReturn(usuario);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(proprietario));
        when(revendaService.buscaRevendaPorId(any())).thenReturn(mock(RevendaDetalhadoResponse.class));
        UsuarioAlteracaoRequest req = new UsuarioAlteracaoRequest("Assistente", "assistente@email.com",
                Cargo.ASSISTENTE, "senhaNova");
        assertDoesNotThrow(() -> usuarioService.patchAlteraUsuario(idRevenda, idUsuario, req));
    }

    @Test
    void naoPermiteAssistenteAlterarUsuario() {
        Usuario assistente = mock(Usuario.class);
        when(assistente.getCargo()).thenReturn(Cargo.ASSISTENTE);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(assistente));
        Usuario usuario = mock(Usuario.class);
        when(usuario.getCargo()).thenReturn(Cargo.ASSISTENTE);
        when(usuarioRepository.buscaUsuarioAtravesId(idUsuario)).thenReturn(usuario);
        UsuarioAlteracaoRequest req = new UsuarioAlteracaoRequest("Assistente", "assistente@email.com",
                Cargo.ASSISTENTE, "senhaNova");
        APIException ex = assertThrows(APIException.class,
                () -> usuarioService.patchAlteraUsuario(idRevenda, idUsuario, req));
        assertTrue(ex.getMessage().contains("Apenas superiores podem alterar dados de usuario"));
    }

    // --- Busca de Assistentes por Revenda ---

    @Test
    void deveRetornarAssistentesPorRevenda() {
        when(revendaService.buscaRevendaPorId(idRevenda)).thenReturn(mock(RevendaDetalhadoResponse.class));
        Usuario assistente = mock(Usuario.class);
        when(assistente.getCargo()).thenReturn(Cargo.ASSISTENTE);
        when(usuarioRepository.buscaAssistentesPorRevenda(idRevenda)).thenReturn(List.of(assistente));
        List<Usuario> assistentes = usuarioService.buscaAssistentesPorRevenda(idRevenda);
        assertFalse(assistentes.isEmpty());
        assertEquals(Cargo.ASSISTENTE, assistentes.get(0).getCargo());
    }

    @Test
    void deveLancarExcecaoSeNaoExistirAssistenteNaRevenda() {
        when(revendaService.buscaRevendaPorId(idRevenda)).thenReturn(mock(RevendaDetalhadoResponse.class));
        when(usuarioRepository.buscaAssistentesPorRevenda(idRevenda)).thenReturn(List.of());
        APIException ex = assertThrows(APIException.class, () -> usuarioService.buscaAssistentesPorRevenda(idRevenda));
        assertTrue(ex.getMessage().contains("Nenhum assistente encontrado"));
    }
}
