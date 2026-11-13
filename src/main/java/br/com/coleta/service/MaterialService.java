package br.com.coleta.service;

import br.com.coleta.dto.MaterialDTO;
import br.com.coleta.dto.MaterialRequestDTO;
import br.com.coleta.model.Material;
import br.com.coleta.repository.MaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaterialService {

    @Inject
    MaterialRepository materialRepository;

    public List<MaterialDTO> listarMateriais() {
        return materialRepository.listAll().stream()
                .map(m -> new MaterialDTO(m.getId(), m.getNome()))
                .collect(Collectors.toList());
    }

    public Material buscarPorId(Long id) {
        return materialRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Material ID " + id + " não encontrado."));
    }

    @Transactional
    public void cadastrarMaterial(MaterialRequestDTO dto) {
        boolean existe = materialRepository.find("lower(nome)", dto.nome.toLowerCase()).firstResultOptional().isPresent();
        if (existe) {
            throw new BadRequestException("Já existe um material cadastrado com este nome.");
        }

        Material material = new Material();
        material.setNome(dto.nome);
        materialRepository.persist(material);
    }

    @Transactional
    public void excluirMaterial(Long id) {
        boolean deletado = materialRepository.deleteById(id);
        if (!deletado) {
            throw new NotFoundException("Material não encontrado.");
        }
    }

    @Transactional
    public void atualizarMaterial(Long id, MaterialRequestDTO dto) {
        Material material = materialRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Material não encontrado."));
        material.setNome(dto.nome);
        materialRepository.persist(material);
    }
}