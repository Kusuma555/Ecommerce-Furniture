package com.cg.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cg.authservice"})
public class AuthServiceApplication {
	
	@Bean
	Gson gson() {
		return new Gson();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
