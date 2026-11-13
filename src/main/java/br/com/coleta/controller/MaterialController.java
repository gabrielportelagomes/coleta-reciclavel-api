package br.com.coleta.controller;

import br.com.coleta.dto.MaterialRequestDTO;
import br.com.coleta.service.MaterialService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/materiais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaterialController {

    @Inject
    MaterialService service;

    @GET
    @PermitAll
    public Response listarMateriais() {
        return Response.ok(service.listarMateriais()).build();
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response cadastrarMaterial(@Valid MaterialRequestDTO dto) {
        service.cadastrarMaterial(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response excluirMaterial(@PathParam("id") Long id) {
        service.excluirMaterial(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response atualizarMaterial(@PathParam("id") Long id, @Valid MaterialRequestDTO dto) {
        service.atualizarMaterial(id, dto);
        return Response.noContent().build();
    }
}