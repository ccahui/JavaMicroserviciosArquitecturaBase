package com.microservices.store.discovery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
public class DiscoveryServerApplication {
	@Value("${data.perfil}")
	private String value;
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerApplication.class, args);
	}
	@GetMapping("/prueba")
	public String prueba(){
		return "Pefil "+value;
	}
}
