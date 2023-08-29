package com.sapo.shipping;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "shipping",
				version = "1.0.0",
				description = "Good App",
				termsOfService = "runcodenow",
				contact = @Contact(
						name = "Anonymous",
						email = "dev.team@gmail.com"
				),
				license = @License(
						name = "license",
						url = "runcodenow"
				)
		)
)
public class ShippingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingApplication.class, args);
	}

}
