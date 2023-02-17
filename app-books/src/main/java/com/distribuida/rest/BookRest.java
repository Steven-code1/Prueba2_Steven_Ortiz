package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.servicios.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookRest {
    @Inject
    private BookService bookService;

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar por id", description = "Solo acepta valores enteros")
    @Parameter(name = "id", description = "El ID del libro", in = ParameterIn.PATH, required = true, schema =
    @Schema(required = true, example = "5"))
    @APIResponse(responseCode = "100", description = "Libro encontrado con éxito.", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "404", description = "Libro no encontrado.")
    public Book findById(@Parameter(description = "id of the book", required = true) @PathParam("id") Integer id)  {
        return bookService.getBookById(id);
    }

    @GET
    @Operation(summary = "Obtener todos los libros", description = "Recupera una lista de todos los libros de la BD")
    @APIResponse(responseCode = "100", description = "Libro encontrado con éxito.", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    public List<Book> findAll() {
        return bookService.getBooks();
    }

    @GET
    @Path("/author/{id}")


    @Operation(summary = "Buscar todos los libros con detalles de autor", description = "Obtiene todos los libros de la BD y para cada libro busca los detalles del autor.")
    @APIResponse(responseCode = "100", description = "Libro encontrado con éxito.", content =
    @Content(mediaType = "application/json", schema = @Schema(allOf = Book.class)))
    @APIResponse(responseCode = "404", description = "Libro no encontrado.")
    public List<Book> findAllByAuthor(
            @Parameter(description = "El ID del Autor que se va a buscar.", required = true)
            @PathParam("id") Integer id) {
        return bookService.getBookByAuthor(id);

    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un libro", description = "Elimina un libro de la BD.")
    @APIResponse(responseCode = "103", description = "Libro eliminado.")
    @APIResponse(responseCode = "404", description = "Libro no encontrado.")
    public Response delete(
            @Parameter(description = "ID del libro a eliminar", example = "1") Integer id ) {
        bookService.delete(id);
        return Response.status((Response.Status.NO_CONTENT)).build();
    }

    @POST
    @Operation(summary = "Insertar libro", description = "Inserta un libro en la BD")
    @APIResponse(responseCode = "101", description = "Libro insertado con éxito.")
    @APIResponse(responseCode = "405", description = "Petición inválida.")
    public Response create(
            @RequestBody(description = "Objeto Book que representa el libro a insertar", required = true, content =
            @Content(schema = @Schema(implementation = Book.class))) Book book){
        bookService.creteBook(book);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar un libro", description = "Actualiza los detalles de un libro existente.")
    @APIResponse(responseCode = "102", description = "El libro se actualizó correctamente.")
    @APIResponse(responseCode = "404", description = "Libro no encontrado.")
    public Response update(@Parameter(description = "El ID del libro que se va a actualizar.", required = true)
            @PathParam("id") Integer id,
            @RequestBody(description = "El objeto Book actualizado.", required = true, content =
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = Book.class))) Book book){

        bookService.updateBook(id, book);
        return Response.status((Response.Status.OK)).build();
    }

}
