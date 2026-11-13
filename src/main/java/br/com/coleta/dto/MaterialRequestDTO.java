package br.com.coleta.dto;

import jakarta.validation.constraints.NotBlank;

public class MaterialRequestDTO {

    @NotBlank(message = "O nome do material não pode ser vazio")
    public String nome;
}