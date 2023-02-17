package com.distribuida;
import jakarta.ws.rs.ApplicationPath;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(
                title = "app-book",
                version = "1.0.0",
                description = "API para manejar libros"
        ),
        servers = {
                @Server(url = "http://localhost:7001")
        },
        tags = {
                @Tag(name = "Books", description = "Endpoints para manejar libros")
        }

)
public class RestApp extends Application {

}
