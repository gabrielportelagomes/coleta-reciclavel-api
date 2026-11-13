package br.com.coleta.controller;

import br.com.coleta.dto.PontoColetaRequestDTO;
import br.com.coleta.service.PontoColetaService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/pontos-coleta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PontoColetaController {

    @Inject
    PontoColetaService service;

    @POST
    @PermitAll
    public Response sugerirPonto(@Valid PontoColetaRequestDTO dto) {
        service.cadastrarPonto(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/aprovados")
    @PermitAll
    public Response listarAprovados() {
        return Response.ok(service.listarAprovados()).build();
    }

    @GET
    @Path("/pendentes")
    @RolesAllowed("ADMIN")
    public Response listarPendentes() {
        return Response.ok(service.listarPendentes()).build();
    }

    @PATCH
    @Path("/{id}/aprovar")
    @RolesAllowed("ADMIN")
    public Response aprovarPonto(@PathParam("id") Long id) {
        service.alterarStatus(id, "APROVADO");
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/rejeitar")
    @RolesAllowed("ADMIN")
    public Response rejeitarPonto(@PathParam("id") Long id) {
        service.alterarStatus(id, "REJEITADO");
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response excluirPonto(@PathParam("id") Long id) {
        service.excluirPonto(id);
        return Response.noContent().build();
    }
}