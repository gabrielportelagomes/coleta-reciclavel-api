package br.com.coleta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class PontoColetaRequestDTO {
    @NotBlank public String nome;
    public String descricao;
    @NotBlank public String endereco;
    @NotBlank public String bairro;
    @NotNull public BigDecimal latitude;
    @NotNull public BigDecimal longitude;
    public String imagemBase64;
    @NotNull public List<Long> materiaisIds;
}