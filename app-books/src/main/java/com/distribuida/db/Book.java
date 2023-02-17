package com.distribuida.db;

import jakarta.inject.Inject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "book",description = "Clase de la tabla Book")
@Table(name = "books")
@NamedQuery(name = "Book.findAll",query = "SELECT b FROM Book b")
@NamedQuery(name = "Book.findByAuthor",query = "SELECT b FROM Book b WHERE b.author= :authorId")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Schema(required = true)
    @Parameter(description = "ISBN del libro", example = "11-11")
    @Column(name = "isbn")
    private String isbn;
    @Schema(required = true)
    @Parameter(description = "Nombre del Libro", example = "Distribuida2.0")
    @Column(name = "title")
    private String title;
    @Schema(required = true)
    @Parameter(description = "Autor del Libro", example = "Steven")
    @Column(name = "author_id")
    private Integer author;
    @Schema(required = true)
    @Parameter(description = "Precio del libro", example = "57.00")
    @Column(name = "price")
    private BigDecimal price;
}
