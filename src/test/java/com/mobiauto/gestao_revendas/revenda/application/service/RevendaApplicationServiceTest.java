package com.mobiauto.gestao_revendas.revenda.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mobiauto.gestao_revendas.revenda.application.api.RevendaAlteracaoRequest;
import com.mobiauto.gestao_revendas.revenda.application.api.RevendaRequest;
import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
import com.mobiauto.gestao_revendas.revenda.repository.RevendaRepository;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;

class RevendaApplicationServiceTest {

    @Mock
    private RevendaRepository revendaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private RevendaApplicationService revendaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarRevendaComCnpjValido() {
        RevendaRequest request = new RevendaRequest("Revenda Teste", "12.345.678/0001-99");
        Revenda revenda = new Revenda(request);
        when(revendaRepository.salva(any())).thenReturn(revenda);

        assertDoesNotThrow(() -> revendaService.criaRevenda(request));
    }

    @Test
    void naoDeveCriarRevendaComCnpjInvalido() {
        RevendaRequest request = new RevendaRequest("Revenda Teste", "123");
        assertThrows(Exception.class, () -> revendaService.criaRevenda(request));
    }

    @Test
    void deveAlterarRevendaComPermissao() {
        UUID id = UUID.randomUUID();
        Revenda revenda = mock(Revenda.class);
        when(revendaRepository.buscaRevendaPorId(id)).thenReturn(revenda);

        RevendaAlteracaoRequest req = new RevendaAlteracaoRequest("Novo Nome", "12.345.678/0001-99");
        assertDoesNotThrow(() -> revendaService.patchAlteraRevenda(id, req));
    }

    @Test
    void deveDeletarRevenda() {
        UUID id = UUID.randomUUID();
        Revenda revenda = mock(Revenda.class);
        when(revendaRepository.buscaRevendaPorId(id)).thenReturn(revenda);

        assertDoesNotThrow(() -> revendaService.deletaRevendaPorId(id));
    }
}
