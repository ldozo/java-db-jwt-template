package com.enginaar.jwtapp.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
  info =@Info(
    title = "Test API",
    version = "v1",
    contact = @Contact(
      name = "Enginaar", email = "kenan@enginaar.com", url = "https://enginaar.com"
    ),
    license = @License(
      name = ".."
    ),
    termsOfService = "Nope",
    description = "This is API"
  ),
  servers = @Server(
    url = "http://localhost:8080",
    description = "Production"
  )
) 
@SecurityScheme(
  name = "Bearer Authentication",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  scheme = "bearer"
)
public class OpenAPIConfig {
	
}
