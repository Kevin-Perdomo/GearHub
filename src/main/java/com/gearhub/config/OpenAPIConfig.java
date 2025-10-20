package com.gearhub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI gearHubOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Servidor Local - Desenvolvimento");

        Contact contact = new Contact();
        contact.setName("GearHub Team");
        contact.setEmail("suporte@gearhub.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("GearHub API")
                .version("1.0.0")
                .description("API REST para gerenciamento de frotas de veículos.\n\n" +
                        "**Funcionalidades principais:**\n" +
                        "- Gerenciamento de Empresas e Sedes\n" +
                        "- Controle de Veículos e Documentação\n" +
                        "- Seed do Banco de Dados (popular/limpar)\n\n" +
                        "**Endpoints de Seed:**\n" +
                        "- `POST /seed/api/executar` - Popular banco com dados de teste\n" +
                        "- `POST /seed/api/limpar` - Limpar todos os dados do banco")
                .contact(contact)
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
