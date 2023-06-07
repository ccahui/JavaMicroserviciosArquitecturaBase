package com.microservices.store.shopping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class ShoppingServiceApplication {
	@Value("${data.name-profile}")
	private String value;
	public static void main(String[] args) {
		SpringApplication.run(ShoppingServiceApplication.class, args);
	}
	@GetMapping("/prueba")
	public String prueba(){
		return "Pefil "+value;
	}
}
