package br.com.coleta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PontoColetaResponseDTO {
    public Long id;
    public String nome;
    public String descricao;
    public String endereco;
    public String bairro;
    public BigDecimal latitude;
    public BigDecimal longitude;
    public String imagemBase64;
    public String status;
    public LocalDateTime criadoEm;
    public List<MaterialDTO> materiais;
}