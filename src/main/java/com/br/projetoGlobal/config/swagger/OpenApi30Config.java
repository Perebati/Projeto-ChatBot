package com.br.projetoGlobal.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
  name = "Bearer Authentication",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  scheme = "bearer"
)
@OpenAPIDefinition(
  info =@Info(
    title = "Chatbot no Global",
    version = "1.0",
    description = "Um prototipo de chatbot para o Global Financeiro"
  )
)
public class OpenApi30Config {
}