package com.distribuida.rest;

import com.distribuida.db.Author;
import com.distribuida.service.AuthorService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@ApplicationScoped
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {
    @Inject
    private AuthorService authorService;


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Buscar autor por ID",
            description = "Devuelve un solo autor")
    @APIResponse(responseCode = "103",
            description = "Autor encontrado con éxito.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class)))
    @APIResponse(responseCode = "404",
            description = "Autor no encontrado.")
    public Author findById(@Parameter(description = "ID of the author to be obtained. Cannot be empty.",
            required = true,
            example = "1") @PathParam("id") Long id) {
        return authorService.getAuthorById(id);
    }
    @GET
    @Operation(summary = "Obtiene todos los autores", description = "Obtiene una lista con todos los autores.")
    @APIResponse(responseCode = "104", description = "Lista de autores obtenida con éxito")
    public List<Author> findAll () {

        return authorService.getAuthors();
    }


    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar autor por ID",
            description = "Elimina un autor existente según su ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "105", description = "Autor eliminado exitosamente"),
            @APIResponse(responseCode = "406", description = "Autor no encontrado") })
    public Response delete (@PathParam("id")  @Parameter(description = "ID del autor a eliminar", example = "123") Long id){
        authorService.delete(id);
        return Response.status((Response.Status.NO_CONTENT) ).build();
    }
    @POST
    @Operation(summary = "Insertar Autor", description = "Inserta un autor en la BD")
    @APIResponse(responseCode = "106", description = "Libro insertado con éxito.")
    @APIResponse(responseCode = "407", description = "Petición inválida.")
    public Response create(
            @RequestBody(description = "Objeto Book que representa el libro a insertar", required = true, content =
            @Content(schema = @Schema(implementation = Author.class)))
            Author author)  {
        authorService.createAuthor(author);
        return Response.status(Response.Status.CREATED).build();
    }
    @PUT @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar un Autor", description = "Actualiza los detalles de un Autor existente.")
    @APIResponse(responseCode = "107", description = "El Autor se actualizó correctamente.")
    @APIResponse(responseCode = "408", description = "Autor no encontrado.")
    public Response update (@Parameter(description = "El ID del Autor que se va a actualizar.", required = true)
                                @PathParam("id") Long id,
                            @RequestBody(description = "El objeto Autor actualizado.", required = true, content =
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Author.class))) Author autor){

        authorService.updateAuthor(id,autor);
        return Response.status((Response.Status.OK) ).build();
    }
}