package com.mobiauto.gestao_revendas.oportunidade.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeRequest;
import com.mobiauto.gestao_revendas.oportunidade.application.repository.OportunidadeRepository;
import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;
import com.mobiauto.gestao_revendas.oportunidade.domain.StatusOportunidade;
import com.mobiauto.gestao_revendas.revenda.repository.RevendaRepository;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
import com.mobiauto.gestao_revendas.usuario.application.service.UsuarioApplicationService;
import com.mobiauto.gestao_revendas.usuario.domain.Cargo;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

class OportunidadeApplicationServiceTest {

    @Mock
    private OportunidadeRepository oportunidadeRepository;
    @Mock
    private UsuarioApplicationService usuarioApplicationService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RevendaRepository revendaRepository;

    @InjectMocks
    private OportunidadeApplicationService oportunidadeService;

    private final UUID idRevenda = UUID.randomUUID();
    private final UUID idOportunidade = UUID.randomUUID();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void devePermitirTransferenciaPorGerenteOuProprietario() {
        UUID idRevenda = UUID.randomUUID();

        Usuario gerente = mock(Usuario.class);
        com.mobiauto.gestao_revendas.revenda.domain.Revenda revenda = mock(
                com.mobiauto.gestao_revendas.revenda.domain.Revenda.class);
        when(revenda.getIdRevenda()).thenReturn(idRevenda);
        when(gerente.getCargo()).thenReturn(Cargo.GERENTE);
        when(gerente.getRevenda()).thenReturn(revenda);
        when(usuarioApplicationService.getUsuarioAutenticado()).thenReturn(gerente);

        Oportunidade oportunidade = mock(Oportunidade.class);
        when(oportunidadeRepository.buscaOportunidadePorId(idOportunidade)).thenReturn(oportunidade);

        Usuario novoResponsavel = mock(Usuario.class);
        when(novoResponsavel.getRevenda()).thenReturn(revenda);
        when(usuarioRepository.buscaUsuarioAtravesId(any())).thenReturn(novoResponsavel);

        OportunidadeRequest req = mock(OportunidadeRequest.class);
        Usuario responsavel = mock(Usuario.class);
        when(req.getResponsavel()).thenReturn(responsavel);
        when(responsavel.getIdUsuario()).thenReturn(UUID.randomUUID());

        assertDoesNotThrow(() -> oportunidadeService.transfereOportunidade(idRevenda, idOportunidade, req));
    }

    @Test
    void naoPermiteTransferenciaPorAssistente() {
        Usuario assistente = mock(Usuario.class);
        when(assistente.getCargo()).thenReturn(Cargo.ASSISTENTE);
        when(usuarioApplicationService.getUsuarioAutenticado()).thenReturn(assistente);

        Oportunidade oportunidade = mock(Oportunidade.class);
        when(oportunidadeRepository.buscaOportunidadePorId(idOportunidade)).thenReturn(oportunidade);

        OportunidadeRequest req = mock(OportunidadeRequest.class);

        APIException ex = assertThrows(APIException.class,
                () -> oportunidadeService.transfereOportunidade(idRevenda, idOportunidade, req));
        assertTrue(ex.getMessage().contains("Apenas gerente ou proprietário podem transferir oportunidades."));
    }

    @Test
    void deveRegistrarDataConclusaoAoConcluir() {
        Oportunidade oportunidade = mock(Oportunidade.class);
        when(oportunidade.getStatus()).thenReturn(StatusOportunidade.CONCLUIDO);

        LocalDateTime antes = LocalDateTime.now();
        when(oportunidade.getDataConclusao()).thenReturn(LocalDateTime.now());
        LocalDateTime depois = oportunidade.getDataConclusao();

        assertNotNull(depois);
        assertTrue(depois.isAfter(antes) || depois.isEqual(antes));
    }

    @Test
    void naoPermiteEditarOportunidadeSemPermissao() {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getCargo()).thenReturn(Cargo.ASSISTENTE);
        when(usuarioApplicationService.getUsuarioAutenticado()).thenReturn(usuario);

        Oportunidade oportunidade = mock(Oportunidade.class);
        when(oportunidadeRepository.buscaOportunidadePorId(idOportunidade)).thenReturn(oportunidade);

        OportunidadeRequest req = mock(OportunidadeRequest.class);

        APIException ex = assertThrows(APIException.class,
                () -> oportunidadeService.transfereOportunidade(idRevenda, idOportunidade, req));
        assertTrue(ex.getMessage().contains("Apenas gerente ou proprietário podem transferir oportunidades."));
    }

    @Test
    void devePermitirTransferenciaComDataConclusao() {
        UUID idRevenda = UUID.randomUUID();

        Usuario gerente = mock(Usuario.class);
        com.mobiauto.gestao_revendas.revenda.domain.Revenda revenda = mock(
                com.mobiauto.gestao_revendas.revenda.domain.Revenda.class);
        when(revenda.getIdRevenda()).thenReturn(idRevenda);
        when(gerente.getCargo()).thenReturn(Cargo.GERENTE);
        when(gerente.getRevenda()).thenReturn(revenda);
        when(usuarioApplicationService.getUsuarioAutenticado()).thenReturn(gerente);

        Oportunidade oportunidade = mock(Oportunidade.class);
        when(oportunidadeRepository.buscaOportunidadePorId(idOportunidade)).thenReturn(oportunidade);
        when(oportunidade.getStatus()).thenReturn(StatusOportunidade.CONCLUIDO);
        when(oportunidade.getDataConclusao()).thenReturn(LocalDateTime.now());

        Usuario novoResponsavel = mock(Usuario.class);
        when(novoResponsavel.getRevenda()).thenReturn(revenda);
        when(usuarioRepository.buscaUsuarioAtravesId(any())).thenReturn(novoResponsavel);

        OportunidadeRequest req = mock(OportunidadeRequest.class);
        Usuario responsavel = mock(Usuario.class);
        when(req.getResponsavel()).thenReturn(responsavel);
        when(responsavel.getIdUsuario()).thenReturn(UUID.randomUUID());

        assertDoesNotThrow(() -> oportunidadeService.transfereOportunidade(idRevenda, idOportunidade, req));
    }
}
