
package br.com.coleta.service;

import br.com.coleta.dto.LoginRequestDTO;
import br.com.coleta.model.Administrador;
import br.com.coleta.repository.AdministradorRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

import java.time.Duration;

@ApplicationScoped
public class AuthService {

    @Inject
    AdministradorRepository administradorRepository;

    public String autenticar(LoginRequestDTO loginDTO) {

        Administrador admin =
                administradorRepository.findByEmail(loginDTO.email);

        if (admin == null ||
                !BcryptUtil.matches(loginDTO.senha, admin.getSenha())) {
            throw new NotAuthorizedException("Credenciais inválidas");
        }

        return Jwt.issuer("coleta-api")
                .upn(admin.getEmail())
                .groups("ADMIN")
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}
