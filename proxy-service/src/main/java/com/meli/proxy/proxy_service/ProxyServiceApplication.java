package com.meli.proxy.proxy_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.meli.proxy.proxy_service.infrastructure")
public class ProxyServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProxyServiceApplication.class, args);
	}
}
