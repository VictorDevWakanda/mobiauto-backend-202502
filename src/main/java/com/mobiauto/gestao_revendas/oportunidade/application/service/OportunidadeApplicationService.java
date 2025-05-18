package com.mobiauto.gestao_revendas.oportunidade.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mobiauto.gestao_revendas.handler.APIException;
import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeListResponse;
import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeRequest;
import com.mobiauto.gestao_revendas.oportunidade.application.api.OportunidadeResponse;
import com.mobiauto.gestao_revendas.oportunidade.application.repository.OportunidadeRepository;
import com.mobiauto.gestao_revendas.oportunidade.domain.Oportunidade;
import com.mobiauto.gestao_revendas.revenda.domain.Revenda;
import com.mobiauto.gestao_revendas.revenda.repository.RevendaRepository;
import com.mobiauto.gestao_revendas.usuario.application.repository.UsuarioRepository;
import com.mobiauto.gestao_revendas.usuario.application.service.UsuarioApplicationService;
import com.mobiauto.gestao_revendas.usuario.domain.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class OportunidadeApplicationService implements OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;
    private final UsuarioApplicationService usuarioApplicationService;
    private final UsuarioRepository usuarioRepository;
    private final RevendaRepository revendaRepository;

    @Override
    public OportunidadeResponse criaOportunidade(UUID idRevenda, OportunidadeRequest oportunidadeRequest) {
        log.info("[inicia] OportunidadeApplicationService - criaOportunidade");
        Usuario usuarioAutenticado = usuarioApplicationService.getUsuarioAutenticado();
        usuarioApplicationService.validaUsuario(usuarioAutenticado, idRevenda);

        if (oportunidadeRequest.getCliente() == null || oportunidadeRequest.getVeiculo() == null) {
            throw APIException.build(HttpStatus.BAD_REQUEST, "Dados do cliente e veículo são obrigatórios.");
        }

        Revenda revenda = revendaRepository.buscaRevendaPorId(idRevenda);
        Usuario responsavel = usuarioRepository.buscaUsuarioAtravesId(
                oportunidadeRequest.getResponsavel().getIdUsuario());

        Oportunidade oportunidade = new Oportunidade(revenda, responsavel, oportunidadeRequest);
        oportunidadeRepository.salva(oportunidade);

        log.info("[finaliza] OportunidadeApplicationService - criaOportunidade");
        return new OportunidadeResponse(oportunidade.getIdOportunidade());
    }

    @Override
    public Page<OportunidadeListResponse> buscaOportunidades(UUID idRevenda, int page, int size) {
        log.info("[inicia] OportunidadeApplicationService - buscaOportunidades");
        revendaRepository.buscaRevendaPorId(idRevenda);
        Page<Oportunidade> oportunidades = oportunidadeRepository.buscaOportunidades(idRevenda, PageRequest.of(page, size, Sort.by("responsavel.nomeCompleto").ascending()));
        log.info("[Finaliza] OportunidadeApplicationService - buscaOportunidades");
        return OportunidadeListResponse.converte(oportunidades);
    }

}
