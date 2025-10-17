package com.example.demo.platform.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.demo")
@EnableJpaRepositories(basePackages = {"com.example.demo.modules.**.adapters.outbound", "com.example.demo.modules.**.adapters.infrastructure.persistence"})
@EntityScan(basePackages = "com.example.demo.modules.**.domain")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
