package br.com.coleta.controller;

import br.com.coleta.dto.LoginRequestDTO;
import br.com.coleta.dto.TokenResponseDTO;
import br.com.coleta.service.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequestDTO loginDTO) {
        String token = authService.autenticar(loginDTO);
        return Response.ok(new TokenResponseDTO(token)).build();
    }
}