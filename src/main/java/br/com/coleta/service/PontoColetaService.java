package br.com.coleta.service;

import br.com.coleta.dto.MaterialDTO;
import br.com.coleta.dto.PontoColetaRequestDTO;
import br.com.coleta.dto.PontoColetaResponseDTO;
import br.com.coleta.model.Material;
import br.com.coleta.model.PontoColeta;
import br.com.coleta.repository.PontoColetaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PontoColetaService {

    @Inject
    PontoColetaRepository pontoColetaRepository;

    @Inject
    MaterialService materialService;

    @Transactional
    public void cadastrarPonto(PontoColetaRequestDTO dto) {
        PontoColeta ponto = new PontoColeta();
        ponto.setNome(dto.nome);
        ponto.setDescricao(dto.descricao);
        ponto.setEndereco(dto.endereco);
        ponto.setBairro(dto.bairro);
        ponto.setLatitude(dto.latitude);
        ponto.setLongitude(dto.longitude);
        ponto.setImagemBase64(dto.imagemBase64);
        ponto.setStatus("PENDENTE");

        for (Long materialId : dto.materiaisIds) {
            Material material = materialService.buscarPorId(materialId);
            ponto.getMateriais().add(material);
        }

        pontoColetaRepository.persist(ponto);
    }

    public List<PontoColetaResponseDTO> listarAprovados() {
        return pontoColetaRepository.findByStatus("APROVADO").stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PontoColetaResponseDTO> listarPendentes() {
        return pontoColetaRepository.findByStatus("PENDENTE").stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void alterarStatus(Long id, String novoStatus) {
        PontoColeta ponto = pontoColetaRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Ponto de coleta não encontrado."));
        ponto.setStatus(novoStatus);
    }

    @Transactional
    public void excluirPonto(Long id) {
        boolean deletado = pontoColetaRepository.deleteById(id);
        if (!deletado) {
            throw new NotFoundException("Ponto de coleta não encontrado");
        }
    }

    private PontoColetaResponseDTO mapToDTO(PontoColeta ponto) {
        PontoColetaResponseDTO dto = new PontoColetaResponseDTO();
        dto.id = ponto.getId();
        dto.nome = ponto.getNome();
        dto.descricao = ponto.getDescricao();
        dto.endereco = ponto.getEndereco();
        dto.bairro = ponto.getBairro();
        dto.latitude = ponto.getLatitude();
        dto.longitude = ponto.getLongitude();
        dto.imagemBase64 = ponto.getImagemBase64();
        dto.status = ponto.getStatus();
        dto.criadoEm = ponto.getCriadoEm();
        dto.materiais = ponto.getMateriais().stream()
                .map(m -> new MaterialDTO(m.getId(), m.getNome()))
                .collect(Collectors.toList());
        return dto;
    }
}