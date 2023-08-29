package com.sapo.shipping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShippingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShippingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
