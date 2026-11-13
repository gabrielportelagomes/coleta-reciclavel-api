package br.com.coleta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
    @NotBlank @Email public String email;
    @NotBlank public String senha;
}