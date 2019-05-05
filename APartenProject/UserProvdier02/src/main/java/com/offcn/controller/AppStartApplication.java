package com.offcn.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppStartApplication.class, args);
	}

}

