package com.cca.dashboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Profile Managment APIs",
description = "These apis are used for managing the profiles of all student and teacher of an institute",
termsOfService = "Term and condition applied",
contact = @Contact(name = "Devloped by Manjit Sharma",email = "manjitsharma963@gmail.com",url = "www.manjitsharma.com"),
license = @License(name = "manjit sharma"),
version = "API/V1"),
servers = {
    @Server(description = "testenv",url = "http://localhost:8080")
})
public class SwaggerConfig {
}
