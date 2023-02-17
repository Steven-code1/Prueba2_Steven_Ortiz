package com.distribuida;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "app-authors",
                version = "1.0.0",
                description = "API para manejar autores"
        ),
        servers = {
                @Server(url = "http://localhost:7002")
        },
        tags = {
                @Tag(name = "Books", description = "Endpoints para manejar Autores")
        }

)
public class RestApplication extends Application {
}
