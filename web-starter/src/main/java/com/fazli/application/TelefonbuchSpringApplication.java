package com.fazli.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.fazli")
@EnableJpaRepositories(basePackages = {"com.fazli"})
@EntityScan(basePackages = {"com.fazli"})
public class TelefonbuchSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelefonbuchSpringApplication.class, args);
    }
}
