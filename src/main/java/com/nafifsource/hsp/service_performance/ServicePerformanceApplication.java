package com.nafifsource.hsp.service_performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ServicePerformanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePerformanceApplication.class, args);
	}

}
