package com.application.jetbill.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerApiConfig {
    @Value("${bookhub.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI(){
        //Definir el servidor de desarrollo
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development Server");

        //Informacion de contacto
        Contact contact = new Contact();
        contact.setEmail("william.billmxe@gmail.com");
        contact.setName("Jetbill");
        contact.setUrl("https://www.jetbill.com");

        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

        //Informacion general de la API
        Info info = new Info()
                .title("API BookHub venta de libros")
                .version("1.0")
                .contact(contact)
                .description("API Restful de venta de libros")
                .termsOfService("https://www.jetbill.com/terms")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .addServersItem(devServer);
    }

}
